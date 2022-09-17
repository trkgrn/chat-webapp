import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Router} from "@angular/router";
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  username: string | undefined;
  password: string | undefined;
  authURL: string = "http://localhost:8080/auth/"
  tokenURL: string = "http://localhost:8080/token/"

  constructor(private http: HttpClient, private router: Router) {
  }

  loggedIn = false;
  private header: HttpHeaders | undefined;


  signOut() {
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    this.router.navigateByUrl('login');
  }

  isUserSignedin() {
    return localStorage.getItem('username') !== null;
  }

  getUsername() {
    return localStorage.getItem('username') as string;
  }

  getToken() {
    return this.http.get(this.tokenURL+this.getUsername());
  }

  getRole() {
    let role = localStorage.getItem("role") as string;
    return role;
  }

  roleMatch(allowedRoles: any) {
    let isMatch = false;
    const userRole = this.getRole()

    if (userRole != null && userRole) {

      for (let j = 0; j < allowedRoles.length; j++) {
        if (userRole == allowedRoles[j]) {
          isMatch = true;
          return isMatch;
        } else {
          continue;
        }
      }

    }
    return isMatch
  }

  saveToken(token:any){
    let obj = {jwt:token, username:this.getUsername()}
   console.log(obj)
  return this.http.post<any>(this.tokenURL,obj);
  }

  login(user: any): Observable<object> {
    return this.http.post<any>(this.authURL + 'login', user, {headers: new HttpHeaders({'Content-Type': 'application/json'})}).pipe(map(async (resp) => {
      if (typeof user.username === "string") {
        localStorage.setItem('username', user.username);
        localStorage.setItem('role', resp.role);
      }
      return resp;
    }));
  }

  register(user: any) {
    return this.http.post<User>(this.authURL + "register", user);
  }

  getUser(name: string) {
    const headers = new HttpHeaders({'Authorization': 'Bearer ' + this.getToken()})
    return this.http.get(this.authURL + "getUser?username=" + name, {headers});
  }

  getAllUser() {
    const headers = new HttpHeaders({'Authorization': 'Bearer ' + this.getToken()})
    return this.http.get(this.authURL + "getAllUser", {headers});
  }

}
