package com.afigueredo.demo.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.afigueredo.demo.api.dtos.ProdutoDto;
import com.afigueredo.demo.api.entities.Produto;
import com.afigueredo.demo.api.response.Response;
import com.afigueredo.demo.api.services.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/produtos")
public class ProdutoController {

  @Autowired
  ProdutoService produtoService;

  @Value("${paginacao.qtd_por_pagina}")
  private int qtdPorPagina;

  public ProdutoController() {
  }

  /**
   * CREATE - Cadastra produto no sistema.
   * 
   * @param produtoDto
   * @param result
   * @return ResponseEntity<Response<ProdutoDto>>
   * @throws NoSuchAlgorithmException
   */
  @PostMapping(value = "/cadastrar")
  public ResponseEntity<Response<ProdutoDto>> create(@Valid @RequestBody ProdutoDto produtoDto, BindingResult result)
      throws NoSuchAlgorithmException {
    Response<ProdutoDto> response = new Response<ProdutoDto>();

    Produto produto = this.converterDtoParaProduto(produtoDto);

    if (result.hasErrors()) {
      System.err.printf("Dados inconsistentes, tente novamente.", result.getAllErrors());
      result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }

    this.produtoService.persistir(produto);

    response.setData(converterProdutoDto(produto));
    return ResponseEntity.ok(response);
  }

  /**
   * RETRIEVE - Retorna um produto pelo seu codigo.
   * 
   * @param codigo
   * @return ResponseEntity<Response<ProdutoDto>>
   */
  @GetMapping(value = "/consultar/{codigo}")
  public ResponseEntity<Response<ProdutoDto>> retrieve(@PathVariable("codigo") Long codigo) {
    Response<ProdutoDto> response = new Response<ProdutoDto>();
    Optional<Produto> produto = produtoService.buscarPorCodigo(codigo);

    if (!produto.isPresent()) {
      System.err.printf("Produto não encontrado. Codigo: {}", codigo);
      return ResponseEntity.badRequest().body(response);
    }

    response.setData(this.converterProdutoDto(produto.get()));
    return ResponseEntity.ok(response);
  }

  /**
   * RETRIEVE - Retorna todos os produtos.
   * 
   * @return ResponseEntity<Response<ProdutoDto[]>>
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping(value = "/consultar/todos")
  public ResponseEntity<Response<List<ProdutoDto>>> retrieve() {

    Response<List<ProdutoDto>> response = new Response<List<ProdutoDto>>();
    List<Produto> produtos = this.produtoService.buscarTodos();
    List<ProdutoDto> produtosDto = this.converterProdutosDtos(produtos);

    response.setData(produtosDto);
    return ResponseEntity.ok(response);
  }

  /**
   * UPDATE - Atualiza os dados de um produto.
   * 
   * @param codigo
   * @param produtoDto
   * @param result
   * @return ResponseEntity<Response<ProdutoDto>>
   * @throws NoSuchAlgorithmException
   */
  @PutMapping(value = "/atualizar/{codigo}")
  public ResponseEntity<Response<ProdutoDto>> update(@PathVariable("codigo") Long codigo,
      @Valid @RequestBody ProdutoDto produtoDto, BindingResult result) throws NoSuchAlgorithmException {
    Response<ProdutoDto> response = new Response<ProdutoDto>();

    Optional<Produto> produto = this.produtoService.buscarPorCodigo(codigo);
    if (!produto.isPresent()) {
      result.addError(new ObjectError("produto", "Produto não encontrado."));
    }

    this.atualizarDadosProduto(produto.get(), produtoDto, result);

    if (result.hasErrors()) {
      result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }

    this.produtoService.persistir(produto.get());
    response.setData(this.converterProdutoDto(produto.get()));

    return ResponseEntity.ok(response);
  }

  /**
   * DELETE - Remove um produto pelo codigo.
   * 
   * @param codigo
   * @return ResponseEntity<Response<Produto>>
   */
  @DeleteMapping(value = "/remover/{codigo}")
  public ResponseEntity<Response<String>> remover(@PathVariable("codigo") Long codigo) {
    Response<String> response = new Response<String>();
    Optional<Produto> produto = this.produtoService.buscarPorCodigo(codigo);

    if (!produto.isPresent()) {
      response.getErrors().add("Erro ao remover produto. Registro não encontrado para o codigo " + codigo);
      return ResponseEntity.badRequest().body(response);
    }

    this.produtoService.remover(codigo);
    return ResponseEntity.ok(new Response<String>());
  }

  /**
   * Atualiza os dados do produto com base nos dados encontrados no DTO.
   * 
   * @param produto
   * @param produtoDto
   * @param result
   * @throws NoSuchAlgorithmException
   */
  private void atualizarDadosProduto(Produto produto, @Valid ProdutoDto produtoDto, BindingResult result)
      throws NoSuchAlgorithmException {
    produto.setDescricao(produtoDto.getDescricao());
  }

  /**
   * Popula o DTO de cadastro para retornar ao response.
   * 
   * @param produto
   * @return ProdutoDto
   */
  private ProdutoDto converterProdutoDto(Produto produto) {
    ProdutoDto produtoDto = new ProdutoDto();
    produtoDto.setCodigo(produto.getCodigo());
    produtoDto.setDescricao(produto.getDescricao());

    return produtoDto;
  }

  private List<ProdutoDto> converterProdutosDtos(List<Produto> produtos) {
    List<ProdutoDto> listProdutoDto = new ArrayList<ProdutoDto>();
    produtos.forEach(produto -> {
      ProdutoDto produtosDto = new ProdutoDto();
      produtosDto.setCodigo(produto.getCodigo());
      produtosDto.setDescricao(produto.getDescricao());

      listProdutoDto.add(produtosDto);
    });

    return listProdutoDto;
  }

  /**
   * Converte os dados do DTP para produto.
   * 
   * @param produtoDto
   * @return Produto
   */

  private Produto converterDtoParaProduto(ProdutoDto produtoDto) {
    Produto produto = new Produto();
    produto.setCodigo(produtoDto.getCodigo());
    produto.setDescricao(produtoDto.getDescricao());

    return produto;
  }

}
