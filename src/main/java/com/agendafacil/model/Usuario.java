package com.agendafacil.model;

/**
 * Entidade Usuario — representa um usuário do sistema AgendaFácil.
 * Relacionada às HU01 (Cadastro) e HU02 (Login).
 */
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {}

    public Usuario(Long id, String nome, String email, String senha) {
        this.id    = id;
        this.nome  = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long   getId()    { return id; }
    public String getNome()  { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }

    public void setId(Long id)       { this.id    = id; }
    public void setNome(String nome) { this.nome  = nome; }
    public void setEmail(String e)   { this.email = e; }
    public void setSenha(String s)   { this.senha = s; }
}
