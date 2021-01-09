package com.afigueredo.demo.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

  /* Responsável pela serializacao e desserializacao do .class */
  private static final long serialVersionUID = -5754246207015712518L;

  private Long codigo;
  private String descricao;

  /* Construtor padrao */
  public Produto() {
  }

  @Id
  @Column(name = "codigo")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_seq")
  @SequenceGenerator(name = "produto_seq", sequenceName = "produto_seq", allocationSize = 1)
  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  @Column(name = "descricao", nullable = true)
  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  @Override
  public String toString() {
    return "Produto [codigo=" + codigo + ", descricao=" + descricao + "]";
  }

}
