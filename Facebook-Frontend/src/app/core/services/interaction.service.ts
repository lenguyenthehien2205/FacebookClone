import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { apiConfig } from 'src/app/shared/utils/api.utils';

@Injectable({
  providedIn: 'root',
})
export class InteractionService {
  private interactionUrl = `${environment.apiBaseUrl}/interactions/post/detail`;
  private interactionByTypeUrl = `${environment.apiBaseUrl}/interactions/post`;
  constructor(private http: HttpClient) {}
  getInteractionPost(postId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(
      `${this.interactionUrl}/${postId}`,
      apiConfig
    );
  }
  getInteractionByTypePost(
    postId: number,
    interactionType: string
  ): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(
      `${this.interactionByTypeUrl}/${postId}/${interactionType}`,
      apiConfig
    );
  }
}
