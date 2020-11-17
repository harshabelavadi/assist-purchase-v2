import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/Product';
import { DashboardService } from 'src/app/service/dashboard.service';

@Component({
  selector: 'app-card-view',
  templateUrl: './card-view.component.html',
  styleUrls: ['./card-view.component.css']
})
export class CardViewComponent implements OnInit {
  @Input() productList: Product[];
  imagename = {};

  constructor(private dashboardService: DashboardService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.dashboardService.getProductList().subscribe((response: Product[]) => {
      for (const element of response) {
        this.imagename[element.pid] = element.imgname;
      }
    });
  }

  detailsRedirect(path: string, id: number): void {
    this.router.navigate([path, id], { relativeTo: this.route });
    }

  isImageAvailable(pid: number): boolean{
    if (this.imagename[pid]) {
      return true;
    }
    return false;
  }
}
