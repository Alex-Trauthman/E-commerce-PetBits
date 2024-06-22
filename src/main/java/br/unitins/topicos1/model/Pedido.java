package br.unitins.topicos1.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido extends DefaultEntity {

    private LocalDateTime dataExpiracao;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    private String status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_racaopedido")
    private List<RacaoPedido> racao;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_brinquedopedido")
    private List<BrinquedoPedido> brinquedo;

    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_remediopedido")
    private List<RemedioPedido> remedio;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_petiscopedido")
    private List<PetiscoPedido> petisco;
    
    public List<RacaoPedido> getRacao() {
        return racao;
    }

    public void setRacao(List<RacaoPedido> racao) {
        this.racao = racao;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public List<BrinquedoPedido> getBrinquedo() {
        return brinquedo;
    }

    public void setBrinquedo(List<BrinquedoPedido> brinquedo) {
        this.brinquedo = brinquedo;
    }

    public List<RemedioPedido> getRemedio() {
        return remedio;
    }

    public void setRemedio(List<RemedioPedido> remedio) {
        this.remedio = remedio;
    }

    public List<PetiscoPedido> getPetisco() {
        return petisco;
    }

    public void setPetisco(List<PetiscoPedido> petisco) {
        this.petisco = petisco;
    }
    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

   

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}