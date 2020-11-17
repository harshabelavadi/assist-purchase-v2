import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getLoginInfo(): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/users/`);
  }
}
