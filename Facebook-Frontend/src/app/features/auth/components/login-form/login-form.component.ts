import { ChangeDetectionStrategy, Component, inject, output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginDTO } from '../../dtos/login.dto';
import { UserService } from 'src/app/core/services/user.service';
import { LoginResponse } from '../../responses/login.response';
import { TokenService } from 'src/app/core/services/token.service';
import { Router } from '@angular/router';
import { ApiResponse } from '../../responses/api.response';
@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginFormComponent {
  router = inject(Router);
  userService = inject(UserService);
  tokenService = inject(TokenService);
  createNewAccount = output<void>();
  form = new FormGroup({
    phoneNumber: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  onCreateNewAccount() {
    this.createNewAccount.emit();
  }

  onSubmit() {
    const formData = {
      phone_number: this.form.value.phoneNumber,
      password: this.form.value.password,
    };
    if (this.form.valid) {
      const loginDTO: LoginDTO = new LoginDTO(formData)
      this.userService.login(loginDTO).subscribe({
        next: (response: ApiResponse) => {
          this.tokenService.setToken(response.data.token);
          this.router.navigate(['/home']);  
        },
        error: (error: any) => {
          alert(error?.error?.message);    
        }
      })
    }
  }
}
