import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Router} from "@angular/router";
import {User} from "../model/user";
import {HttpService} from "./http.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  username: string | undefined;
  email:string | undefined;
  jwt:string | undefined;
  password: string | undefined;
  authURL: string = "http://localhost:8080/auth/"
  tokenURL: string = "http://localhost:8080/token/"
  userURL:string = "http://localhost:8080/user/"

  constructor(private http: HttpClient, private router: Router,
              private httpService:HttpService) {
  }

  loggedIn = false;
  private header: HttpHeaders | undefined;

  test(){
    return this.httpService.get("user/test");
  }


  signOut() {
    localStorage.removeItem('token');
    this.router.navigateByUrl('login');
  }

  isUserSignedin() {
    return localStorage.getItem('token') !== null;
  }

  // getUsername() {
  //   return localStorage.getItem('token') as string;
  // }

  getToken() {
    return localStorage.getItem('token') as string;
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


  login(user: any): Observable<object> {
    return this.http.post<any>(this.authURL + 'login', user, {headers: new HttpHeaders({'Content-Type': 'application/json'})}).pipe(map(async (resp) => {
      if (typeof user.username === "string") {
        localStorage.setItem('token', resp.token);
        localStorage.setItem('role',resp.role);
        this.jwt = resp.token;

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
