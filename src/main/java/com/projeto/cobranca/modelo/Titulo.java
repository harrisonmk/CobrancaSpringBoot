package com.projeto.cobranca.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Titulo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob //para o banco aceitar textos muito grandes
    @NotBlank(message= "Descricao eh obrigatoria") //campo nao pode ser vazio ou nulo
    @Size(max=60,message="A descricao nao pode conter mais de 60 caracteres") 
    private String descricao;

    @NotNull(message="Data de vencimento eh obrigatorio")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(javax.persistence.TemporalType.DATE) //coloca tipo date no banco de dados
    private Date dataVencimento;

    @NotNull(message="Valor eh obrigatorio")
    @DecimalMin(value="0.01",message="Valor nao pode ser menor que 0,1")
    @DecimalMax(value="9999999.99",message="valor nao pode ser maior que 9.999.999.99")
    @NumberFormat(pattern = "#,##0.00") //para definir as casas decimais com virgula
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "titulo_status")
    private StatusTitulo status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public StatusTitulo getStatus() {
        return status;
    }

    public void setStatus(StatusTitulo status) {
        this.status = status;
    }

    
}
