import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/Product';
import { DashboardService } from 'src/app/service/dashboard.service';
import * as Constants from '../../models/constants';

@Component({
  selector: 'app-add-remove-products',
  templateUrl: './add-remove-products.component.html',
  styleUrls: ['./add-remove-products.component.css']
})
export class AddRemoveProductsComponent implements OnInit {
  productname: string = Constants.EMPTY;
  touchscreen: string = Constants.EMPTY;
  monitorsize: string = Constants.EMPTY;
  category: string = Constants.EMPTY;
  transportmonitor: string = Constants.EMPTY;
  selectedproductname: string = Constants.EMPTY;
  productsnameslist: string[] = [];
  categories: string[] = [];
  productList: Product[] = [];

  addErrorMsg = Constants.ADD_PRODUCT_ERROR;
  addErrorFlag = false;

  deleteErrorMsg = Constants.DELETE_PRODUCT_ERROR;
  deleteErrorFlag = false;

  constructor(private dashboardService: DashboardService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.getProductNames();
  }

  getProductNames(): void {
    this.dashboardService.getProductList().subscribe((products: Product[]) => {
      this.productList = products;
      this.productList.forEach(element => {
        const capitalizeCategory = element.category[0].toUpperCase() + element.category.slice(1);
        if (!this.categories.includes(capitalizeCategory)) {
          this.categories.push(capitalizeCategory);
        }
        if (!this.productsnameslist.includes(element.pname)) {
          this.productsnameslist.push(element.pname);
        }
      });
      this.productsnameslist.sort((num1, num2) => num1 < num2 ? -1 : num1 > num2 ? 1 : 0);
      this.categories.sort((category1, category2) => category1 < category2 ? -1 : category1 > category2 ? 1 : 0);
    });
    }

  validateAdd(): void{
      const newProduct: Product = new Product();

      if (this.productname === Constants.EMPTY || this.touchscreen === Constants.EMPTY || this.monitorsize === Constants.EMPTY ||
        this.category === Constants.EMPTY || this.transportmonitor === Constants.EMPTY) {
        this.addErrorFlag = true;
      }
      else {
        this.addErrorFlag = false;
        newProduct.pname = this.productname;
        newProduct.touchscreen = this.touchscreen === Constants.YES ? true : false;
        newProduct.transportMonitor = this.transportmonitor === Constants.YES ? true : false;
        newProduct.size = Number(this.monitorsize);
        newProduct.category = this.category.toLowerCase();

        this.dashboardService.addProduct(newProduct).subscribe();

        this.redirectToResultsView();
      }
  }

  validateDelete(): void {
    if (this.selectedproductname === Constants.EMPTY) {
      this.deleteErrorFlag = true;
    }
    else {
      this.deleteErrorFlag = false;
    }

    for (const element of this.productList) {
      if (element.pname === this.selectedproductname) {
        this.redirectToDeleteConfirmation(element.pid);
      }
    }
  }

  redirectToDeleteConfirmation(pid: number): void {
    this.router.navigate([Constants.NAVIGATION_CONSTS.FROM_ADDREMOVE_TO_REMCONFIRM, pid], { relativeTo: this.route });
  }

  redirectToResultsView(): void {
    this.router.navigate([Constants.NAVIGATION_CONSTS.FROM_ADDREMOVE_TO_RESVIEW, 0], { relativeTo: this.route });
  }
}
