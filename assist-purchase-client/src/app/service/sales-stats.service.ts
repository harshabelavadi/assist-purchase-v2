import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SalesStatsService {

  constructor() { }

  private salesDataSubject = new Subject<any>();

    sendSalesStats(salesStats: any): void {
        this.salesDataSubject.next(salesStats);
    }

    getSalesStats(): Observable<any> {
        return this.salesDataSubject.asObservable();
    }
}
