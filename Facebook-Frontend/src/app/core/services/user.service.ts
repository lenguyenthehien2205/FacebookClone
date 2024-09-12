import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterDTO } from 'src/app/features/auth/dtos/register.dto';
import { environment } from 'src/app/environments/environment';
import { LoginDTO } from 'src/app/features/auth/dtos/login.dto';

export interface User {
  id: number;
  username: string;
  avatar: string;
  isOnline: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiRegister = `${environment.apiBaseUrl}/users/register`;
  private apiLogin = `${environment.apiBaseUrl}/users/login`;
  private apiUrl = 'http://localhost:8088/api/v1/users'; // Thay thế bằng URL API thực tế
  private apiConfig = {
    headers: this.createHeaders()
  }
  private createHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi'
    });
  }

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl, this.apiConfig);
  }
  register(registerData: RegisterDTO):Observable<any>{
    const headers = this.createHeaders();
    return this.http.post(this.apiRegister, registerData, {headers});
  }
  login(loginData: LoginDTO):Observable<any>{
    const headers = this.createHeaders();
    return this.http.post(this.apiLogin, loginData, {headers});
  }
}