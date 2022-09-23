import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Message} from "../model/message";
@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http:HttpClient) { }

  getMessages(url:any,channelName:any){
    return this.http.post<Array<Message>>(environment.baseUrl+url,channelName);
  }


}
