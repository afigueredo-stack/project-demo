import { Component, OnInit } from '@angular/core';
import { Product, ProductsService } from '../product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  constructor(private productService: ProductsService) { }

  ngOnInit(): void {
    this.productService.listProducts().subscribe(({ data }) => {
      this.products = data;
    })
  }

  deleteProduct(product: Product) {
    this.productService.deleteProduct(product.codigo).subscribe(() => {
      const index = this.products.indexOf(product);
      this.products.splice(index, 1);
    })
  }

}
