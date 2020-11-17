import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Product } from 'src/app/models/Product';
import { DashboardService } from 'src/app/service/dashboard.service';
import * as Constants from '../../../models/constants';

@Component({
  selector: 'app-purchased-view',
  templateUrl: './purchased-view.component.html',
  styleUrls: ['./purchased-view.component.css']
})
export class PurchasedViewComponent implements OnInit {
  message: string = Constants.EMPTY;
  feedbackMessage: string = Constants.EMPTY;
  routerSub: Subscription;

  constructor(private route: ActivatedRoute, private router: Router, private dashboardService: DashboardService) { }

  ngOnInit(): void {

    this.routerSub = this.route.paramMap.subscribe((response) => {
      const pid = Number(response.get(Constants.ID));
      this.dashboardService.getProductById(pid).subscribe((product: Product) => {
          this.message = Constants.PURCHASE_MESSAGE(pid.toString(), product.staff.staffname, product.staff.email, product.staff.mobile);
      });
    });
  }

  redirect(): void {
    this.router.navigate([Constants.NAVIGATION_CONSTS.DASHBOARD], { relativeTo: this.route });
  }
}
