# PMOCBR

Aplicativo Android para solicitacao de ART (documento de engenharia), com autenticacao Google, registro de solicitacoes e acompanhamento basico.

## Objetivo do projeto

O PMOCBR permite que um cliente:
- faca login com Google;
- preencha e envie uma solicitacao de ART;
- receba confirmacao com data/hora e ID unica da solicitacao;
- acompanhe o andamento basico;
- visualize o historico das solicitacoes;
- acesse configuracoes de conta e privacidade.

## Escopo confirmado (MVP)

- Android com `Jetpack Compose + Material 3`
- Login apenas com Google
- Tela inicial com 4 modulos: `Solicitar`, `Acompanhar`, `Historico`, `Configuracoes`
- Formulario de solicitacao ART sem anexos
- ID unica no formato `PMOC-AAAAMMDD-4chars` (ex.: `PMOC-20260428-AB12`)
- Persistencia em Firebase (`Auth + Firestore`) no plano gratuito
- Envio de e-mail via `Cloud Functions` (backend)
- Historico cronologico simples, sem filtros no MVP
- Sem offline no MVP
- LGPD minimo para Play interna: consentimento + politica de privacidade + solicitacao de exclusao de conta/dados

## Arquitetura proposta

- Frontend Android: Compose + MVVM + Navigation
- Backend: Firebase Auth, Firestore, Cloud Functions
- Modelo base de solicitacao:
  - `id`
  - `userId`
  - `createdAt`
  - `status` (`Recebida`, `Em analise`, `Concluida`)
  - `formData`

## Fluxo funcional (alto nivel)

1. Usuario faz login com Google
2. Acessa Home
3. Entra em `Solicitar` e envia formulario
4. Solicitação e salva no Firestore com ID unica
5. Usuario recebe popup de confirmacao (data + ID)
6. Cloud Function envia e-mail com os dados da solicitacao
7. Solicitações aparecem em `Historico` e `Acompanhar`

## Plano de implementacao em prompts (Gemini)

O plano esta dividido em 9 etapas:
1. Setup Android + Firebase
2. Login Google + sessao
3. Home com 4 modulos
4. Solicitar + validacao + ID + persistencia
5. Popup de confirmacao + regras de status
6. Historico e Acompanhar
7. Cloud Function de e-mail
8. Configuracoes + LGPD
9. Testes e preparacao para Play interna

## Arquivos de referencia para execucao com Gemini

- Prompts completos: `PROMPTS_GEMINI_PMOCBR.md`
- Versao copiar/colar por blocos: `PROMPTS_GEMINI_PMOCBR_BLOCOS.md`
- Plano original aprovado: `.cursor/plans/prompts_gemini_pmocbr_faefdfe7.plan.md` (ou equivalente na pasta de planos da sessao)

## Como usar os prompts

- Execute os blocos em ordem.
- Apos cada etapa, valide build e fluxo principal.
- Em cada prompt, solicite antes:
  1) arquivos que serao alterados
  2) riscos
  3) diff proposto
- So depois aplique as alteracoes.

## Criterios de pronto do MVP

- Login Google funcionando
- Solicitacao ART enviada com sucesso
- Popup com data/hora e ID unica exibido ao usuario
- Registro salvo no Firestore
- Historico exibindo solicitacoes do usuario
- E-mail de notificacao disparado pelo backend
- Telas principais navegaveis sem falhas criticas

## Diario de progresso (checklist)

Use esta secao para marcar o andamento real da implementacao.

### Etapas do desenvolvimento

- [ ] Etapa 1 - Setup Android + Firebase
- [ ] Etapa 2 - Login Google + sessao
- [ ] Etapa 3 - Home com 4 modulos
- [ ] Etapa 4 - Solicitar + validacao + ID + persistencia
- [ ] Etapa 5 - Popup de confirmacao + regras de status
- [ ] Etapa 6 - Historico e Acompanhar
- [ ] Etapa 7 - Cloud Function de e-mail
- [ ] Etapa 8 - Configuracoes + LGPD
- [ ] Etapa 9 - Testes e preparacao para Play interna

### Marcos de validacao (MVP)

- [ ] Login Google validado em dispositivo real
- [ ] Envio de solicitacao ART validado ponta a ponta
- [ ] ID unica no padrao `PMOC-AAAAMMDD-4chars` validada
- [ ] Popup de confirmacao exibindo data/hora correta
- [ ] Historico carregando apenas dados do usuario autenticado
- [ ] E-mail da Cloud Function recebido com dados corretos
- [ ] Fluxo de solicitacao de exclusao (LGPD) validado
- [ ] Build de release (AAB) gerado com sucesso
- [ ] Upload em Play Internal Testing concluido

### Registro rapido de execucao

- Data:
- Etapa executada:
- Resultado:
- Pendencias:
