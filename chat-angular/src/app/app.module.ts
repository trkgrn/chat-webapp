import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import {PasswordModule} from "primeng/password";
import {CheckboxModule} from "primeng/checkbox";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";
import {InputTextModule} from "primeng/inputtext";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthService} from "./services/auth.service";
import {LoginGuard} from "./components/auth/login/login.guard";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import { HomeComponent } from './components/home/home.component';
import {HttpService} from "./services/http.service";
import {JwtInterceptor} from "./JwtInterceptor";
import { UnauthorizedComponent } from './components/error/unauthorized/unauthorized.component';
import { ForbiddenComponent } from './components/error/forbidden/forbidden.component';
import { NotfoundComponent } from './components/error/notfound/notfound.component';
import { TestComponent } from './components/test/test.component';
import { ChatComponent } from './components/chat/chat.component';
import {DialogModule} from "primeng/dialog";
import {TableModule} from "primeng/table";
import {PaginatorModule} from "primeng/paginator";
import { NavbarComponent } from './components/navbar/navbar.component';
import {environment} from "../environments/environment";
import {AsyncPipe} from "@angular/common";
import {NotificationService} from "./services/notification.service";
import {AngularFireModule} from "@angular/fire/compat";
import {AngularFireMessagingModule} from "@angular/fire/compat/messaging";



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    UnauthorizedComponent,
    ForbiddenComponent,
    NotfoundComponent,
    TestComponent,
    ChatComponent,
    NavbarComponent
  ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        PasswordModule,
        CheckboxModule,
        FormsModule,
        ButtonModule,
        RippleModule,
        InputTextModule,
        ReactiveFormsModule,
        HttpClientModule,
        ToastModule,
        DialogModule,
        TableModule,
        PaginatorModule,
      AngularFireModule.initializeApp(environment.firebase),
      AngularFireMessagingModule
    ],
  providers: [AuthService,LoginGuard,MessageService,HttpService,AsyncPipe,NotificationService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },],
  bootstrap: [AppComponent]
})
export class AppModule { }
