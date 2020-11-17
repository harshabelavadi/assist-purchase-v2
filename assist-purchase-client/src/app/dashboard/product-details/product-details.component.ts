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
  meanStats: any;

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
          if (this.ignoreRemainingLoop(element, product)) {
            continue;
          }
          this.insertProductDetails(element, product);
          this.putBooleanEquivalentStringValues(element, product);
        }
        this.setImageDisplayFlag();
      });
    });
  }

  setImageDisplayFlag(): void {
    if (this.imagename) {
      this.imagedisplayFlag = true;
    }
    else {
      this.imagedisplayFlag = false;
    }
  }
  putBooleanEquivalentStringValues(element: string, product: Product): void {
    if (!(element === Constants.PROD_DETAILS_KEYS.SIZE || element === Constants.PROD_DETAILS_KEYS.PRODUCT_NAME ||
      element === Constants.PROD_DETAILS_KEYS.DESCRIPTION)) {
        this.tableDatasource.push([Constants.PROD_DETAILS_DISP_VALUES[element], Constants.PROD_DETAILS_DISP_VALUES[product[element]]]);
      }
  }
  insertProductDetails(element: string, product: Product): void {
    if (element === Constants.PROD_DETAILS_KEYS.SIZE || element === Constants.PROD_DETAILS_KEYS.PRODUCT_NAME ||
      element === Constants.PROD_DETAILS_KEYS.DESCRIPTION) {
      this.tableDatasource.push([Constants.PROD_DETAILS_DISP_VALUES[element], product[element]]);
    }
  }

  ignoreRemainingLoop(element: string, product: Product): boolean {
    if (element === Constants.PROD_DETAILS_KEYS.PID || element === Constants.PROD_DETAILS_KEYS.STAFF) {
      return true;
    }
    if (element === Constants.PROD_DETAILS_KEYS.IMAGENAME) {
      this.imagename = product[element];
      return true;
    }
    return false;
  }

 getMonitorSaleStats(): void {
  this.salesService.getAll().subscribe((response: Sales[]) => {
    this.meanStats = {};
    for (const element of response){
      this.pushStatsData(element);
      this.getMeanStats(element);
    }
    this.validateStatsDataAndPushBenchmarkData();
    this.salesSubObservableService.sendSalesStats([this.statisticsdata, this.benchmarkdata]);
    });
  }

  validateStatsDataAndPushBenchmarkData(): void {
    if (this.statisticsdata.length === 0) {
      this.displayGraphFlag = false;
      return;
    }
    for (const element of Object.keys(this.meanStats)) {
      this.benchmarkdata.push([Number(element), Number((this.meanStats[element][0] / this.meanStats[element][1]).toFixed(2))]);
    }
  }

  getMeanStats(element: Sales): any {
    let milliseconds: number;
    milliseconds = new Date(element.salesDate).getTime();
    if (milliseconds in this.meanStats){
      this.meanStats[milliseconds][0] += element.salescount;
      this.meanStats[milliseconds][1] += 1;
    }
    else {
      this.meanStats[milliseconds] = [element.salescount, 0];
    }
  }

  pushStatsData(element: Sales): void {
    if (element.product.pid === this.productId) {
      this.statisticsdata.push([new Date(element.salesDate).getTime(), element.salescount]);
    }
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

