import { ChangeDetectionStrategy, Component, HostListener, inject, OnInit, signal, ViewChild } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, ResolveFn, Router, RouterStateSnapshot } from '@angular/router';
import { ProfileService } from 'src/app/core/services/profile.service';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { map } from 'rxjs';
import { Title } from '@angular/platform-browser';
import { ProfileHeaderComponent } from '../components/profile-header/profile-header.component';
import { ProfileHeaderResponse } from 'src/app/shared/responses/profile/profile-header.response';
import { ImageResponse, ProfileFriendsReponse } from 'src/app/shared/responses/common/image.response';
import { TokenService } from 'src/app/core/services/token.service';
import { LOADING_TIME } from 'src/app/shared/constants/app-config';
import { ImageService } from 'src/app/core/services/image.service';
import { ProfilePostsComponent } from '../components/profile-posts/profile-posts.component';
import { PostService } from 'src/app/core/services/post.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfileComponent implements OnInit{
  @ViewChild(ProfileHeaderComponent) profileHeaderComponent!: ProfileHeaderComponent;
  pathname: string | null = '';
  route = inject(ActivatedRoute);
  router = inject(Router);
  title = inject(Title);
  haveCoverPhoto: boolean = false;
  activatedRoute = inject(ActivatedRoute);
  postService = inject(PostService);
  profileHeaderResponse = signal<ProfileHeaderResponse>(
    new ProfileHeaderResponse()
  );

  ngOnInit(): void {
    this.pathname = this.route.snapshot.paramMap.get('pathname');
    this.activatedRoute.data.subscribe(data => {
      const fullname = data['fullname'];
      // Cập nhật tiêu đề
      this.title.setTitle(fullname + " | Facebook");
      this.pathname = this.route.snapshot.paramMap.get('pathname');
      this.loadProfileHeader();
    });
  }
  ngOnDestroy(): void {
    this.title.setTitle('Facebook'); 
  }

  isLoading = signal<boolean>(false);
  delay = signal<boolean>(false);
  profileService = inject(ProfileService);
  tokenService = inject(TokenService);
  imageService = inject(ImageService);
  loadProfileHeader() {
    if (this.delay()) return; // Không tải nếu đang đợi
    this.isLoading.set(true);
    this.delay.set(true);
    this.profileService
      .getProfileHeaderByPathname(
        this.pathname!,
        Number(this.tokenService.getProfileId())
      )
      .subscribe({
        next: (response: ApiResponse) => {
          this.profileService.currentProfileId.set(response.data.profile_id);
          this.profileHeaderResponse.set(
            response.data as ProfileHeaderResponse
          );
          this.profileHeaderResponse.update((current) => {
            if (current) {
              if(current.cover_photo){
                this.haveCoverPhoto = true;
              }else{
                this.haveCoverPhoto = false;
              }
              return {
                ...current,
                avatar: this.getAvatar(current.avatar),
                cover_photo: this.getCoverPhoto(current.cover_photo),
                avatar_friends: current.avatar_friends.map(
                  (image: ImageResponse) => ({
                    ...image,
                    url: this.getAvatar(image.url),
                  })
                ),
              };
            }
            return current;
          });
          setTimeout(() => {
            this.delay.set(false);
            this.isLoading.set(false);
          }, LOADING_TIME);
        },
      });
  }
  getAvatar(url: string): string {
    return this.imageService.getAvatar(url);
  }
  getCoverPhoto(url: string): string {
    return this.imageService.getCoverPhoto(url);
  }
  // handleScroll(event: Event): void {
  //   const element = event.target as HTMLElement;
  //   const scrollPosition = element.scrollTop + element.clientHeight;
  //   const scrollThreshold = element.scrollHeight*0.8;
  //   console.log('scrollPosition', scrollPosition);
  //   console.log('scrollThreshold', scrollThreshold);
  //   if (scrollPosition >= scrollThreshold) {
  //     this.profilePostsComponent.loadPosts();
  //   }
  // }
  @HostListener('window:scroll', ['$event'])
  onWindowScroll(): void {
    // Use the document’s root element to get correct scroll values
    const element = document.documentElement;
  
    const scrollPosition = element.scrollTop + element.clientHeight;
    const scrollThreshold = element.scrollHeight * 0.7;
  
    console.log('scrollPosition', scrollPosition);
    console.log('scrollThreshold', scrollThreshold);
  
    if (scrollPosition >= scrollThreshold) {
      this.postService.triggerLoadPosts();
    }
  }
  // constructor() {
  //   // Lắng nghe sự kiện đóng trình duyệt
  //   window.addEventListener('beforeunload', this.updateOfflineStatus.bind(this));
  // }

  // updateOfflineStatus() {
  //   // Gửi yêu cầu cập nhật trạng thái người dùng
  //   navigator.sendBeacon('/api/user/offline');
  // }

  // ngOnDestroy() {
  //   window.removeEventListener('beforeunload', this.updateOfflineStatus);
  // }
}
export const resolveUserName: ResolveFn<string> = (
  activatedRoute: ActivatedRouteSnapshot,
  routerState: RouterStateSnapshot
) => {
  const profileService = inject(ProfileService);
  const pathname = activatedRoute.paramMap.get('pathname')!;
  
  // Trả về Observable, resolver sẽ đợi Observable này hoàn thành trước khi chuyển hướng
  return profileService.getInfo(pathname).pipe(
    map((response: ApiResponse) => {
      return response.data.fullname;
    })
  );
};

// export const resolveTitle: ResolveFn<string> = (
//   activatedRoute,
//   routerState
// ) => {
//   return resolveUserName(activatedRoute, routerState);
// };
