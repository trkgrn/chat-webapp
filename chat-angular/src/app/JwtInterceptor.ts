import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import {AuthService} from "./services/auth.service";


@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add auth header with jwt if account is logged in and request is to the api url
    const isLoggedIn = this.authService.getToken();
    if (isLoggedIn) {
      request = request.clone({
        setHeaders: { Authorization: `Bearer ${this.authService.getToken()}` }
      });
    }

    return next.handle(request);
  }
}
