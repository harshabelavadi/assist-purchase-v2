import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import * as Constants from '../models/constants';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username = Constants.EMPTY;
  password = Constants.EMPTY;
  errorMsg = Constants.LOGIN_ERROR;
  errorFlag = false;
  usersList: any;

  constructor(private route: ActivatedRoute, private router: Router, private loginService: LoginService) { }

  ngOnInit(): void {
    this.loginService.getLoginInfo().subscribe((response: any) => {
      this.usersList = response;
    });
  }

  redirect(path: string): void {
    this.router.navigate([path], { relativeTo: this.route });
    }

    validate(): void {
      for (const element of this.usersList){
        if (element.uname === this.username && element.password === this.password && element.admin === true) {
          this.redirect(Constants.NAVIGATION_CONSTS.FROM_LOGIN_TO_ADDREMOVE);
          this.errorFlag = false;
          return;
        }
        else {
          this.errorFlag = true;
        }
      }
    }
}
