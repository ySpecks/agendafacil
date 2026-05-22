package com.agendafacil;

import com.agendafacil.model.Usuario;
import com.agendafacil.repository.UsuarioRepository;
import com.agendafacil.service.CadastroService;
import com.agendafacil.service.LoginService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * =========================================================
 * TESTES DE UNIDADE + REGRESSÃO — HU02: Login
 * =========================================================
 * Tipo: Teste de Unidade (Unit Test) e Teste de Regressão
 *
 * Critérios de Aceitação cobertos:
 *  CA1 - Permitir login com e-mail e senha
 *  CA2 - Exibir erro para dados inválidos
 *  CA3 - Redirecionar após login (usuário retornado)
 *
 * Os testes de regressão garantem que alterações no CadastroService
 * não quebrem o fluxo de login.
 *
 * Integrante responsável pelo commit: [Nome do Integrante 2]
 * =========================================================
 */
@DisplayName("HU02 - Login")
class LoginServiceTest {

    private UsuarioRepository repository;
    private CadastroService   cadastroService;
    private LoginService      loginService;

    @BeforeEach
    void setUp() {
        repository      = new UsuarioRepository();
        cadastroService = new CadastroService(repository);
        loginService    = new LoginService(repository);

        // Pré-condição: usuário cadastrado para os testes de login
        cadastroService.cadastrar("Carlos Souza", "carlos@email.com", "minhasenha");
    }

    @AfterEach
    void tearDown() {
        repository.limpar();
    }

    // -------------------------------------------------------
    // TESTE 1 — CA1 + CA3: Login bem-sucedido
    // -------------------------------------------------------
    @Test
    @DisplayName("CA1+CA3 | Deve autenticar usuário com credenciais válidas")
    void deveAutenticarComCredenciaisValidas() {
        // Act
        Usuario autenticado = loginService.autenticar("carlos@email.com", "minhasenha");

        // Assert
        assertNotNull(autenticado,                               "Usuário não deve ser nulo");
        assertEquals("Carlos Souza",      autenticado.getNome(), "Nome deve corresponder");
        assertEquals("carlos@email.com",  autenticado.getEmail(),"E-mail deve corresponder");
    }

    // -------------------------------------------------------
    // TESTE 2 — CA2: Credenciais inválidas — senha errada
    // -------------------------------------------------------
    @Test
    @DisplayName("CA2 | Deve lançar SecurityException para senha incorreta")
    void deveRejeitarSenhaIncorreta() {
        SecurityException ex = assertThrows(
                SecurityException.class,
                () -> loginService.autenticar("carlos@email.com", "senhaerrada"),
                "Senha incorreta deve lançar SecurityException"
        );
        assertEquals("Credenciais inválidas.", ex.getMessage());
    }

    // -------------------------------------------------------
    // TESTE 3 — CA2: Credenciais inválidas — e-mail inexistente
    // -------------------------------------------------------
    @Test
    @DisplayName("CA2 | Deve lançar SecurityException para e-mail não cadastrado")
    void deveRejeitarEmailNaoCadastrado() {
        assertThrows(
                SecurityException.class,
                () -> loginService.autenticar("inexistente@email.com", "minhasenha"),
                "E-mail não cadastrado deve lançar SecurityException"
        );
    }

    // -------------------------------------------------------
    // TESTE 4 — REGRESSÃO: Cadastro seguido de login
    // Garante que alterações no CadastroService não quebram o Login
    // -------------------------------------------------------
    @Test
    @DisplayName("REGRESSÃO | Cadastro seguido de login deve funcionar de ponta a ponta")
    void regressao_cadastroSeguidoDeLogin() {
        // Arrange
        cadastroService.cadastrar("Maria Fernandes", "maria@email.com", "senha456");

        // Act
        Usuario autenticado = loginService.autenticar("maria@email.com", "senha456");

        // Assert
        assertNotNull(autenticado);
        assertEquals("Maria Fernandes", autenticado.getNome());
    }

    // -------------------------------------------------------
    // TESTE 5 — CA1: Campos em branco devem ser rejeitados
    // -------------------------------------------------------
    @Test
    @DisplayName("CA1 | Deve lançar exceção para e-mail em branco")
    void deveRejeitarEmailEmBranco() {
        assertThrows(IllegalArgumentException.class,
                () -> loginService.autenticar("", "minhasenha"));
    }

    @Test
    @DisplayName("CA1 | Deve lançar exceção para senha em branco")
    void deveRejeitarSenhaEmBranco() {
        assertThrows(IllegalArgumentException.class,
                () -> loginService.autenticar("carlos@email.com", ""));
    }
}
