import { Component, OnInit } from '@angular/core';
import { Product } from '../models/Product';
import { DashboardService } from '../service/dashboard.service';
import * as Constants from '../models/constants';

@Component({
  selector: 'app-assist',
  templateUrl: './assist.component.html',
  styleUrls: ['./assist.component.css']
})
export class AssistComponent implements OnInit {
  size = null;
  touchscreen = Constants.EMPTY;
  category = Constants.EMPTY;
  transportMonitor = Constants.EMPTY;
  uniqueSizeList: number[] = [];
  results: Product[];
  categories: string[] = [];

  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.getItemsForDropDown();
  }

  getItemsForDropDown(): void {
    this.dashboardService.getProductList().subscribe((products: Product[]) => {
      products.forEach(element => {
        const capitalizeCategory = element.category[0].toUpperCase() + element.category.slice(1);
        if (!this.categories.includes(capitalizeCategory)) {
          this.categories.push(capitalizeCategory);
        }

        if (!this.uniqueSizeList.includes(element.size)) {
          this.uniqueSizeList.push(element.size);
        }
        });
      this.uniqueSizeList.sort((num1, num2) => num1 < num2 ? -1 : num1 > num2 ? 1 : 0);
      this.categories.sort((category1, category2) => category1 < category2 ? -1 : category1 > category2 ? 1 : 0);
    });
  }

  getMonitorList(): void {
    let transportMonitorBoolean = false;
    let touchscreenBoolean = false;
    this.results = [];
    if (this.areAllOptionsSelected())
    {
      transportMonitorBoolean = this.transportMonitor === Constants.YES ? true : false;
      touchscreenBoolean = this.touchscreen === Constants.YES ? true : false;
      this.category = this.category.toLowerCase();

      this.dashboardService.getProductListByFilter(touchscreenBoolean, this.size, this.category, transportMonitorBoolean)
      .subscribe((response: Product[]) => {
        this.results = response;
        this.results.sort((p1, p2) => p1.pname < p2.pname ? -1 : p1.pname > p2.pname ? 1 : 0);
      });
    }

    this.refreshAllProperties();

  }

  refreshAllProperties(): void{
    this.size = 0;
    this.touchscreen = Constants.EMPTY;
    this.category = Constants.EMPTY;
    this.transportMonitor = Constants.EMPTY;
  }

  areAllOptionsSelected(): boolean {
      if (this.touchscreen && this.size && this.category && this.transportMonitor) {
        return true;
      }
      return false;
  }

}
