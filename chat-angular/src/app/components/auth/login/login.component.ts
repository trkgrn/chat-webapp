import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form:FormGroup;

  constructor(private formBuilder:FormBuilder, private authService:AuthService,
              private router:Router) {
    this.form = formBuilder.group({
      username:[null , Validators.required],
      password:[null, Validators.required]
    })
  }

  ngOnInit(): void {
    localStorage.removeItem("token");
  }

 async login()
  {
    let resp:any = await this.authService.login(this.form.value).toPromise();
    this.router.navigate(["/home"]);
  }
}
