package com.agendafacil;

import com.agendafacil.model.Usuario;
import com.agendafacil.repository.UsuarioRepository;
import com.agendafacil.service.CadastroService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * =========================================================
 * TESTES DE UNIDADE — HU01: Cadastro de Usuário
 * =========================================================
 * Tipo: Teste de Unidade (Unit Test)
 *
 * Critérios de Aceitação cobertos:
 *  CA1 - Permitir cadastro com nome, e-mail e senha
 *  CA2 - Validar e-mail já existente
 *  CA3 - Exibir mensagem de sucesso (retorno sem exceção + ID gerado)
 *
 * Integrante responsável pelo commit: [Nome do Integrante 1]
 * =========================================================
 */
@DisplayName("HU01 - Cadastro de Usuário")
class CadastroServiceTest {

    private UsuarioRepository repository;
    private CadastroService   cadastroService;

    @BeforeEach
    void setUp() {
        repository      = new UsuarioRepository();
        cadastroService = new CadastroService(repository);
    }

    @AfterEach
    void tearDown() {
        repository.limpar();
    }

    // -------------------------------------------------------
    // TESTE 1 — CA1 + CA3: Cadastro bem-sucedido
    // -------------------------------------------------------
    @Test
    @DisplayName("CA1+CA3 | Deve cadastrar usuário com dados válidos e retornar ID gerado")
    void deveCadastrarUsuarioComDadosValidos() {
        // Arrange
        String nome  = "Ana Lima";
        String email = "ana@email.com";
        String senha = "senha123";

        // Act
        Usuario salvo = cadastroService.cadastrar(nome, email, senha);

        // Assert
        assertNotNull(salvo.getId(),            "ID deve ser gerado automaticamente");
        assertEquals(nome,  salvo.getNome(),    "Nome deve ser persistido");
        assertEquals(email, salvo.getEmail(),   "E-mail deve ser persistido");
        assertEquals(1, repository.listarTodos().size(), "Banco deve conter exatamente 1 usuário");
    }

    // -------------------------------------------------------
    // TESTE 2 — CA2: Rejeitar e-mail duplicado
    // -------------------------------------------------------
    @Test
    @DisplayName("CA2 | Deve lançar exceção ao cadastrar e-mail já existente")
    void deveRejeitarEmailDuplicado() {
        // Arrange — cadastra primeiro usuário
        cadastroService.cadastrar("Ana Lima", "ana@email.com", "senha123");

        // Act + Assert — tenta cadastrar o mesmo e-mail
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> cadastroService.cadastrar("Outro Nome", "ana@email.com", "outrasenha"),
                "Deve lançar IllegalArgumentException para e-mail duplicado"
        );

        assertEquals("E-mail já cadastrado.", ex.getMessage());
        assertEquals(1, repository.listarTodos().size(), "Banco não deve ter registrado o segundo usuário");
    }

    // -------------------------------------------------------
    // TESTE 3 — CA1: Rejeitar campos inválidos
    // -------------------------------------------------------
    @Test
    @DisplayName("CA1 | Deve lançar exceção para nome em branco")
    void deveRejeitarNomeEmBranco() {
        assertThrows(IllegalArgumentException.class,
                () -> cadastroService.cadastrar("", "ana@email.com", "senha123"),
                "Nome em branco deve ser rejeitado");
    }

    @Test
    @DisplayName("CA1 | Deve lançar exceção para e-mail sem '@'")
    void deveRejeitarEmailSemArroba() {
        assertThrows(IllegalArgumentException.class,
                () -> cadastroService.cadastrar("Ana", "emailinvalido", "senha123"),
                "E-mail sem '@' deve ser rejeitado");
    }

    @Test
    @DisplayName("CA1 | Deve lançar exceção para senha com menos de 6 caracteres")
    void deveRejeitarSenhaCurta() {
        assertThrows(IllegalArgumentException.class,
                () -> cadastroService.cadastrar("Ana", "ana@email.com", "123"),
                "Senha com menos de 6 caracteres deve ser rejeitada");
    }
}
