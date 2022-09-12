import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LayoutService} from "../../../services/layout.service";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form:FormGroup;

  constructor(public layoutService:LayoutService,private formBuilder:FormBuilder,
              private authService:AuthService) {
    this.form = formBuilder.group({
      username:[null , Validators.required],
      password:[null, Validators.required],
      mail:[null,Validators.required]
    })
  }

  ngOnInit(): void {
  }

  register(){
    console.log(this.form.value)
  }

}
