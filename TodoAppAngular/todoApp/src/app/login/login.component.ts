import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_service/auth.service';
import { TokenStorageService } from '../_service/token-storage.service';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {

    let val = this.tokenStorage.getRemember();
    if (val == "true" || val == true) {
      this.form.rememberMe = true;
    } else {
      this.form.rememberMe = false;
    }
    if (this.form.rememberMe == true) {
      this.form.username = this.tokenStorage.getName();
    }
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
   }
    this.activatedRoute.queryParams.subscribe(params => {
      // take params

    });
  }

  onSubmit(): void {
    this.authService.login(this.form).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);

        this.tokenStorage.setRemember(this.form.rememberMe);
 
        if (this.form.rememberMe == "false" || this.form.rememberMe == false) {
          this.tokenStorage.saveName(null);
        } else {
          this.tokenStorage.saveName(this.form.username);
        }
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.reloadPage();
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }
 
  reloadPage(): void {
    window.location.href = '/index.html';
  }
}