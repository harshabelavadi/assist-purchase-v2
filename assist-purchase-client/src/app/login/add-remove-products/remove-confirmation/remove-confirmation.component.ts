import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Product } from 'src/app/models/Product';
import { DashboardService } from 'src/app/service/dashboard.service';
import * as Constants from '../../../models/constants';

@Component({
  selector: 'app-remove-confirmation',
  templateUrl: './remove-confirmation.component.html',
  styleUrls: ['./remove-confirmation.component.css']
})
export class RemoveConfirmationComponent implements OnInit {
  tableDatasource: Array<any> = new Array();
  routerSub: Subscription;
  productDetails: Product = new Product();
  productId: number;
  imagename: string = Constants.EMPTY;
  imagedisplayFlag = true;

  constructor(private dashboardService: DashboardService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.routerSub = this.route.paramMap.subscribe((response) => {
      this.productId = Number(response.get(Constants.ID));
      this.dashboardService.getProductById(this.productId).subscribe((product: Product) => {
        for (const element in product) {
          if (element === Constants.PROD_DETAILS_KEYS.PID || element === Constants.PROD_DETAILS_KEYS.STAFF) {
            continue;
          }
          if (element === Constants.PROD_DETAILS_KEYS.IMAGENAME) {
            this.imagename = product[element];
            continue;
          }
          if (element === Constants.PROD_DETAILS_KEYS.SIZE || element === Constants.PROD_DETAILS_KEYS.PRODUCT_NAME) {
            this.tableDatasource.push([Constants.PROD_DETAILS_DISP_VALUES[element], product[element]]);
          }
          else {
            this.tableDatasource.push([Constants.PROD_DETAILS_DISP_VALUES[element], Constants.PROD_DETAILS_DISP_VALUES[product[element]]]);
          }
        }

        if (this.imagename) {
          this.imagedisplayFlag = true;
        }
        else {
        this.imagedisplayFlag = false;
        }
      });
    });
  }

  delete(decision: number): void {
    if (decision) {
      this.dashboardService.deleteProduct(this.productId).subscribe();
      this.router.navigate([Constants.NAVIGATION_CONSTS.FROM_REMOVECONFIRM_TO_RESVIEW, 1], { relativeTo: this.route });
    }
    else {
    this.router.navigate([Constants.NAVIGATION_CONSTS.DASHBOARD], { relativeTo: this.route });
    }
  }

}
