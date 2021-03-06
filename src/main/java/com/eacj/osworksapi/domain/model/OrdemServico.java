
package com.eacj.osworksapi.domain.model;

import com.eacj.osworksapi.domain.ValidationGroups;
import com.eacj.osworksapi.domain.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Entity
public class OrdemServico {
    
    /*
        As validações nesta classe são desnecessárias uma vez que a sua 
        estruturação e validação é definida pela classe pela representação na API.
        Como a unica forma de acesso ao recurso é através da API, uma segunda validação,
        neste caso, seria redundante.
    */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /*
        Valid é utilizado para cascateamento da validação. Significa que devera 
        validar cliente de acordo com as validações descritas em cliente.
        ConvertGroup altera o grupo de validações padrão para um grupo de 
        validações específico. Nesta utilização, a validação só será validada nos
        campos que possuem uma validação do grupo ValidationGroups.ClienteId
    */
    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class)
    @NotNull
    @ManyToOne
    private Cliente cliente;
    
    @NotBlank
    private String descricao;
    
    @NotNull
    private BigDecimal preco;
    
    //Identifica que a entrada de dados da API deve ser negada. Ou seja, este atributo deve ser processado apenas pelo backend da aplicacao
    @JsonProperty(access = Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private StatusOrdemServico status;
    
    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataAbertura;
    
    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataFinalizacao;
    
    @OneToMany(mappedBy = "ordemServico")
    private List<Comentario> comentarios = new ArrayList<>();

    public boolean podeSerFinalizada(){
        return StatusOrdemServico.ABERTA.equals(getStatus());
    }
    
    public boolean naoPodeSerFinalizada(){
        return !podeSerFinalizada();
    }
    
    public void finalizar(){
        
        if(naoPodeSerFinalizada())
            throw new NegocioException("Ordem de servico não pode ser finalizada");
        
        setStatus(StatusOrdemServico.FINALIZADA);
        setDataFinalizacao(OffsetDateTime.now());
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public StatusOrdemServico getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemServico status) {
        this.status = status;
    }

    public OffsetDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(OffsetDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public OffsetDateTime getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(OffsetDateTime dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrdemServico other = (OrdemServico) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
