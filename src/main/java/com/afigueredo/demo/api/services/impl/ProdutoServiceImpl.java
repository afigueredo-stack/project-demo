package com.afigueredo.demo.api.services.impl;

import java.util.List;
import java.util.Optional;

import com.afigueredo.demo.api.entities.Produto;
import com.afigueredo.demo.api.repositories.ProdutoRepository;
import com.afigueredo.demo.api.services.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {

  @Autowired
  ProdutoRepository produtoRepository;

  public Produto persistir(Produto produto) {
    return this.produtoRepository.save(produto);
  }

  public List<Produto> buscarTodos() {
    return this.produtoRepository.findAll();
  }

  public Optional<Produto> buscarPorCodigo(Long codigo) {
    return Optional.of(this.produtoRepository.findByCodigo(codigo));
  }

  public Optional<Produto> buscarPorDescricao(String descricao) {
    return Optional.of(this.produtoRepository.findByDescricao(descricao));
  }

  @Override
  public void remover(Long codigo) {
    this.produtoRepository.deleteById(codigo);
  }

}
