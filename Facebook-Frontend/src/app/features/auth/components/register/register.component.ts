import { Component, inject, OnInit, output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { UserService } from 'src/app/core/services/user.service';
import { RegisterDTO } from '../../dtos/register.dto';
import { Router } from '@angular/router';

function emailOrPhoneValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    if (!value) {
      return null;  // Để Validators.required xử lý trường hợp này
    }

    // Regex cho email
    const emailPattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    
    // Regex cho số điện thoại 10 số bắt đầu bằng 0
    const phonePattern = /^0[0-9]{9}$/;

    if (emailPattern.test(value) || phonePattern.test(value)) {
      return null;  // Hợp lệ
    }

    return { emailOrPhone: true };  // Không hợp lệ
  };
}
function dateValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const day = control.get('day')?.value;
    const month = control.get('month')?.value;
    const year = control.get('year')?.value;

    if (!day || !month || !year) {
      return null; // Để Validators.required xử lý trường hợp này
    }

    // Kiểm tra năm nhuận
    const isLeapYear = (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0);
    
    // Mảng số ngày trong mỗi tháng
    const daysInMonth = [31, isLeapYear ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    // Kiểm tra tính hợp lệ của ngày trong tháng
    if (day < 1 || day > daysInMonth[month - 1]) {
      return { invalidDate: true };
    }

    // Kiểm tra tháng 2 năm nhuận
    if (month === 2 && day === 29 && !isLeapYear) {
      return { invalidLeapYear: true };
    }

    return null; // Hợp lệ
  };
}
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {
  userService = inject(UserService);
  router = inject(Router);
  registerForm!: FormGroup;
  onSubmit() {
    if (this.registerForm.valid) {
      const formData = {
        username: this.registerForm.value.firstName +" "+ this.registerForm.value.lastName,
        password: this.registerForm.value.password,
        avatar: "myduyen.jpg",
        phone_number: this.registerForm.value.phoneNumberOrEmail,
        role_id: 1
      }
      const registerDTO: RegisterDTO = new RegisterDTO(formData);
      this.userService.register(registerDTO).subscribe({
        next: (response: any) => {
          alert(response.message);
          // this.onCloseRegisterForm();
          this.router.navigate(['/home']);          
        },
        error: (error: any) => {          
          alert(`Cannot register, error: ${error.message}`);          
        }
    })   
    }
  }

  closeRegisterForm = output<void>();

  onCloseRegisterForm() {
    this.registerForm.reset({
      day: this.selectedDay,
      month: this.selectedMonth,
      year: this.selectedYear
    });
    this.closeRegisterForm.emit();
  }

  days: number[] = [];
  months: { value: number; name: string }[] = [];
  years: number[] = [];
  
  selectedDay: number = 0;
  selectedMonth: number = 0;
  selectedYear: number = 0;

  ngOnInit() {
    const today = new Date();
    this.selectedDay = today.getDate();
    this.selectedMonth = today.getMonth() + 1; // getMonth() trả về 0-11
    this.selectedYear = today.getFullYear();

    this.generateDays();
    this.generateMonths();
    this.generateYears();
    this.registerForm = new FormGroup({
      firstName: new FormControl('', [
        Validators.required,
        Validators.pattern(/^[a-zA-ZÀ-ỹ\s-]+$/) // Chữ cái, khoảng trắng, dấu gạch ngang
      ]),
      lastName: new FormControl('', [
        Validators.required,
        Validators.pattern(/^[a-zA-ZÀ-ỹ\s-]+$/) // Chữ cái, khoảng trắng, dấu gạch ngang
      ]),
      phoneNumberOrEmail: new FormControl('', [Validators.required, emailOrPhoneValidator()]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      gender: new FormControl('', [Validators.required]),
      day: new FormControl(this.selectedDay, [Validators.required]),
      month: new FormControl(this.selectedMonth, [Validators.required]),
      year: new FormControl(this.selectedYear, [Validators.required]),
    },
    { validators: dateValidator() }
  );
  }

  generateDays() {
    for (let i = 1; i <= 31; i++) {
      this.days.push(i);
    }
  }

  generateMonths() {
    const monthNames = [
      'Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
      'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'
    ];
    this.months = monthNames.map((name, index) => ({ value: index + 1, name }));
  }

  generateYears() {
    const currentYear = new Date().getFullYear();
    for (let i = currentYear; i >= currentYear - 100; i--) {
      this.years.push(i);
    }
  }
}
