import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterDTO } from 'src/app/features/components/auth/login/register/register.dto';
import { environment } from 'src/app/environments/environment';

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
    const headers = new HttpHeaders({
        'Content-Type': 'application/json'
    });
    return this.http.post(this.apiRegister, registerData, {headers});
  }
}