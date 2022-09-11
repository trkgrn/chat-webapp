import { Component, OnInit } from '@angular/core';
import {LayoutService} from "../../../services/layout.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  valCheck: string[] = ['remember'];

  password: any;
  constructor(public layoutService:LayoutService) { }

  ngOnInit(): void {
  }

}
