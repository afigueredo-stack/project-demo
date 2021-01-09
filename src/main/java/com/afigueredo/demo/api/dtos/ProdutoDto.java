package com.afigueredo.demo.api.dtos;

import org.hibernate.validator.constraints.Length;

public class ProdutoDto {

  private Long codigo;
  private String descricao;

  /* Construtor padrao */
  public ProdutoDto() {
  }

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  @Length(min = 3, max = 200, message = "Descricão deve possuir pelo menos 3 caracteres.")
  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  @Override
  public String toString() {
    return "ProdutoDto [codigo=" + codigo + ", descricao=" + descricao + "]";
  }

}
