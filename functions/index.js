const { onDocumentCreated } = require("firebase-functions/v2/firestore");
const admin = require("firebase-admin");
const nodemailer = require("nodemailer");

admin.initializeApp();

// Função disparada quando uma nova solicitação é criada
exports.enviarEmailSolicitacao = onDocumentCreated("solicitacoes/{docId}", async (event) => {
    const snapshot = event.data;
    if (!snapshot) return;

    const data = snapshot.data();
    const docId = event.params.docId;

    // Evita reenvio se já foi marcado como enviado
    if (data.emailSent === true) return;

    // Configuração do transportador de e-mail (usando segredos do Firebase)
    // Você deve configurar essas variáveis no Firebase CLI (instruções abaixo)
    const transporter = nodemailer.createTransport({
        host: process.env.SMTP_HOST,
        port: parseInt(process.env.SMTP_PORT || "587"),
        secure: false, // true para 465, false para outras
        auth: {
            user: process.env.SMTP_USER,
            pass: process.env.SMTP_PASS,
        },
    });

    const form = data.formData || {};

    const mailOptions = {
        from: `"App PMOCBR" <${process.env.SMTP_USER}>`,
        to: process.env.DESTINATARIO_FIXO, // Seu e-mail de engenheiro
        subject: `Nova Solicitação de ART: ${docId}`,
        html: `
            <h1>Nova Solicitação de ART Recebida</h1>
            <p><strong>ID da Solicitação:</strong> ${docId}</p>
            <p><strong>Usuário (Email):</strong> ${data.userEmail}</p>
            <hr>
            <h3>Dados do Formulário:</h3>
            <ul>
                <li><strong>Responsável:</strong> ${form.nomeResponsavel}</li>
                <li><strong>Empresa:</strong> ${form.razaoSocial}</li>
                <li><strong>CNPJ:</strong> ${form.cnpj}</li>
                <li><strong>Tipo de Serviço:</strong> ${form.tipoServico}</li>
                <li><strong>Início:</strong> ${form.dataInicio} | <strong>Fim:</strong> ${form.dataTermino}</li>
                <li><strong>Obs:</strong> ${form.observacoes || "Nenhuma"}</li>
            </ul>
            <p>Verifique os detalhes completos no console do Firestore.</p>
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
