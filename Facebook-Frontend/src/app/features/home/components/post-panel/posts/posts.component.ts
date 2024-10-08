import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  inject,
  OnInit,
  Output,
  signal,
} from '@angular/core';
import { Media } from 'src/app/core/models/media.model';
import { PostFetchData } from 'src/app/core/models/post-fetch-data.model';
import { Post } from 'src/app/core/models/post.model';
import { PostService } from 'src/app/core/services/post.service';
import { formatDate, getDayOfWeek, getTimeAgo } from 'src/app/core/utils/date-format-utils';
import { getName } from 'src/app/core/utils/name-format-utils';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/features/auth/responses/api.response';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrl: './posts.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PostsComponent implements OnInit {
  // posts = signal<Post[]>([]);
  postService = inject(PostService);
  postFetchData: PostFetchData = {
    user_id: 1,
    limit: 3,
    fetched_ids: [],
  };
  posts: Post[] = [];
  ngOnInit() {
    this.loadPosts();
  }
  constructor(private cdRef: ChangeDetectorRef) {}

  loadPosts() {
    this.postFetchData.fetched_ids = this.postService.getFetchedIds();
    this.postService.fetchPosts(this.postFetchData).subscribe({
      next: (response: ApiResponse) => {
        if (
          response &&
          response.data.length > 0 &&
          Array.isArray(response.data)
        ) {
          const newPosts = response.data as Post[];
          // Xử lý bài viết mới
          newPosts.forEach((post: Post) => {
            if (post) {
              post.medias.forEach((media: Media) => {
                if (media.media_type === 'image') {
                  media.url = `${environment.apiBaseUrl}/posts/images/${media.url}`;
                } else if (media.media_type === 'video') {
                  media.url = `${environment.apiBaseUrl}/posts/videos/${media.url}`;
                }
              });
              post.avatar = `${environment.apiBaseUrl}/users/images/${post.avatar}`;
            }
          });

          const newFetchedIds = newPosts.map((post) => post.post_id);
          this.postService.updateFetchedIds(newFetchedIds);

          this.postService.addPosts(newPosts);
          this.posts = this.postService.getPosts();
          this.cdRef.detectChanges();
        }
      },
      error: (error) => {
        console.error('Lỗi khi tải danh sách bài đăng:', error);
      },
    });
  }

  getDayOfWeek(date: Date): string {
    return getDayOfWeek(date);
  }

  formatDate(inputDate: number[]): string {
    return formatDate(inputDate);
  }
  getTimeAgo(inputDate: number[]): string {
    return getTimeAgo(inputDate);
  }
  getDisplayName(post: Post): string {
    return getName(post.first_name, post.last_name, post.display_format);
  }
}
