import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  private header: HttpHeaders | undefined;

  constructor(private http: HttpClient) {
  }

  get(url:any): any {
    return this.http.get(environment.baseUrl + url).toPromise()
      .then((res:any)=>{
        if(res.refreshToken)
          localStorage.setItem("token",res.refreshToken);
      });
  }

  post(url:any, body:any): any {
    return this.http.post(environment.baseUrl + url, body).toPromise()
      .then((res:any)=>{
        if(res.refreshToken)
          localStorage.setItem("token",res.refreshToken);
      });
  }

  patch(url:any, body:any): any {
    return this.http.patch(environment.baseUrl + url, body).toPromise()
      .then((res:any)=>{
        if(res.refreshToken)
          localStorage.setItem("token",res.refreshToken);
      });
  }

  delete(url:any): any {
    return this.http.delete(environment.baseUrl + url).toPromise()
      .then((res:any)=>{
        if(res.refreshToken)
          localStorage.setItem("token",res.refreshToken);
      });
  }

}
