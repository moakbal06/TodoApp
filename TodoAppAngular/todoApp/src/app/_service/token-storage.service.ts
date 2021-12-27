import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const USER_NAME = 'user-name';
const REM = 'remember';



@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  signOut():void
  {
    window.sessionStorage.clear();
  }

  public saveToken(token :string):void
  {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY,token);

  }

  public getToken(): string 
  {
   return window.sessionStorage.getItem(TOKEN_KEY) as string;
  }

  public saveUser(user: any):void
  {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY,JSON.stringify(user));
  }

  public getUser():any{
   return window.sessionStorage.getItem(USER_KEY);
  }

  public setRemember(flag:string):void
  {
    window.localStorage.removeItem(REM);
    window.localStorage.setItem(REM,flag);
  }

  public saveName(name:string | null):void
  {
    window.localStorage.removeItem(USER_NAME);
    window.localStorage.setItem(USER_NAME,name as string);
  }
  public getName():any
  {
    return window.localStorage.getItem(USER_NAME);
  }
  public getRemember():any
  {
    return window.localStorage.getItem(REM);
  }
}
