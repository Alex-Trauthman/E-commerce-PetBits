package br.unitins.topicos1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BrinquedoPedido extends DefaultEntity {

    private Double preco;
    private Double desconto;
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "id_brinquedo")
    private Brinquedo brinquedo;

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Brinquedo getBrinquedo() {
        return brinquedo;
    }

    public void setBrinquedo(Brinquedo brinquedo) {
        this.brinquedo = brinquedo;
    }

}