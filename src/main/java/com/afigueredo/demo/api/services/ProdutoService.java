package com.afigueredo.demo.api.services;

import java.util.List;
import java.util.Optional;

import com.afigueredo.demo.api.entities.Produto;

public interface ProdutoService {

  /**
   * Persiste o produto no banco de dados
   * 
   * @param produto
   * @return Produto
   */
  Produto persistir(Produto produto);

  /**
   * Busca todos os produtos
   * 
   * @param produto
   * @return Produto
   */
  List<Produto> buscarTodos();

  /**
   * Busca produto pelo codigo
   * 
   * @param produto
   * @return Produto
   */
  Optional<Produto> buscarPorCodigo(Long codigo);

  /**
   * Busca produto pela descricao
   * 
   * @param produto
   * @return Produto
   */
  Optional<Produto> buscarPorDescricao(String descricao);

  /**
   * Remove um produto da base de dados.
   * 
   * @param codigo
   */
  void remover(Long codigo);
}
