import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/shared/responses/api.response';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  private profileHeaderApiUrl = `${environment.apiBaseUrl}/profiles/header`;
  private profileApiUrl = `${environment.apiBaseUrl}/profiles`;
  private apiConfig = {
    headers: this.createHeaders(),
  };
  private createHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi',
    });
  }
  constructor(private http: HttpClient) {}
  getProfileHeaderByPathname(
    pathname: string,
    myProfileId: number
  ): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(
      `${this.profileHeaderApiUrl}/${pathname}/${myProfileId}`,
      this.apiConfig
    );
  }
  checkPathname(pathname: string): Observable<boolean>{
    return this.http.get<boolean>(`${this.profileHeaderApiUrl}/check-pathname/${pathname}`, this.apiConfig);
  }
  getFullname(pathname: string): Observable<ApiResponse>{
    return this.http.get<ApiResponse>(`${this.profileApiUrl}/fullname/${pathname}`, this.apiConfig);
  }
}
