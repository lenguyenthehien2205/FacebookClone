import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { apiConfig } from 'src/app/shared/utils/api.utils';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  private profileHeaderApiUrl = `${environment.apiBaseUrl}/profiles/header`;
  private profileApiUrl = `${environment.apiBaseUrl}/profiles`;
  mediaApiUrl = `${environment.apiBaseUrl}/medias`;
  currentProfileId = signal<number>(0);
  constructor(private http: HttpClient) {}
  getProfileHeaderByPathname(pathname: string, myProfileId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.profileHeaderApiUrl}/${pathname}/${myProfileId}`, apiConfig);
  }
  checkPathname(pathname: string): Observable<boolean>{
    return this.http.get<boolean>(`${this.profileHeaderApiUrl}/check-pathname/${pathname}`, apiConfig);
  }
  getInfo(pathname: string): Observable<ApiResponse>{
    return this.http.get<ApiResponse>(`${this.profileApiUrl}/info/${pathname}`, apiConfig);
  }
  getMediaImagesProfile(profile_id: number): Observable<ApiResponse>{
    return this.http.get<ApiResponse>(`${this.mediaApiUrl}/images/${profile_id}`, apiConfig);
  }
  getFriends(pathname: string, myProfileId: number){
    return this.http.get<ApiResponse>(`${this.profileApiUrl}/friends/${pathname}/${myProfileId}`, apiConfig);
  }
  searchProfiles(keyword: string) {
    return this.http.get<ApiResponse>(`${this.profileApiUrl}/search`, {
      params: { keyword: keyword}, // Truyền params vào request
      ...apiConfig // Giữ lại cấu hình khác nếu cần
    });
  }
}
