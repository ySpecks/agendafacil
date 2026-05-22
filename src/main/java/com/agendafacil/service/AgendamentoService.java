package com.agendafacil.service;

import com.agendafacil.model.Agendamento;
import com.agendafacil.model.Usuario;
import com.agendafacil.repository.AgendamentoRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Serviço responsável pelo agendamento de serviços.
 * Implementa as regras da HU03 (Agendamento de serviço).
 *
 * CA1 - Exibir horários disponíveis
 * CA2 - Permitir selecionar data e hora
 * CA3 - Confirmar agendamento
 * CA4 - Bloquear horários ocupados
 */
public class AgendamentoService {

    private final AgendamentoRepository repository;

    public AgendamentoService(AgendamentoRepository repository) {
        this.repository = repository;
    }

    /**
     * CA1 - Retorna lista de horários disponíveis dentre os candidatos fornecidos.
     */
    public List<Agendamento> listarHorariosDisponiveis(List<LocalDateTime> candidatos) {
        if (candidatos == null || candidatos.isEmpty()) {
            throw new IllegalArgumentException("Lista de horários candidatos não pode ser vazia.");
        }
        return repository.listarHorariosDisponiveis(candidatos);
    }

    /**
     * CA2 + CA3 - Cria e confirma um agendamento para o usuário na data/hora informada.
     *
     * @throws IllegalArgumentException se parâmetros forem inválidos
     * @throws IllegalStateException    se horário já estiver ocupado (CA4)
     */
    public Agendamento agendar(Usuario usuario, String servico, LocalDateTime dataHora) {
        // CA2 - Validar seleção
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário é obrigatório.");
        }
        if (servico == null || servico.isBlank()) {
            throw new IllegalArgumentException("Serviço é obrigatório.");
        }
        if (dataHora == null) {
            throw new IllegalArgumentException("Data e hora são obrigatórias.");
        }
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar em data passada.");
        }

        // CA4 - Bloquear horário ocupado
        if (repository.horarioOcupado(dataHora)) {
            throw new IllegalStateException("Horário já está ocupado.");
        }

        // CA3 - Confirmar agendamento
        Agendamento agendamento = new Agendamento(null, usuario, servico, dataHora);
        agendamento.setConfirmado(true);
        return repository.salvar(agendamento);
    }
}
