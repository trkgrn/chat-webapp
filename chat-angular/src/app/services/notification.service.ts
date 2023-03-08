import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {AngularFireMessaging} from "@angular/fire/compat/messaging";
import {MessageService} from "primeng/api";
import { getMessaging } from "@firebase/messaging";


@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  currentMessage = new BehaviorSubject(null);
  constructor(private angularFireMessaging: AngularFireMessaging,private messageService: MessageService) {
  }

  requestPermission() {

    this.angularFireMessaging.requestToken.subscribe(
      (token) => {
        if (typeof token === "string") {
          localStorage.setItem("notificationToken", token);
          console.log(token);
        }
      },
      (err) => {
        console.error('Unable to get permission to notify.', err);
      }
    );
  }

  receiveMessage() {
    this.angularFireMessaging.messages.subscribe(
      (payload:any) => {
        this.messageService.add({
          key: 'rootToast',
          severity: 'info', summary: payload.notification.title,
          detail: payload.notification.body
        })
        console.log("new message received. ", payload);
        this.currentMessage.next(payload);
      })
  }
}
