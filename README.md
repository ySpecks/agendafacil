# AgendaFácil

Projeto desenvolvido em Java para simular um sistema simples de agendamento de serviços. O sistema permite cadastrar usuários, realizar login e efetuar agendamentos.

## Tecnologias utilizadas

- Java 17
- JUnit 5
- Maven

## Casos de teste implementados

### HU01 – Cadastro de Usuário
Testes responsáveis por validar o processo de cadastro de usuários.

### HU02 – Login
Testes para verificar autenticação de usuários cadastrados e validar cenários de regressão.

### HU03 – Agendamento
Testes de sistema e ponta a ponta (E2E) para validar o fluxo completo de agendamento.

### Casos de teste

| Nome do Teste | Caso |
|---------------|------|
| sistemaDeveRejeitarDataNoPassado | CA2 - Data passada é rejeitada |

## Como executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.8 ou superior

### Executar todos os testes

```bash
mvn test
```

### Executar testes específicos

```bash
mvn test -Dtest=CadastroServiceTest
```

```bash
mvn test -Dtest=LoginServiceTest
```

```bash
mvn test -Dtest=AgendamentoServiceTest
```

## Estrutura do projeto

```text
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
    │       ├── CadastroService.java
    │       ├── LoginService.java
    │       └── AgendamentoService.java
    └── test/java/com/agendafacil/
        ├── CadastroServiceTest.java
        ├── LoginServiceTest.java
        └── AgendamentoServiceTest.java
```

## Autor

Projeto desenvolvido por **ySpecks**.
