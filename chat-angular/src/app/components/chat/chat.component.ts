import {AfterViewChecked, Component, ElementRef, OnInit} from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {environment} from "../../../environments/environment";
import {User} from "../../model/user";
import {FormControl} from "@angular/forms";
import {Observable, of} from "rxjs";
import {AuthService} from "../../services/auth.service";
import {ChatService} from "../../services/chat.service";
import {ActivatedRoute} from "@angular/router";
import {Message} from "../../model/message";


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit, AfterViewChecked {
  destinationUser?: User;
  originUser: any;
  channelName?: string;
  socket?: WebSocket;
  stompClient?: Stomp.Client;
  newMessage = new FormControl('');
  messages?: Observable<Array<Message>>;

  constructor(private authService: AuthService, private chatService: ChatService,
              private el: ElementRef, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.authService.getUser().subscribe((res: any) => {
      this.originUser = res;
      console.log(this.originUser.id)
      if (this.originUser.id == 1) {
        console.log("a")
        var user = new User();
        user.id = 2;
        user.username = "abc"
        this.destinationUser = user;
        console.log("b")
      } else {
        var user = new User();
        user.id = 1;
        user.username = "trkgrn"
        this.destinationUser = user;
      }
      console.log("Test")
      this.connectToChat();
      console.log(this.el)
      this.el.nativeElement.querySelector("#chat").scrollIntoView();
    });

  }

  ngAfterViewChecked(): void {
    this.scrollDown();
  }

  scrollDown() {
    var container = this.el.nativeElement.querySelector("#chat");
    container.scrollTop = container.scrollHeight;
  }


  connectToChat() {
    const id1 = this.originUser.id!;
    const nick1 = this.originUser.username;
    const id2 = this.destinationUser?.id!;
    const nick2 = this.destinationUser?.username!;

    if (id1 > id2) {
      this.channelName = nick1 + '&' + nick2;
    } else {
      this.channelName = nick2 + '&' + nick1;
    }
    this.loadChat();
    console.log('connecting to chat...');
    this.socket = new SockJS(environment.baseUrl + 'chat');
    this.stompClient = Stomp.over(this.socket);

    this.stompClient.connect({}, (frame) => {
      //func = what to do when connection is established
      console.log('connected to: ' + frame);
      this.stompClient!.subscribe(
        '/topic/messages/' + this.channelName,
        (response) => {
          this.loadChat();
        }
      );
    });
  }



  loadChat() {
    this.messages = this.chatService.getMessages('getMessages', this.channelName)
    this.messages.subscribe(data => {
      let mgs: Array<Message> = data;
      mgs.sort((a, b) => (a.id > b.id) ? 1 : -1)
      this.messages = of(mgs);
    })
    console.log(this.messages);
  }

  sendMsg() {
    if (this.newMessage.value !== '') {
      this.stompClient!.send(
        '/app/chat/' + this.channelName,
        {},
        JSON.stringify({
          sender: this.originUser.username,
          t_stamp: 'to be defined in server',
          content: this.newMessage.value,
        })
      );
      this.newMessage.setValue('');
    }
  }

  whenWasItPublished(myTimeStamp: string) {
    const endDate = myTimeStamp.indexOf('-');
    return (
      myTimeStamp.substring(0, endDate) +
      ' at ' +
      myTimeStamp.substring(endDate + 1)
    );
  }


}
