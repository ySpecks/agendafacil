package com.agendafacil.model;

import java.time.LocalDateTime;

/**
 * Entidade Agendamento — representa um agendamento de serviço.
 * Relacionada à HU03 (Agendamento de serviço).
 */
public class Agendamento {

    private Long id;
    private Usuario usuario;
    private String servico;
    private LocalDateTime dataHora;
    private boolean confirmado;

    public Agendamento() {}

    public Agendamento(Long id, Usuario usuario, String servico, LocalDateTime dataHora) {
        this.id        = id;
        this.usuario   = usuario;
        this.servico   = servico;
        this.dataHora  = dataHora;
        this.confirmado = false;
    }

    public Long          getId()         { return id; }
    public Usuario       getUsuario()    { return usuario; }
    public String        getServico()    { return servico; }
    public LocalDateTime getDataHora()   { return dataHora; }
    public boolean       isConfirmado()  { return confirmado; }

    public void setId(Long id)                     { this.id        = id; }
    public void setUsuario(Usuario u)              { this.usuario   = u; }
    public void setServico(String s)               { this.servico   = s; }
    public void setDataHora(LocalDateTime d)       { this.dataHora  = d; }
    public void setConfirmado(boolean confirmado)  { this.confirmado = confirmado; }
}
