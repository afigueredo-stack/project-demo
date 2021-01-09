import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ProductsService } from '../product.service';

@Component({
  selector: 'app-product-update',
  templateUrl: './product-update.component.html',
  styleUrls: ['./product-update.component.css']
})
export class ProductUpdateComponent implements OnInit {

  productForm = new FormGroup({
    descricao: new FormControl('', Validators.required)
  });
  codigo: number;

  constructor(private productService: ProductsService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(paramMap => {
      this.codigo = parseInt(paramMap.get('id'));
      this.productService.retrieveProduct(this.codigo).subscribe(product => {
        console.log(product);
        this.productForm.reset(product.data);
      })
    })
  }

  updateProduct() {
    this.productService.updateProduct({ codigo: this.codigo, ...this.productForm.value })
      .subscribe(product => {
        this.productForm.reset(product.data);
      })
  }

}
