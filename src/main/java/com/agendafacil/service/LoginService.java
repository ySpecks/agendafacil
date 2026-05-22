package com.agendafacil.service;

import com.agendafacil.model.Usuario;
import com.agendafacil.repository.UsuarioRepository;

/**
 * Serviço responsável pela autenticação de usuários.
 * Implementa as regras da HU02 (Login).
 *
 * CA1 - Permitir login com e-mail e senha
 * CA2 - Exibir erro para dados inválidos
 * CA3 - Redirecionar após login (retorno do usuário autenticado)
 */
public class LoginService {

    private final UsuarioRepository repository;

    public LoginService(UsuarioRepository repository) {
        this.repository = repository;
    }

    /**
     * Autentica o usuário com e-mail e senha.
     *
     * @return Usuario autenticado (CA3 - sinaliza redirecionamento)
     * @throws SecurityException    se credenciais forem inválidas (CA2)
     * @throws IllegalArgumentException se campos estiverem em branco
     */
    public Usuario autenticar(String email, String senha) {
        // CA1 - Verificar campos
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail é obrigatório.");
        }
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Senha é obrigatória.");
        }

        // CA2 - Validar credenciais
        Usuario usuario = repository.buscarPorEmail(email)
                .orElseThrow(() -> new SecurityException("Credenciais inválidas."));

        if (!usuario.getSenha().equals(senha)) {
            throw new SecurityException("Credenciais inválidas.");
        }

        // CA3 - retorna usuário autenticado (controller usaria para redirecionar)
        return usuario;
    }
}
