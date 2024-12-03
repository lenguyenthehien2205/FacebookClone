import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/app/environments/environment";
import { ApiResponse } from "src/app/features/auth/responses/api.response";

@Injectable({
    providedIn: 'root',
})
export class InteractionService {
    private interactionUrl = `${environment.apiBaseUrl}/interactions/post/detail`;
    private interactionByTypeUrl = `${environment.apiBaseUrl}/interactions/post`;
    private createHeaders(): HttpHeaders {
        return new HttpHeaders({
          'Content-Type': 'application/json',
          'Accept-Language': 'vi'
        });
      }
    private apiConfig = {
        headers: this.createHeaders()
      };
    constructor(private http: HttpClient) { }
    getInteractionPost(postId: number): Observable<ApiResponse> {
        return this.http.get<ApiResponse>(`${this.interactionUrl}/${postId}`, this.apiConfig);
    }
    getInteractionByTypePost(postId: number, interactionType: string): Observable<ApiResponse> {
      return this.http.get<ApiResponse>(`${this.interactionByTypeUrl}/${postId}/${interactionType}`, this.apiConfig);
  }
}