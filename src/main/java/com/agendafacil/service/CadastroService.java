package com.agendafacil.service;

import com.agendafacil.model.Usuario;
import com.agendafacil.repository.UsuarioRepository;

/**
 * Serviço responsável pelo cadastro de usuários.
 * Implementa as regras das HU01 (Cadastro de usuário).
 *
 * CA1 - Permitir cadastro com nome, e-mail e senha
 * CA2 - Validar e-mail já existente
 * CA3 - Exibir mensagem de sucesso (retorno do método)
 */
public class CadastroService {

    private final UsuarioRepository repository;

    public CadastroService(UsuarioRepository repository) {
        this.repository = repository;
    }

    /**
     * Cadastra um novo usuário.
     *
     * @return Usuario cadastrado com ID gerado
     * @throws IllegalArgumentException se algum campo for inválido ou e-mail já existir
     */
    public Usuario cadastrar(String nome, String email, String senha) {
        // CA1 - Validar campos obrigatórios
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter ao menos 6 caracteres.");
        }

        // CA2 - Verificar duplicidade
        if (repository.existePorEmail(email)) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        // Salvar e retornar (CA3 - sucesso implícito no retorno sem exceção)
        Usuario usuario = new Usuario(null, nome, email, senha);
        return repository.salvar(usuario);
    }
}
