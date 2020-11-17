import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sales } from '../models/sales';

@Injectable({
  providedIn: 'root'
})
export class SalesService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/sales/`);
  }

  getSalesById(salesid: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/sales/${salesid}`);
  } 

  addSales(sales: Sales): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/sales/`, sales);
  }

  updateSales(salesid: number, sales: Sales): Observable<any> {
    return this.http.put(`${this.baseUrl}/api/sales/${salesid}`, sales);
  }

  deleteSales(salesid: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/api/sales/${salesid}`, { responseType: 'text' });
  }
}
