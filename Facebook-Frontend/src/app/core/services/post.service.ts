import { Injectable, signal } from '@angular/core';
import { filter, map, Observable } from 'rxjs';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/features/auth/responses/api.response';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Post } from '../models/post.model';
import { Media } from '../models/media.model';
import { PostFetchData } from '../models/post-fetch-data.model';
@Injectable({
  providedIn: 'root',
})
export class PostService {
  private apiPosts = `${environment.apiBaseUrl}/posts/random-authors-latest`;
  private apiConfig = {
    headers: this.createHeaders()
  };

  private fetchedIds: Set<number> = new Set();
  posts: Post[] = [];

  constructor(private http: HttpClient) { }

  private createHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi'
    });
  }

  fetchPosts(postFetchData: PostFetchData): Observable<ApiResponse> {
    console.log(this.posts);
    return this.http.post<ApiResponse>(this.apiPosts, postFetchData, this.apiConfig).pipe(
      map((response: ApiResponse) => {
        if (response && response.data.length > 0 && Array.isArray(response.data)) {
          // Lọc ra các bài viết chưa được hiển thị
          response.data = (response.data as Post[]).filter(post => !this.fetchedIds.has(post.post_id));
        }
        // Nếu không còn bài viết nào sau khi lọc
        if (response.data.length === 0) {
          return { 
            message: 'No new posts available', 
            status: '404', 
            data: [] 
          }; // Trả về ApiResponse với thông điệp
        }
        return response;
      })
    );
  }

  updateFetchedIds(newIds: number[]) {
    newIds.forEach(id => this.fetchedIds.add(id));
    console.log('Updated fetched_ids:', Array.from(this.fetchedIds));
  }

  getFetchedIds(): number[] {
    return Array.from(this.fetchedIds);
  }

  addPosts(newPosts: Post[]): void {
    this.posts = [...this.posts, ...newPosts];
  }

  getPosts(): Post[] {
    return this.posts;
  }
}
