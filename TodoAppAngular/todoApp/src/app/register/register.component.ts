import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {AuthService} from '../_service/auth.service'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form : any = {};
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  exhibitionId:any;

  constructor(private authService:AuthService,private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.exhibitionId = params['id'];

    });
  }

  onSubmit():void{

    this.authService.register(this.form).subscribe(
      data => {
        console.log(data);
        this.isSuccessful=true;
        this.isSignUpFailed=false;
        this.reloadPage();
    },
    err => {
      console.log(err);
      this.isSignUpFailed=true;
      this.isSuccessful=false;
      this.errorMessage = err.error.message;
    }
    );
  }
  reloadPage(): void {
    window.location.href = '/login?id='+this.exhibitionId;
  }
}
