import { Component, OnInit } from '@angular/core';
import {LayoutService} from "../../../services/layout.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form:FormGroup;

  constructor(public layoutService:LayoutService,private formBuilder:FormBuilder,
              private authService:AuthService) {
    this.form = formBuilder.group({
      username:[null , Validators.required],
      password:[null, Validators.required]
    })
  }

  ngOnInit(): void {
  }

}
