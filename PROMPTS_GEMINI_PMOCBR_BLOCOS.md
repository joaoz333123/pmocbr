# PMOCBR - Versao copiar e colar por bloco

Fluxo rapido:
1) Copie um bloco inteiro (do `INICIO` ao `FIM`).
2) Cole no Gemini.
3) Espere ele concluir e so depois va para o proximo bloco.

---

==================== INICIO BLOCO 00 - COMANDO FIXO POS-PROMPT ====================

```text
Antes de codar, leia o estado atual do projeto e me mostre:
1) Arquivos que voce vai alterar
2) Riscos da mudanca
3) O diff proposto
So depois aplique as alteracoes.
```

==================== FIM BLOCO 00 ====================

---

==================== INICIO BLOCO 01 - SETUP ANDROID + FIREBASE ====================

```text
Voce e um engenheiro Android senior. Crie e ajuste o projeto PMOCBR com foco em MVP.

Contexto obrigatorio:
- Projeto Android com Kotlin.
- UI: Jetpack Compose + Material 3.
- Arquitetura: MVVM simples.
- Backend: Firebase no plano gratuito.
- Sem suporte offline no MVP.

Objetivo deste passo:
1) Validar/ajustar estrutura base Android app.
2) Adicionar dependencias necessarias para:
   - Firebase Auth
   - Firebase Firestore
   - Google Sign-In (Credential Manager / Google Identity)
   - Navigation Compose
   - ViewModel Compose
3) Configurar gradle para Firebase usando a forma atual recomendada.
4) Preparar o app para receber google-services.json.
5) Garantir build compilando sem erros.

Arquivos esperados para revisar/editar:
- build.gradle.kts (raiz)
- settings.gradle.kts
- app/build.gradle.kts
- gradle/libs.versions.toml (se estiver usando version catalog)
- app/src/main/AndroidManifest.xml

Regras:
- Nao usar bibliotecas desnecessarias.
- Nao implementar funcionalidade final ainda (apenas setup).
- Comentar no codigo somente quando realmente necessario.

Entregue:
1) Diff completo dos arquivos alterados.
2) Lista de passos manuais no Firebase Console (criar projeto/app Android e baixar google-services.json).
3) Checklist de verificacao final de compilacao.
```

==================== FIM BLOCO 01 ====================

---

==================== INICIO BLOCO 02 - LOGIN GOOGLE E SESSAO ====================

```text
Continue no projeto PMOCBR e implemente autenticacao Google.

Objetivo:
- Login apenas com conta Google.
- Manter sessao ativa.
- Se usuario ja estiver autenticado, abrir Home direto.
- Se nao estiver, abrir tela de Login.

Escopo tecnico:
- Firebase Auth com Google.
- Fluxo moderno recomendado para Android (Credential Manager / Google ID).
- Camada de autenticacao com ViewModel + estado de UI.

Telas:
1) LoginScreen:
   - Botao "Entrar com Google"
   - Estado de loading
   - Mensagem de erro amigavel
2) Splash/Startup route:
   - Decide para onde navegar com base no usuario logado

Entregue:
1) Implementacao completa com classes/arquivos novos.
2) Rotas de navegacao claras.
3) Tratamento de falha de login e cancelamento.
4) Passos para habilitar Google Sign-In no Firebase Console.
5) Pequeno guia de teste manual.
```

==================== FIM BLOCO 02 ====================

---

==================== INICIO BLOCO 03 - HOME COM 4 MODULOS ====================

```text
Continue no PMOCBR e implemente a tela inicial apos login.

Requisitos de negocio:
- Home com 4 opcoes:
  1. Solicitar
  2. Acompanhar
  3. Historico
  4. Configuracoes

Requisitos de UX:
- Layout limpo em Compose Material 3.
- Mostrar nome/foto do usuario Google no topo (quando disponivel).
- Botao de logout em Configuracoes (pode ser placeholder se necessario, mas funcional de preferencia).

Requisitos tecnicos:
- Criar grafo de navegacao consistente.
- Criar telas placeholder iniciais para Acompanhar, Historico e Configuracoes.
- Manter padrao MVVM.

Entregue:
1) Codigo das telas e navegacao.
2) Estrutura de pacotes recomendada.
3) Lista de melhorias de UX para iteracoes futuras (curta).
```

==================== FIM BLOCO 03 ====================

---

==================== INICIO BLOCO 04 - SOLICITAR + ID HIBRIDA + PERSISTENCIA ====================

```text
Implemente agora o fluxo principal "Solicitar" no PMOCBR.

Objetivo:
- Usuario preenche formulario ART.
- Ao enviar, salvar no Firestore.
- Gerar ID unica no formato: PMOC-AAAAMMDD-4chars
  Exemplo: PMOC-20260428-AB12
- status inicial: "Recebida"

Modelo de dados da solicitacao:
- id: String
- userId: String
- userEmail: String?
- createdAt: timestamp servidor
- status: String (Recebida | Em analise | Concluida)
- formData: Map<String, Any>

Formulario (semi dinamico):
- Parte fixa:
  - nomeResponsavel
  - empresa
  - cnpj (opcional)
  - cidade
  - tipoServico
  - observacoes (opcional)
- Parte selecionavel:
  - 5 checkboxes de exemplo em lista configuravel local

Validacoes:
- Campos obrigatorios nao podem ficar vazios.
- Botao Enviar desabilitado durante submissao.
- Prevenir envio duplicado por clique repetido.

Entregue:
1) ViewModel + estado de formulario.
2) Repositorio Firestore.
3) Funcao de geracao da ID hibrida.
4) Mapeamento claro do objeto salvo no Firestore.
5) Diff completo e explicacao curta das decisoes.
```

==================== FIM BLOCO 04 ====================

---

==================== INICIO BLOCO 05 - POPUP CONFIRMACAO E STATUS ====================

```text
Continue o PMOCBR implementando feedback de envio.

Ao enviar solicitacao com sucesso:
- Exibir popup/modal de confirmacao com:
  - Data/hora de envio (formatada pt-BR)
  - ID unica da solicitacao
  - Mensagem curta de sucesso
- Botao "OK" volta para Home.

Falhas:
- Se salvar no Firestore falhar, exibir mensagem amigavel e permitir tentar novamente.

Padrao de status no app:
- Recebida (padrao ao criar)
- Em analise
- Concluida

Entregue:
1) Implementacao do popup de sucesso.
2) Tratamento de erro robusto no fluxo de envio.
3) Pequenos ajustes de UX para confianca do usuario (loading, mensagens).
```

==================== FIM BLOCO 05 ====================

---

==================== INICIO BLOCO 06 - HISTORICO E ACOMPANHAR ====================

```text
Implemente as telas Historico e Acompanhar do PMOCBR usando Firestore.

Regra geral:
- Exibir somente solicitacoes do usuario autenticado.
- Ordenacao: mais recentes primeiro.

Tela Historico:
- Lista simples cronologica sem filtros.
- Cada item mostra:
  - ID
  - data de criacao
  - status atual
  - resumo curto (ex: tipoServico + empresa)

Tela Acompanhar:
- Lista igual ao historico, com foco no status.
- Opcional: detalhe simples ao tocar no item.

Estados de UI:
- loading
- lista vazia
- erro de carregamento
- sucesso

Entregue:
1) Queries Firestore por userId com ordenacao por data.
2) Componentes Compose reutilizaveis para card de solicitacao.
3) Tratamento de formato de data pt-BR.
```

==================== FIM BLOCO 06 ====================

---

==================== INICIO BLOCO 07 - CLOUD FUNCTION EMAIL ====================

```text
Implemente backend de envio de e-mail no PMOCBR usando Firebase Cloud Functions.

Objetivo:
- Quando nova solicitacao for criada no Firestore, enviar e-mail para destinatario fixo (configuravel).
- Conteudo do e-mail deve incluir:
  - ID da solicitacao
  - data
  - userEmail
  - campos principais do formulario

Requisitos:
- Usar variaveis de ambiente/secret para credenciais SMTP.
- Nao expor segredo no app Android.
- Implementar tratamento de erro e logs.
- Evitar reenvio duplicado (idempotencia basica, se possivel).

Entregue:
1) Codigo de Cloud Function (Node.js ou TypeScript).
2) Exemplo de configuracao de secrets/variaveis.
3) Comandos de deploy.
4) Estrategia simples para testar localmente e em producao.
```

==================== FIM BLOCO 07 ====================

---

==================== INICIO BLOCO 08 - CONFIGURACOES + LGPD ====================

```text
Implemente a tela Configuracoes com foco em LGPD para MVP do PMOCBR.

Obrigatorio:
1) Exibir dados basicos da conta logada (nome/email).
2) Logout funcional.
3) Secao de privacidade contendo:
   - Link/visualizacao de Politica de Privacidade
   - Tela de consentimento de uso de dados (com registro local e remoto simples)
   - Acao "Solicitar exclusao de conta/dados"

Para exclusao:
- No MVP, pode registrar um pedido de exclusao em colecao no Firestore:
  - userId
  - userEmail
  - createdAt
  - status: "aberto"
- Mostrar confirmacao ao usuario apos enviar pedido.

Entregue:
1) UI completa da tela Configuracoes.
2) Fluxo de consentimento.
3) Fluxo de solicitacao de exclusao.
4) Pontos que devem constar no Data Safety da Play Console (lista objetiva).
```

==================== FIM BLOCO 08 ====================

---

==================== INICIO BLOCO 09 - TESTES E PLAY INTERNA ====================

```text
Finalize o PMOCBR com checklist tecnico para distribuicao interna na Play.

Tarefas:
1) Revisar app para crashes e estados de erro comuns.
2) Criar plano de testes manuais E2E cobrindo:
   - login
   - solicitar
   - popup de confirmacao
   - historico
   - acompanhar
   - configuracoes + exclusao
3) Adicionar testes basicos (unitarios onde fizer sentido).
4) Validar configuracoes de build para release interna.
5) Gerar checklist de publicacao Play Internal Testing.

Saida esperada:
- Lista de correcoes recomendadas antes de release.
- Checklist de QA em formato objetivo.
- Passo a passo curto para gerar AAB e publicar em Internal Testing.
```

==================== FIM BLOCO 09 ====================

---

Ordem de uso: 01 -> 02 -> 03 -> 04 -> 05 -> 06 -> 07 -> 08 -> 09.
Depois de cada bloco 01..09, rode o bloco 00.
