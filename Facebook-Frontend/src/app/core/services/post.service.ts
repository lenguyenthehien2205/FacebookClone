import { Injectable, input, signal } from '@angular/core';
import { filter, map, Observable } from 'rxjs';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Post } from '../../shared/models/post.model';
import { Media } from '../../shared/models/media.model';
import { PostFetchData } from '../../shared/models/post.model';
import { apiConfig } from 'src/app/shared/utils/api.utils';
@Injectable({
  providedIn: 'root',
})
export class PostService {
  private apiPosts = `${environment.apiBaseUrl}/posts/random-authors-latest`;

  private fetchedIds: Set<number> = new Set();
  posts = signal<Post[]>([]);

  constructor(private http: HttpClient) {}

  fetchPosts(postFetchData: PostFetchData): Observable<ApiResponse> {
    // console.log(this.posts);
    return this.http
      .post<ApiResponse>(this.apiPosts, postFetchData, apiConfig)
      .pipe(
        map((response: ApiResponse) => {
          if (
            response &&
            response.data.length > 0 &&
            Array.isArray(response.data)
          ) {
            // Lọc ra các bài viết chưa được hiển thị
            response.data = (response.data as Post[]).filter(
              (post) => !this.fetchedIds.has(post.id)
            );
          }
          // Nếu không còn bài viết nào sau khi lọc
          if (response.data.length === 0) {
            return {
              message: 'No new posts available',
              status: '404',
              data: [],
            }; // Trả về ApiResponse với thông điệp
          }
          return response;
        })
      );
  }

  updateFetchedIds(newIds: number[]) {
    newIds.forEach((id) => this.fetchedIds.add(id));
    console.log('Updated fetched_ids:', Array.from(this.fetchedIds));
  }

  getFetchedIds(): number[] {
    return Array.from(this.fetchedIds);
  }

  addPosts(newPosts: Post[]): void {
    this.posts.update((posts) => [...posts, ...newPosts]);
  }
  getPosts(): Post[] {
    return this.posts();
  }
}
