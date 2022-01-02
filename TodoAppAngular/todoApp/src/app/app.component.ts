import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { TokenStorageService } from './_service/token-storage.service';
import { RouterModule } from '@angular/router';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  isLoggedIn = false;
  showUser = false;
  x:number | undefined;
  username: string | undefined;
  @ViewChild('sidenav') matSidenav: any;

  
  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.x = Math.floor(Math.random() * (5 - 1) + 1);
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();

      var json = JSON.parse(user);
      var $: any;

      this.username ="Hosgeldin "+ json.username;
    }else{
    this.username = "Lutfen Giris Yap"
    }
  }
  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}

