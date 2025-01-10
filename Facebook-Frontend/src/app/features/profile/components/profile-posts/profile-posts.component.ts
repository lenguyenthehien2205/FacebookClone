import { AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, computed, inject, OnDestroy, OnInit, Optional, signal, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { concatMap, Subscription } from 'rxjs';
import { ImageService } from 'src/app/core/services/image.service';
import { ProfileService } from 'src/app/core/services/profile.service';
import { TokenService } from 'src/app/core/services/token.service';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { ImageProfileResponse, ImageResponse, ImageWithFullnameResponse, ProfileFriendsReponse } from 'src/app/shared/responses/common/image.response';
import { ProfileComponent } from '../../page/profile.component';
import { ProfileHeaderResponse } from 'src/app/shared/responses/profile/profile-header.response';
import { LOADING_TIME } from 'src/app/shared/constants/app-config';
import { PostService } from 'src/app/core/services/post.service';
import { LoadPostDTO } from 'src/app/shared/dtos/load-post.dto';
import { Post } from 'src/app/shared/models/post.model';
import { Media } from 'src/app/shared/models/media.model';
import { environment } from 'src/app/environments/environment';
import { formatDate, getDayOfWeek, getTimeAgo } from 'src/app/shared/utils/date-format-utils';

@Component({
  selector: 'app-profile-posts',
  templateUrl: './profile-posts.component.html',
  styleUrl: './profile-posts.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfilePostsComponent implements OnInit{
  pathname: string | null = '';
  profileService = inject(ProfileService);
  tokenService = inject(TokenService);
  imageService = inject(ImageService);
  postService = inject(PostService);
  router = inject(Router);
  profileImageResponses = signal<ImageProfileResponse[]>([]);
  profileFriendsResponse = signal<ProfileFriendsReponse>(new ProfileFriendsReponse());
  route = inject(ActivatedRoute);
  cdr = inject(ChangeDetectorRef);
  profileId = signal<number>(0);
  authorId = signal<number>(0); // id của người đăng bài
  loadPostDTO = new LoadPostDTO({
    author_id: 0,
    limit: 4,
    fetched_ids: []
  });
  posts: Post[] = [];

  // constructor(@Optional() private profile: ProfileComponent) {}
  loadImages() {
    this.profileService.getInfo(this.pathname!).pipe(
      concatMap((response: any) => {
        this.profileId.set(response.data.profile_id); // Cập nhật profileId
        return this.profileService.getMediaImagesProfile(this.profileId()); // Gọi getMediaImagesProfile sau khi profileId được cập nhật
      })
    ).subscribe({
      next: (reponse: ApiResponse) => {
        const images = reponse.data as ImageProfileResponse[];
        if(images){
          images.forEach((image: ImageProfileResponse) => {
            if(image){
              image.url = `${this.profileService.mediaApiUrl}/image_post/${image.url}`;
            }
          });
        }
        this.profileImageResponses.set(images);
      }
    });
  }
  loadFriends(){
    this.profileService.getFriends(this.pathname!, this.tokenService.getProfileId()).subscribe({
      next: (reponse: ApiResponse) => {
        this.profileFriendsResponse.set(reponse.data as ProfileFriendsReponse);
        this.profileFriendsResponse().profile_avatar_friends.forEach(element => {
          console.log('Element:', element);
          
            element.url = this.getAvatar(element.url);
        });
      }
    })
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const newPathname = params.get('pathname');
      if (newPathname && newPathname !== this.pathname) {
        this.pathname = newPathname;
        
        // Reload data
        this.loadImages();
        this.loadFriends();
        setTimeout(() => { // bất đồng bộ để cập nhật profileId
          this.loadPostDTO.author_id = this.profileId();
          if(this.loadPostDTO.author_id === this.tokenService.getProfileId()){
            this.loadMyPosts();
          }else{
            this.loadPosts();
          }
        }, 100);
      }
    });
    this.postService.loadPosts$.subscribe(() => {
      if(this.loadPostDTO.author_id === this.tokenService.getProfileId()){
        this.loadMyPosts();
      }else{
        this.loadPosts();
      }
    });
  }

  getAvatar(url: string): string {
    return this.imageService.getAvatar(url);
  }
  
  loadPosts() {
    this.postService.loadPostsByProfileId(this.loadPostDTO).subscribe({
      next: (response: ApiResponse) => {
        this.loadPostsFn(response);
      },
      error: (error) => {
        console.error('Lỗi khi tải danh sách bài đăng:', error);
      },
    });
  }

  loadMyPosts() {
    this.postService.loadMyPosts(this.loadPostDTO).subscribe({
      next: (response: ApiResponse) => {
        this.loadPostsFn(response);
      },
      error: (error) => {
        console.error('Lỗi khi tải danh sách bài đăng:', error);
      },
    });
  }
  private loadPostsFn(response: ApiResponse) {
    console.log('Response:', response.data);
        
    if (response && response.data.length > 0 && Array.isArray(response.data)) {
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
      console.log('New fetched ids:', newFetchedIds);
      
      this.loadPostDTO.fetched_ids.push(...newFetchedIds);
      this.posts.push(...newPosts);
      this.cdr.detectChanges();
    }
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
  // getAuthorIdAndRedirect(profileId: number, pathname: string): void {
  //   this.authorId.set(profileId);
  //   console.log('AuthorId:', this.authorId());
    
  //   setTimeout(() => {
  //     window.location.href = `${pathname}/posts`;
  //   }, 100);
  // }
}
