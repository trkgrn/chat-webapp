import { Component } from '@angular/core';
import {NotificationService} from "./services/notification.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'chat-angular';
  message:any;
  constructor(private notificationService: NotificationService) { }
  ngOnInit() {
    this.notificationService.requestPermission()
    this.notificationService.receiveMessage()
    this.message = this.notificationService.currentMessage
  }
}
