import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

export interface Product {
  codigo: number,
  descricao: string,
  data: Object
}

const urlBase = 'http://localhost:8080';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http: HttpClient) { }

  listProducts() {
    return this.http.get<Product[]>(`${urlBase}/api/produtos/consultar/todos`);
  }

  retrieveProduct(codigo: number) {
    return this.http.get<Product>(`${urlBase}/api/produtos/consultar/${codigo}`);
  }

  createProduct(product: Product) {
    return this.http.post<Product>(`${urlBase}/api/produtos/cadastrar/`, product);
  }

  updateProduct(product: Product) {
    return this.http.put<Product>(`${urlBase}/api/produtos/atualizar/${product.codigo}`, product)
  }

  deleteProduct(codigo: number) {
    return this.http.delete(`${urlBase}/api/produtos/remover/${codigo}`)
  }
}
