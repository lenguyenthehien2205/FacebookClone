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
import { Media } from 'src/app/shared/models/media.model';
import { PostFetchData } from 'src/app/shared/models/post.model';
import { Post } from 'src/app/shared/models/post.model';
import { PostService } from 'src/app/core/services/post.service';
import { TokenService } from 'src/app/core/services/token.service';
import {
  formatDate,
  getDayOfWeek,
  getTimeAgo,
} from 'src/app/shared/utils/date-format-utils';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { LOADING_TIME } from 'src/app/shared/constants/app-config';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrl: './posts.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PostsComponent implements OnInit {
  isLoading = signal<boolean>(false);
  delay = signal<boolean>(false);
  // posts = signal<Post[]>([]);
  tokenService = inject(TokenService);
  postService = inject(PostService);
  postFetchData: PostFetchData = {
    author_id: this.tokenService.getProfileId(),
    limit: 3,
    fetched_ids: [],
  };
  posts = signal<Post[]>([]);
  ngOnInit() {
    this.loadPosts();
  }
  constructor(private cdRef: ChangeDetectorRef) {}

  loadPosts() {
    if (this.delay()) return; // Không tải nếu đang đợi
    this.isLoading.set(true);
    this.delay.set(true);
    this.postFetchData.fetched_ids = this.postService.getFetchedIds();
    this.postService.fetchPosts(this.postFetchData).subscribe({
      next: (response: ApiResponse) => {
        if (
          response &&
          response.data.length > 0 &&
          Array.isArray(response.data)
        ) {
          const newPosts = response.data as Post[];
          newPosts.forEach((post: Post) => {
            if (post) {
              if (post.medias) {
                post.medias.forEach((media: Media) => {
                  if (media.media_type === 'image') {
                    media.url = `${environment.apiBaseUrl}/medias/image_post/${media.url}`;
                  } else if (media.media_type === 'video') {
                    media.url = `${environment.apiBaseUrl}/medias/video_post/${media.url}`;
                  }
                });
              }
              post.avatar = `${environment.apiBaseUrl}/profiles/avatar_image/${post.avatar}`;
            }
          });
          const newFetchedIds = newPosts.map((post) => post.id);
          this.postService.updateFetchedIds(newFetchedIds);
          this.postService.addPosts(newPosts);
          setTimeout(() => {
            this.posts.set(this.postService.getPosts());
            this.delay.set(false);
            this.isLoading.set(false);
            this.cdRef.detectChanges();
          }, LOADING_TIME);
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
  // getDisplayName(post: Post): string {
  //   return getName(post.first_name, post.last_name, post.display_format);
  // }
}
