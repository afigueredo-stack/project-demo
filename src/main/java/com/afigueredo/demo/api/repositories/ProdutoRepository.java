package com.afigueredo.demo.api.repositories;

import java.util.List;

import com.afigueredo.demo.api.entities.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

  List<Produto> findAll();

  Produto findByCodigo(Long codigo);

  Produto findByDescricao(String descricao);

}
