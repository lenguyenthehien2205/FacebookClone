import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, inject, input, OnDestroy, OnInit, signal } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { ImageService } from 'src/app/core/services/image.service';
import { ProfileService } from 'src/app/core/services/profile.service';
import { TokenService } from 'src/app/core/services/token.service';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { ProfileHeaderResponse } from 'src/app/shared/responses/profile/profile-header.response';
import { ImageResponse } from 'src/app/shared/responses/common/image.response';
import { LOADING_TIME } from 'src/app/shared/constants/app-config';
import { SharedModule } from "../../../../shared/shared.module";
import { NavigationEnd, NavigationStart, Router } from '@angular/router';

@Component({
  selector: 'app-profile-header',
  templateUrl: './profile-header.component.html',
  styleUrl: './profile-header.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfileHeaderComponent implements OnInit, OnDestroy {
  pathname = input<string | null>();
  isLoading = signal<boolean>(false);
  delay = signal<boolean>(false);
  profileHeaderResponse = signal<ProfileHeaderResponse>(
    new ProfileHeaderResponse()
  );
  tokenService = inject(TokenService);
  profileService = inject(ProfileService);
  imageService = inject(ImageService);

  // navigation
  navItems = [
    { name: 'Bài viết', url: 'posts' },
    { name: 'Giới thiệu', url: 'about/overview' },
    { name: 'Bạn bè', url: 'friends' },
    { name: 'Ảnh', url: 'photos' },
    { name: 'Video', url: 'videos' },
    { name: 'Check in', url: 'map' }
  ];
  activeItemNavItem: string | null = null;
  onSelectNavItem(name: string) {
    this.activeItemNavItem = name;
    console.log(this.activeItemNavItem);
  }
  private routerSubscription!: Subscription;
  router = inject(Router);
  cdr = inject(ChangeDetectorRef);
  ngOnInit(): void {
    this.loadProfileHeader();
    const pathAfterHostname = this.router.url;
    this.updateActiveNavItem(pathAfterHostname);
    // Lắng nghe sự kiện điều hướng hoàn tất
    this.routerSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd || event instanceof NavigationStart) {
        const updatedPathAfterHostname = this.router.url;
        this.updateActiveNavItem(updatedPathAfterHostname);
      }
    });
  }
  private updateActiveNavItem(url: string): void {
    const baseUrl = `/${this.pathname()}`;
    
    if (url.startsWith(`${baseUrl}/posts`)) {
      this.activeItemNavItem = 'Bài viết';
    } else if (url.startsWith(`${baseUrl}/about`)) {
      this.activeItemNavItem = 'Giới thiệu';
    } else if (url.startsWith(`${baseUrl}/friends`)) {
      this.activeItemNavItem = 'Bạn bè';
    } else if (url.startsWith(`${baseUrl}/photos`)) {
      this.activeItemNavItem = 'Ảnh';
    } else if (url.startsWith(`${baseUrl}/videos`)) {
      this.activeItemNavItem = 'Video';
    } else if (url.startsWith(`${baseUrl}/map`)) {
      this.activeItemNavItem = 'Check in';
    } else {
      this.activeItemNavItem = 'Bài viết';  // Mặc định
    }
    this.cdr.detectChanges();  // Đảm bảo giao diện được cập nhật
  }
  ngOnDestroy(): void {
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }
  getRouterLink(url: string): string {
    const pathnameValue = this.pathname();
    if (pathnameValue) {
      return `/${pathnameValue}/${url}`;
    }
    return `/${url}`;
  }
  
  loadProfileHeader() {
    if (this.delay()) return; // Không tải nếu đang đợi
    this.isLoading.set(true);
    this.delay.set(true);
    this.profileService
      .getProfileHeaderByPathname(
        this.pathname()!,
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
}
