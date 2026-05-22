package com.agendafacil.repository;

import com.agendafacil.model.Agendamento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório em memória de Agendamentos (simula banco de dados nos testes).
 */
public class AgendamentoRepository {

    private final List<Agendamento> banco = new ArrayList<>();
    private long proximoId = 1;

    public Agendamento salvar(Agendamento agendamento) {
        if (agendamento.getId() == null) {
            agendamento.setId(proximoId++);
        }
        banco.add(agendamento);
        return agendamento;
    }

    public boolean horarioOcupado(LocalDateTime dataHora) {
        return banco.stream()
                .anyMatch(a -> a.getDataHora().equals(dataHora) && a.isConfirmado());
    }

    public List<Agendamento> listarHorariosDisponiveis(List<LocalDateTime> candidatos) {
        List<Agendamento> livres = new ArrayList<>();
        for (LocalDateTime dt : candidatos) {
            if (!horarioOcupado(dt)) {
                Agendamento slot = new Agendamento();
                slot.setDataHora(dt);
                livres.add(slot);
            }
        }
        return livres;
    }

    public Optional<Agendamento> buscarPorId(Long id) {
        return banco.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public List<Agendamento> listarTodos() {
        return new ArrayList<>(banco);
    }

    public void limpar() {
        banco.clear();
        proximoId = 1;
    }
}
