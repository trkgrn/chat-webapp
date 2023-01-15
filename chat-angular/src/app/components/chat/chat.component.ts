import {AfterViewChecked, Component, ElementRef, OnInit} from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {environment} from "../../../environments/environment";
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
  destinationUser?: any;
  originUser: any;
  channelName?: string;
  socket?: WebSocket;
  stompClient?: Stomp.Client;
  newMessage = new FormControl('');
  messages?: Observable<Array<Message>>;
  chats?: Array<any>;
  filter: any;
  friendSearch: any;
  searchResult: Array<any> = [];

  displayChat: boolean = false;
  displayAddFriend: boolean = false;

  constructor(private authService: AuthService, private chatService: ChatService,
              private el: ElementRef, private route: ActivatedRoute) {
  }

  ngOnInit() {

    this.route.params.subscribe((params: any) => {
      this.chatInit()
      if (params.chatName) {
        this.messagesInit()
        this.showChat()
      } else {
        this.hiddenChat()
      }
    });
  }


  async addFriend(selectedUser: any) {
    var origin: any = await this.authService.getUser().toPromise();
    var friend: any = await this.chatService.addFriend(origin, selectedUser);
    var temp: any = await this.chatService.getCandidateFriendsByUsername(this.friendSearch, 0, 5);
    this.searchResult = temp;
    var chat: any = await this.chatService.getChats("getChats/").toPromise();
    this.chats = chat;
  }

  async findCandidateFriends() {
    if (this.friendSearch != "") {
      var temp: any = await this.chatService.getCandidateFriendsByUsername(this.friendSearch, 0, 5);
      this.searchResult = temp;
    }

  }

  addFriendPage() {
    this.displayAddFriend = true;
  }

  showChat() {
    this.displayChat = true;
  }

  hiddenChat() {
    this.displayChat = false;
  }

  getDestinationUser(chat: any) {
    if (chat.origin.username == this.originUser.username) {
      return chat.destination;
    }
    return chat.origin;
  }

  chatInit() {
    this.authService.getUser().subscribe((r: any) => {
      this.originUser = r
    })
    this.chatService.getChats("getChats/").subscribe((r: any) => {
      this.chats = r
    })

  }

  messagesInit() {
    this.authService.getUser().subscribe((res: any) => {
      this.originUser = res;

      let dest = "";

      this.route.params.subscribe((params: any) => {
        if (params.chatName) {
          const usernames = params.chatName.split('&');
          if (usernames[0] == this.originUser.username) {
            dest = usernames[1];
          } else {
            dest = usernames[0];
          }
        }
        this.authService.getUserByUsername(dest).subscribe((resp: any) => {
          this.destinationUser = resp;
          this.connectToChat();
          this.el.nativeElement.querySelector("#chat").scrollIntoView();
        });
      });
    });
  }

  ngAfterViewChecked(): void {
    if (this.displayChat)
      this.scrollDown();
  }

  scrollDown() {
    var container = this.el.nativeElement.querySelector("#chat");
    container.scrollTop = container.scrollHeight;
  }


  connectToChat() {
    const id1 = this.originUser.userId!;
    const nick1 = this.originUser.username;
    const id2 = this.destinationUser?.userId!;
    const nick2 = this.destinationUser?.username!;
    if (id1 > id2) {
      this.channelName = nick1 + '&' + nick2;
    } else {
      this.channelName = nick2 + '&' + nick1;
    }
    this.loadChat();
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
      mgs.sort((a, b) => (a.messageId > b.messageId) ? 1 : -1)
      this.messages = of(mgs);
    })
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

  getImage(user:any) {
    if(user){
      var img = user.image;
      var base64Data = img.imageByte;
      var retrievedImage = 'data:image/jpeg;base64,' + base64Data;
     return  retrievedImage;
    }
    return null;
  }


}
