import { Component } from '@angular/core';
import { AuthService, RegisterRequest } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  imports: [CommonModule, FormsModule],
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  form: RegisterRequest = {
    name: '',
    lastname: '',
    username: '',
    email: '',
    password: '',
    dob: ''
  };

  constructor(private auth: AuthService, private router: Router) {}

  submit() {
    this.auth.register(this.form).subscribe({
      next: () => this.router.navigate(['/home']),
      error: err => console.error(err)
    });
  }
}
