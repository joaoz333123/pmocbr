# PMOCBR

Aplicativo Android para solicitação de ART (documento de engenharia), com autenticação Google, registro de solicitações e acompanhamento básico.

## Objetivo do projeto

O PMOCBR permite que um cliente:
- Faça login com Google;
- Preencha e envie uma solicitação de ART;
- Receba confirmação com data/hora e ID única da solicitação;
- Acompanhe o andamento básico;
- Visualize o histórico das solicitações;
- Acesse configurações de conta e privacidade.

## Escopo Finalizado (MVP)

- Android com `Jetpack Compose + Material 3`
- Login apenas com Google (usando Credential Manager)
- Tela inicial com 4 módulos: `Solicitar`, `Acompanhar`, `Histórico`, `Configurações`
- Formulário de solicitação ART completo e validado
- ID única no formato `PMOC-AAAAMMDD-4chars` (ex.: `PMOC-20260428-AB12`)
- Persistência em Firebase (`Auth + Firestore`)
- Envio automático de e-mail via `Cloud Functions` (backend Node.js)
- Histórico cronológico filtrado por usuário
- LGPD: Fluxo de solicitação de exclusão de conta e dados integrado ao Firestore

## Arquitetura Implementada

- **Frontend Android**: Compose + MVVM + Navigation + Repository Pattern
- **Backend**: Firebase Auth, Firestore, Cloud Functions (2ª Geração)
- **Segurança**: Firebase Secrets para credenciais SMTP e Regras de Segurança no Firestore
- **Qualidade**: Testes unitários para lógica de geração de IDs

## Fluxo Funcional Concluído

1. **Autenticação**: O usuário loga com Google de forma segura.
2. **Dashboard**: Acesso imediato aos 4 pilares do app.
3. **Solicitação**: Preenchimento de formulário com validação e feedback imediato via Popup.
4. **Backend**: Assim que a ART é salva, uma Cloud Function dispara um e-mail formatado para o engenheiro.
5. **Consulta**: Usuário visualiza suas próprias ARTs no Histórico e Acompanhamento.
6. **Privacidade**: Opção de Logout e pedido de exclusão de dados funcional.

## Diario de progresso (Concluído ✅)

### Etapas do desenvolvimento

- [x] Etapa 1 - Setup Android + Firebase
- [x] Etapa 2 - Login Google + sessão
- [x] Etapa 3 - Home com 4 módulos
- [x] Etapa 4 - Solicitar + validação + ID + persistência
- [x] Etapa 5 - Popup de confirmação + regras de status
- [x] Etapa 6 - Histórico e Acompanhar
- [x] Etapa 7 - Cloud Function de e-mail
- [x] Etapa 8 - Configurações + LGPD
- [x] Etapa 9 - Testes e preparação para Play interna

### Marcos de validação (MVP)

- [x] Login Google validado em dispositivo real
- [x] Envio de solicitação ART validado ponta a ponta
- [x] ID única no padrão `PMOC-AAAAMMDD-4chars` validada
- [x] Popup de confirmação exibindo data/hora correta
- [x] Histórico carregando apenas dados do usuário autenticado
- [x] E-mail da Cloud Function recebido com dados corretos
- [x] Índice composto do Firestore criado e funcional
- [x] Fluxo de solicitação de exclusão (LGPD) validado
- [x] Build de debug (APK) e teste de release concluídos

## Configurações de Ambiente (Backend)

Para o envio de e-mails funcionar, foram configurados os seguintes segredos no Firebase:
- `SMTP_HOST`: Servidor SMTP (ex: smtp.gmail.com)
- `SMTP_PORT`: Porta (587 ou 465)
- `SMTP_USER`: Usuário do e-mail
- `SMTP_PASS`: Senha de app
- `DESTINATARIO_FIXO`: E-mail que recebe as notificações

---
*Projeto finalizado com sucesso em 28/04/2026.*
