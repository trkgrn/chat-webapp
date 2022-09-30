import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form:FormGroup;

  constructor(private formBuilder:FormBuilder, private authService:AuthService,
              private router:Router,private messageService:MessageService) {
    this.form = formBuilder.group({
      username:[null , Validators.required],
      password:[null, Validators.required],
      mail:[null,Validators.required],
      name:[null,Validators.required],
      telNumber:[null,Validators.required],
      role:["KULLANICI",Validators.required]
    })
  }

  ngOnInit(): void {
  }

  register(){
    this.authService.register(this.form.value)
      .subscribe(data=>{
        this.messageService.add({severity: 'success', summary: 'Kayıt başarılı!',
          detail: 'Başarılı bir şekilde kaydınız tamamlandı. Giriş yapın.'});
        this.router.navigate(["/login"]);
      },error =>{
        this.messageService.add({severity: 'error', summary: 'Hatalı giriş!',
          detail: error.error});
      } )
  }

}
