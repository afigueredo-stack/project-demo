import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductsService } from '../product.service';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent {

  productForm = new FormGroup({
    descricao: new FormControl('', Validators.required)
  });

  constructor(private productService: ProductsService, private router: Router) { }

  createProduct(): void {
    this.productService.createProduct(this.productForm.value).subscribe(_ => {
      this.router.navigate(['/products'])
    })
  }


}
