import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { apiConfig } from 'src/app/shared/utils/api.utils';

@Injectable({
  providedIn: 'root',
})
export class MediaService {
  private apiMediaImage = `${environment.apiBaseUrl}/medias/image_post`;
  private apiMediaVideo = `${environment.apiBaseUrl}/medias/video_post`;
  private apiMediaPost = `${environment.apiBaseUrl}/posts`;
  private apiMedia = `${environment.apiBaseUrl}/medias/post`;
  constructor(private http: HttpClient) {}
  fetchMediaImage(mediaUrl: string): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(
      `${this.apiMediaImage}/${mediaUrl}`,
      apiConfig
    );
  }
  fetchMediaVideo(mediaUrl: string): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(
      `${this.apiMediaVideo}/${mediaUrl}`,
      apiConfig
    );
  }
  hasMedia(postId: number): Observable<boolean> {
    return this.http
      .get<ApiResponse>(`${this.apiMedia}/${postId}`, apiConfig)
      .pipe(
        map((response: ApiResponse) => {
          if (response && response.data && response.data.media_responses) {
            return response.data.media_responses.length > 0;
          }
          return false;
        })
      );
  }
  getMediaByPostId(postId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(
      `${this.apiMedia}/${postId}`,
      apiConfig
    );
  }
}
