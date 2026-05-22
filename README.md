<<<<<<< HEAD
# AgendaFácil 📅

Sistema de Agendamento de Serviços — Projeto acadêmico com testes automatizados em JUnit 5.

---

## 📋 Sobre o Projeto

O **AgendaFácil** é um sistema de agendamento de serviços desenvolvido como materialização do Documento de Visão.  
O projeto cobre as seguintes Histórias de Usuário:

| HU    | Funcionalidade          |
|-------|------------------------|
| HU01  | Cadastro de Usuário    |
| HU02  | Login                  |
| HU03  | Agendamento de Serviço |

---

## 🧪 Testes Implementados

### HU01 – Cadastro de Usuário (`CadastroServiceTest`)
**Tipo:** Teste de Unidade

| Teste | CA Coberto | Descrição |
|-------|-----------|-----------|
| `deveCadastrarUsuarioComDadosValidos` | CA1, CA3 | Cadastro com dados válidos gera ID |
| `deveRejeitarEmailDuplicado` | CA2 | E-mail duplicado lança exceção |
| `deveRejeitarNomeEmBranco` | CA1 | Nome em branco é rejeitado |
| `deveRejeitarEmailSemArroba` | CA1 | E-mail inválido é rejeitado |
| `deveRejeitarSenhaCurta` | CA1 | Senha < 6 caracteres é rejeitada |

---

### HU02 – Login (`LoginServiceTest`)
**Tipo:** Teste de Unidade + Teste de Regressão

| Teste | CA Coberto | Descrição |
|-------|-----------|-----------|
| `deveAutenticarComCredenciaisValidas` | CA1, CA3 | Login correto retorna usuário |
| `deveRejeitarSenhaIncorreta` | CA2 | Senha errada lança SecurityException |
| `deveRejeitarEmailNaoCadastrado` | CA2 | E-mail inexistente lança SecurityException |
| `regressao_cadastroSeguidoDeLogin` | REGRESSÃO | Garante integração Cadastro→Login |
| `deveRejeitarEmailEmBranco` | CA1 | Campo vazio é rejeitado |
| `deveRejeitarSenhaEmBranco` | CA1 | Campo vazio é rejeitado |

---

### HU03 – Agendamento de Serviço (`AgendamentoServiceTest`)
**Tipo:** Teste de Sistema + Teste End-to-End (E2E)

| Teste | CA Coberto | Descrição |
|-------|-----------|-----------|
| `sistemaDeveListarTodosHorariosQuandoNenhumOcupado` | CA1 | Lista horários disponíveis |
| `sistemaDeveBloqueiarHorarioOcupado` | CA4 | Bloqueia horário já ocupado |
| `sistemaDeveCriarEConfirmarAgendamento` | CA2, CA3 | Cria e confirma agendamento |
| `sistemaDeveReduzirDisponibilidadeAposAgendamento` | CA1, CA4 | Disponibilidade reduz após ocupação |
| `e2e_fluxoCompletoDoUsuario` | **E2E** | Cadastro → Login → Agendamento completo |
| `sistemaDeveRejeitarDataNoPassado` | CA2 | Data passada é rejeitada |

---

## 🛠️ Tecnologias

- **Java 17**
- **JUnit 5** (Jupiter)
- **Maven**

---

## 🚀 Como executar

### Pré-requisitos
- Java 17+
- Maven 3.8+

### Rodar todos os testes
```bash
mvn test
```

### Rodar testes de uma HU específica
```bash
mvn test -Dtest=CadastroServiceTest      # HU01 - Unidade
mvn test -Dtest=LoginServiceTest         # HU02 - Unidade + Regressão
mvn test -Dtest=AgendamentoServiceTest   # HU03 - Sistema + E2E
```

---

## 📁 Estrutura do Projeto

```
agendafacil/
├── pom.xml
└── src/
    ├── main/java/com/agendafacil/
    │   ├── model/
    │   │   ├── Usuario.java
    │   │   └── Agendamento.java
    │   ├── repository/
    │   │   ├── UsuarioRepository.java
    │   │   └── AgendamentoRepository.java
    │   └── service/
    │       ├── CadastroService.java       ← HU01
    │       ├── LoginService.java          ← HU02
    │       └── AgendamentoService.java    ← HU03
    └── test/java/com/agendafacil/
        ├── CadastroServiceTest.java       ← Unidade
        ├── LoginServiceTest.java          ← Unidade + Regressão
        └── AgendamentoServiceTest.java    ← Sistema + E2E
```

---

## 👤 Integrante

| Integrante | Responsabilidade |
|-----------|-----------------|
| [ySpecks] | Desenvolvimento completo — models, repositories, services e todos os testes (HU01, HU02, HU03) |

---

## 📝 Como subir para o GitHub/GitLab

```bash
# Inicializar o repositório
git init
git add .
git commit -m "feat: projeto AgendaFácil com testes JUnit (HU01, HU02, HU03)"

# Criar repositório público no GitHub/GitLab e conectar
git remote add origin https://github.com/seu-usuario/agendafacil.git
git branch -M main
git push -u origin main
```
=======
# agendafacil
>>>>>>> 1162cc32b5b4fe6111197a97cb2094507eab83fa
