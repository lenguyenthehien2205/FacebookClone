import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { apiConfig } from 'src/app/shared/utils/api.utils';

@Injectable({
  providedIn: 'root',
})
export class FriendService {
  constructor(private http: HttpClient) {}
  isFriend(profileId1: number, profileId2: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(
      `${environment.apiBaseUrl}/friends/is-friend/${profileId1}/${profileId2}`,
      apiConfig
    );
  }
}
