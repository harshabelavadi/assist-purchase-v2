import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Product } from 'src/app/models/Product';
import { Sales } from 'src/app/models/sales';
import { DashboardService } from 'src/app/service/dashboard.service';
import { SalesStatsService } from 'src/app/service/sales-stats.service';
import { SalesService } from 'src/app/service/sales.service';
import * as Constants from '../../models/constants';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  routerSub: Subscription;
  tableDatasource: any[] = [];
  productId: number;
  statisticsdata: any[] = [];
  benchmarkdata: any[] = [];

  emailAddress: string = Constants.EMPTY;
  contactNumber: string = Constants.EMPTY;

  errorFlag = false;
  errorMsg: string;

  displayGraphFlag = true;
  imagename: string = Constants.EMPTY;
  imagedisplayFlag = false;

  constructor(private salesSubObservableService: SalesStatsService, private dashboardService: DashboardService,
              private salesService: SalesService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.routerSub = this.route.paramMap.subscribe((response) => {
      this.productId = Number(response.get(Constants.ID));
      this.getMonitorSaleStats();
      this.dashboardService.getProductById(this.productId).subscribe((product: Product) => {

        for (const element in product) {
          if (element === Constants.PROD_DETAILS_KEYS.PID || element === Constants.PROD_DETAILS_KEYS.STAFF) {
            continue;
          }
          if (element === Constants.PROD_DETAILS_KEYS.IMAGENAME) {
            this.imagename = product[element];
            continue;
          }
          if (element === Constants.PROD_DETAILS_KEYS.SIZE || element === Constants.PROD_DETAILS_KEYS.PRODUCT_NAME ||
              element === Constants.PROD_DETAILS_KEYS.DESCRIPTION) {
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

 getMonitorSaleStats(): void {
  this.salesService.getAll().subscribe((response: Sales[]) => {
    let milliseconds: number;
    const meanStats = {};
    for (const element of response){
      if (element.product.pid === this.productId) {
        this.statisticsdata.push([new Date(element.salesDate).getTime(), element.salescount]);
      }

      milliseconds = new Date(element.salesDate).getTime();
      if (milliseconds in meanStats){
        meanStats[milliseconds][0] += element.salescount;
        meanStats[milliseconds][1] += 1;
      }
      else {
        meanStats[milliseconds] = [element.salescount, 0];
      }
    }

    if (this.statisticsdata.length === 0) {
      this.displayGraphFlag = false;
      return;
    }

    for (const element of Object.keys(meanStats)) {
      this.benchmarkdata.push([Number(element), Number((meanStats[element][0] / meanStats[element][1]).toFixed(2))]);
    }
    this.salesSubObservableService.sendSalesStats([this.statisticsdata, this.benchmarkdata]);
    });
  }

  validate(): void {
    if (this.emailAddress === Constants.EMPTY || this.contactNumber === Constants.EMPTY) {
      this.errorFlag = true;
      this.errorMsg = Constants.EMPTYFIELDS_ERRORMSG;
    }
    else if (! this.isEmailAddressValid() ) {
      this.errorFlag = true;
      this.errorMsg = Constants.EMAIL_INVALID;
    }
    else if (! this.isContactNumberValid() ) {
      this.errorFlag = true;
      this.errorMsg = Constants.CONTACT_INVALID;
    }
    else
    {
      this.errorFlag = false;
      this.router.navigate([Constants.NAVIGATION_CONSTS.FROM_PRODUCT_DETAILS_TO_PURCHASE, this.productId], { relativeTo: this.route });
    }
  }

  isEmailAddressValid(): boolean {
    const regexPattern = new RegExp(Constants.EMAIL_VALIDATION);
    return regexPattern.test(this.emailAddress);
  }

  isContactNumberValid(): boolean {
    const regexPattern = new RegExp(Constants.CONTACT_VALIDATION);
    return regexPattern.test(this.contactNumber);
  }
}

