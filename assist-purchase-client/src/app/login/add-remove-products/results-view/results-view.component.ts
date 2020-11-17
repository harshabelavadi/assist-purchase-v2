import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import * as Constants from '../../../models/constants';

@Component({
  selector: 'app-results-view',
  templateUrl: './results-view.component.html',
  styleUrls: ['./results-view.component.css']
})
export class ResultsViewComponent implements OnInit {
  message: string;
  routerSub: Subscription;
  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.routerSub = this.route.paramMap.subscribe((response) => {
      const flag = Number(response.get(Constants.ID));
      if (flag) {
      this.message = Constants.PRODUCT_REMOVED;
      }
      else {
      this.message = Constants.PRODUCT_ADDED;
      }
  });
}

}
