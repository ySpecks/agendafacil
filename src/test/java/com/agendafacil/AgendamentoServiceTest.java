package com.agendafacil;

import com.agendafacil.model.Agendamento;
import com.agendafacil.model.Usuario;
import com.agendafacil.repository.AgendamentoRepository;
import com.agendafacil.repository.UsuarioRepository;
import com.agendafacil.service.AgendamentoService;
import com.agendafacil.service.CadastroService;
import com.agendafacil.service.LoginService;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * =========================================================
 * TESTES DE SISTEMA + END-TO-END — HU03: Agendamento de Serviço
 * =========================================================
 * Tipo: Teste de Sistema e Teste End-to-End (E2E)
 *
 * Os testes de Sistema validam o comportamento isolado do
 * AgendamentoService com seus colaboradores reais (sem mocks).
 *
 * Os testes E2E simulam o fluxo completo do usuário:
 *   Cadastro → Login → Agendamento
 *
 * Critérios de Aceitação cobertos:
 *  CA1 - Exibir horários disponíveis
 *  CA2 - Permitir selecionar data e hora
 *  CA3 - Confirmar agendamento
 *  CA4 - Bloquear horários ocupados
 *
 * Integrante responsável pelo commit: [Nome do Integrante 3]
 * =========================================================
 */
@DisplayName("HU03 - Agendamento de Serviço")
class AgendamentoServiceTest {

    // Repositórios compartilhados (simula camada de persistência real)
    private UsuarioRepository    usuarioRepo;
    private AgendamentoRepository agendamentoRepo;

    // Services
    private CadastroService    cadastroService;
    private LoginService       loginService;
    private AgendamentoService agendamentoService;

    // Horários fixos para os testes (sempre no futuro)
    private static final LocalDateTime HORARIO_A = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
    private static final LocalDateTime HORARIO_B = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);
    private static final LocalDateTime HORARIO_C = LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0);

    @BeforeEach
    void setUp() {
        usuarioRepo        = new UsuarioRepository();
        agendamentoRepo    = new AgendamentoRepository();
        cadastroService    = new CadastroService(usuarioRepo);
        loginService       = new LoginService(usuarioRepo);
        agendamentoService = new AgendamentoService(agendamentoRepo);
    }

    @AfterEach
    void tearDown() {
        usuarioRepo.limpar();
        agendamentoRepo.limpar();
    }

    // -------------------------------------------------------
    // TESTE SISTEMA 1 — CA1: Listar horários disponíveis
    // -------------------------------------------------------
    @Test
    @DisplayName("SISTEMA | CA1 - Deve listar todos os horários quando nenhum está ocupado")
    void sistemaDeveListarTodosHorariosQuandoNenhumOcupado() {
        // Arrange
        List<LocalDateTime> candidatos = List.of(HORARIO_A, HORARIO_B, HORARIO_C);

        // Act
        List<Agendamento> disponiveis = agendamentoService.listarHorariosDisponiveis(candidatos);

        // Assert
        assertEquals(3, disponiveis.size(), "Todos os 3 horários devem estar disponíveis");
    }

    // -------------------------------------------------------
    // TESTE SISTEMA 2 — CA4: Bloquear horário já ocupado
    // -------------------------------------------------------
    @Test
    @DisplayName("SISTEMA | CA4 - Deve bloquear horário já ocupado por outro agendamento")
    void sistemaDeveBloqueiarHorarioOcupado() {
        // Arrange — ocupa HORARIO_A
        Usuario usuario = new Usuario(1L, "Teste", "teste@email.com", "senha123");
        agendamentoService.agendar(usuario, "Corte de Cabelo", HORARIO_A);

        // Act — tenta agendar no mesmo horário
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> agendamentoService.agendar(usuario, "Barba", HORARIO_A),
                "Deve lançar IllegalStateException para horário ocupado"
        );

        assertEquals("Horário já está ocupado.", ex.getMessage());
    }

    // -------------------------------------------------------
    // TESTE SISTEMA 3 — CA2 + CA3: Confirmar agendamento
    // -------------------------------------------------------
    @Test
    @DisplayName("SISTEMA | CA2+CA3 - Deve criar e confirmar agendamento com dados válidos")
    void sistemaDeveCriarEConfirmarAgendamento() {
        // Arrange
        Usuario usuario = new Usuario(1L, "Beatriz", "bia@email.com", "senha123");

        // Act
        Agendamento agendamento = agendamentoService.agendar(usuario, "Manicure", HORARIO_B);

        // Assert
        assertNotNull(agendamento.getId(),          "ID deve ser gerado");
        assertTrue(agendamento.isConfirmado(),       "Agendamento deve estar confirmado (CA3)");
        assertEquals("Manicure", agendamento.getServico(), "Serviço deve ser persistido");
        assertEquals(HORARIO_B, agendamento.getDataHora(), "Data/hora deve ser persistida");
    }

    // -------------------------------------------------------
    // TESTE SISTEMA 4 — CA1 + CA4: Horário disponível reduz após agendamento
    // -------------------------------------------------------
    @Test
    @DisplayName("SISTEMA | CA1+CA4 - Horário disponível deve reduzir após ser ocupado")
    void sistemaDeveReduzirDisponibilidadeAposAgendamento() {
        // Arrange
        Usuario usuario = new Usuario(1L, "Teste", "teste@email.com", "senha123");
        List<LocalDateTime> candidatos = List.of(HORARIO_A, HORARIO_B, HORARIO_C);

        // Ocupa HORARIO_A
        agendamentoService.agendar(usuario, "Corte", HORARIO_A);

        // Act
        List<Agendamento> disponiveis = agendamentoService.listarHorariosDisponiveis(candidatos);

        // Assert
        assertEquals(2, disponiveis.size(), "Apenas 2 horários devem restar disponíveis");
        assertTrue(disponiveis.stream().noneMatch(a -> HORARIO_A.equals(a.getDataHora())),
                "HORARIO_A não deve aparecer como disponível");
    }

    // -------------------------------------------------------
    // TESTE E2E — Fluxo completo: Cadastro → Login → Agendamento
    // -------------------------------------------------------
    @Test
    @DisplayName("E2E | Fluxo completo: Cadastro → Login → Agendamento deve funcionar")
    void e2e_fluxoCompletoDoUsuario() {
        // === PASSO 1: Cadastro (HU01) ===
        Usuario cadastrado = cadastroService.cadastrar(
                "João Silva", "joao@email.com", "senhaSegura");

        assertNotNull(cadastrado.getId(), "Usuário deve ser cadastrado com sucesso");

        // === PASSO 2: Login (HU02) ===
        Usuario autenticado = loginService.autenticar("joao@email.com", "senhaSegura");

        assertNotNull(autenticado, "Login deve retornar o usuário autenticado");
        assertEquals(cadastrado.getId(), autenticado.getId(), "IDs devem ser iguais");

        // === PASSO 3: Verificar horários disponíveis (HU03 - CA1) ===
        List<LocalDateTime> candidatos = List.of(HORARIO_A, HORARIO_B);
        List<Agendamento> disponiveis  = agendamentoService.listarHorariosDisponiveis(candidatos);

        assertFalse(disponiveis.isEmpty(), "Deve haver horários disponíveis");

        // === PASSO 4: Realizar agendamento (HU03 - CA2 + CA3) ===
        Agendamento agendamento = agendamentoService.agendar(
                autenticado, "Consulta", HORARIO_A);

        assertNotNull(agendamento.getId(),    "Agendamento deve ter ID gerado");
        assertTrue(agendamento.isConfirmado(),"Agendamento deve estar confirmado");
        assertEquals(autenticado.getId(), agendamento.getUsuario().getId(),
                "Agendamento deve pertencer ao usuário autenticado");

        // === PASSO 5: Verificar bloqueio (HU03 - CA4) ===
        assertThrows(IllegalStateException.class,
                () -> agendamentoService.agendar(autenticado, "Outro Serviço", HORARIO_A),
                "Horário já ocupado deve ser bloqueado após o agendamento");
    }

    // -------------------------------------------------------
    // TESTE SISTEMA 5 — CA2: Rejeitar data no passado
    // -------------------------------------------------------
    @Test
    @DisplayName("SISTEMA | CA2 - Deve rejeitar agendamento com data no passado")
    void sistemaDeveRejeitarDataNoPassado() {
        Usuario usuario = new Usuario(1L, "Teste", "teste@email.com", "senha123");
        LocalDateTime passado = LocalDateTime.now().minusDays(1);

        assertThrows(IllegalArgumentException.class,
                () -> agendamentoService.agendar(usuario, "Corte", passado),
                "Data passada deve ser rejeitada");
    }
}
