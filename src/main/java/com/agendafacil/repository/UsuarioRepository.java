package com.agendafacil.repository;

import com.agendafacil.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório em memória de Usuários (simula banco de dados nos testes).
 */
public class UsuarioRepository {

    private final List<Usuario> banco = new ArrayList<>();
    private long proximoId = 1;

    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(proximoId++);
        }
        banco.add(usuario);
        return usuario;
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return banco.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean existePorEmail(String email) {
        return buscarPorEmail(email).isPresent();
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(banco);
    }

    public void limpar() {
        banco.clear();
        proximoId = 1;
    }
}
