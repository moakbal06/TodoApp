import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { UrlHelper } from '../_helpers/UrlHelper';


const AUTH_API = UrlHelper.BASE_URL+'/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {  

  constructor(private http:HttpClient) { }

  login(credentials: { username: any; password: any; }) : Observable<any>
  {
    
    return this.http.post(AUTH_API+'signin',{
      username:credentials.username,
      password:credentials.password
    },
    httpOptions);
  }

  register(user: { username: any; email: any; password: any; }) : Observable<any>
  {
    
    return this.http.post(AUTH_API+'signup',
    {username:user.username,
    email:user.email,
    password:user.password},
    httpOptions)

  }


}
