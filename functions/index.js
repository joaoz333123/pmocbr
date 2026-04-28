const { onDocumentCreated } = require("firebase-functions/v2/firestore");
const admin = require("firebase-admin");
const nodemailer = require("nodemailer");

admin.initializeApp();

// Função disparada quando uma nova solicitação é criada
// Adicionamos a configuração 'secrets' para que a função de 2ª geração possa acessar as credenciais SMTP
exports.enviarEmailSolicitacao = onDocumentCreated({
    document: "solicitacoes/{docId}",
    secrets: ["SMTP_HOST", "SMTP_PORT", "SMTP_USER", "SMTP_PASS", "DESTINATARIO_FIXO"]
}, async (event) => {
    const snapshot = event.data;
    if (!snapshot) return;

    const data = snapshot.data();
    const docId = event.params.docId;

    // Evita reenvio se já foi marcado como enviado
    if (data.emailSent === true) return;

    // Configuração do transportador de e-mail (usando segredos do Firebase)
    const transporter = nodemailer.createTransport({
        host: process.env.SMTP_HOST,
        port: parseInt(process.env.SMTP_PORT || "587"),
        secure: process.env.SMTP_PORT === "465", // true para porta 465, false para outras como 587
        auth: {
            user: process.env.SMTP_USER,
            pass: process.env.SMTP_PASS,
        },
    });

    const form = data.formData || {};

    const mailOptions = {
        from: `"App PMOCBR" <${process.env.SMTP_USER}>`,
        to: process.env.DESTINATARIO_FIXO,
        subject: `Nova Solicitação de ART: ${docId}`,
        html: `
            <div style="font-family: sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #ddd; padding: 20px; border-radius: 8px;">
                <h1 style="color: #1a73e8; border-bottom: 2px solid #1a73e8; padding-bottom: 10px;">Nova Solicitação de ART Recebida</h1>
                <p><strong>ID da Solicitação:</strong> ${docId}</p>
                <p><strong>Usuário (Email):</strong> ${data.userEmail}</p>
                <hr style="border: 0; border-top: 1px solid #eee; margin: 20px 0;">
                <h3 style="color: #333;">Dados do Formulário:</h3>
                <ul style="list-style: none; padding-left: 0;">
                    <li style="margin-bottom: 8px;"><strong>Responsável:</strong> ${form.nomeResponsavel}</li>
                    <li style="margin-bottom: 8px;"><strong>CPF:</strong> ${form.cpf}</li>
                    <li style="margin-bottom: 8px;"><strong>Telefone:</strong> ${form.telefone}</li>
                    <li style="margin-bottom: 8px;"><strong>Empresa:</strong> ${form.razaoSocial}</li>
                    <li style="margin-bottom: 8px;"><strong>CNPJ:</strong> ${form.cnpj}</li>
                    <li style="margin-bottom: 8px;"><strong>CEP:</strong> ${form.cep}</li>
                    <li style="margin-bottom: 8px;"><strong>Endereço:</strong> ${form.enderecoCompleto}</li>
                    <li style="margin-bottom: 8px;"><strong>Tipo de Serviço:</strong> ${form.tipoServico}</li>
                    <li style="margin-bottom: 8px;"><strong>Início:</strong> ${form.dataInicio} | <strong>Fim:</strong> ${form.dataTermino}</li>
                    <li style="margin-bottom: 8px;"><strong>Itens:</strong> ${form.checkItem1 ? "Serviço em Altura; " : ""}${form.checkItem2 ? "Equipamentos Especiais" : ""}</li>
                    <li style="margin-bottom: 8px;"><strong>Observações:</strong> ${form.observacoes || "Nenhuma"}</li>
                </ul>
                <p style="font-size: 12px; color: #777; margin-top: 30px; border-top: 1px solid #eee; padding-top: 10px;">
                    Verifique os detalhes completos no console do Firestore.
                </p>
            </div>
        `,
    };

    try {
        await transporter.sendMail(mailOptions);
        console.log(`E-mail enviado com sucesso para a solicitação ${docId}`);

        // Marca como enviado no Firestore para evitar duplicidade
        return snapshot.ref.update({ emailSent: true });
    } catch (error) {
        console.error("Erro ao enviar e-mail:", error);
        return null;
    }
});
