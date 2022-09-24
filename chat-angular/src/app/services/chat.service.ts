import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Message} from "../model/message";
import {AuthService} from "./auth.service";
@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http:HttpClient, private authService:AuthService, private httpService:HttpService) { }

  getMessages(url:any,channelName:any){
    return this.http.post<Array<Message>>(environment.baseUrl+url,channelName);
  }

  getChats(url:any){
    return  this.http.get(environment.baseUrl+url+this.authService.getToken());
  }


}
