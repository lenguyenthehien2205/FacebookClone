import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, inject, input, OnChanges, OnDestroy, OnInit, signal, SimpleChanges } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { ImageService } from 'src/app/core/services/image.service';
import { ProfileService } from 'src/app/core/services/profile.service';
import { TokenService } from 'src/app/core/services/token.service';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { ProfileHeaderResponse } from 'src/app/shared/responses/profile/profile-header.response';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { FriendService } from 'src/app/core/services/friend.service';

@Component({
  selector: 'app-profile-header',
  templateUrl: './profile-header.component.html',
  styleUrl: './profile-header.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfileHeaderComponent implements OnInit, OnDestroy, OnChanges  {
  pathname = input<string | null>();
  response = input<ProfileHeaderResponse>(new ProfileHeaderResponse());
  isLoading = input<boolean>();
  delay = input<boolean>();
  tokenService = inject(TokenService);
  profileService = inject(ProfileService);
  imageService = inject(ImageService);
  friendService = inject(FriendService);
  haveCoverPhoto = input();
  isOptionsOpen : boolean = false;
  isFriend: boolean = false;
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
    // this.loadProfileHeader();
    const pathAfterHostname = this.router.url;
    this.updateActiveNavItem(pathAfterHostname);
    // Lắng nghe sự kiện điều hướng hoàn tất
    this.routerSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd || event instanceof NavigationStart) {
        const updatedPathAfterHostname = this.router.url;
        this.updateActiveNavItem(updatedPathAfterHostname);
      }
    });
    this.checkFriendship();
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
  
  triggerFileAvatarInput(): void {
    const fileAvatarInput = document.getElementById('fileAvatarInput') as HTMLInputElement;
    if (fileAvatarInput) {
      fileAvatarInput.click();
    }
  }
  triggerFileCoverPhotoInput(): void {
    const fileCoverPhotoInput = document.getElementById('fileCoverPhotoInput') as HTMLInputElement;
    if (fileCoverPhotoInput) {
      fileCoverPhotoInput.click();
    }
  }
  
  onFileAvatarSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      this.uploadAvatar(file);
    }
  }
  onFileCoverPhotoSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      this.uploadCoverPhoto(file);
    }
  }

  uploadCoverPhoto(file: File): void {
    const formData = new FormData();
    formData.append('file', file);
  
    this.imageService.uploadCoverPhoto(formData).subscribe({
      next: (response: ApiResponse) => {
        if (response && response.data && response.data.cover_photo) {
          alert('Upload cover photo successfully');
          this.response().cover_photo = response.data.cover_photo;
          window.location.reload();
        }
      },
      error: (error) => {
        alert('Error when uploading cover photo, please try again.');
      }
    });
  }
  
  uploadAvatar(file: File): void {
    const formData = new FormData();
    formData.append('file', file);
  
    this.imageService.uploadAvatar(formData).subscribe({
      next: (response: ApiResponse) => {
        console.log('Upload avatar response:', response);
        if (response && response.data && response.data.avatar) {
          alert('Upload avatar successfully');
          this.response().avatar = response.data.avatar;
          window.location.reload();
        }
      },
      error: (error) => {
        alert('Error when uploading avatar, please try again.');
      }
    });
  }
  isMyProfile(): boolean {
    return this.tokenService.getProfileId() === this.response().profile_id;
  }
  // isFriend(): boolean {
  //   let isFriend = false;
  //   this.friendService.isFriend(this.tokenService.getProfileId(), this.response().profile_id).subscribe({
  //     next: (response: ApiResponse) => {
  //       isFriend = response.data;
  //       console.log('isFriend:', isFriend);
        
  //     },
  //     error: (error) => {
  //       console.error('Error checking friendship status', error);
  //     }
  //   });
  //   console.log('isFriend:', isFriend);
  //   console.log(this.tokenService.getProfileId(), this.response().profile_id);
  //   return isFriend;
  // }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['response'] && this.response()?.profile_id) {
      console.log('Profile header response:', this.response()); 
      
      this.checkFriendship();
    }
  }
  checkFriendship() {
    this.friendService
      .isFriend(this.tokenService.getProfileId(), this.response().profile_id)
      .subscribe({
        next: (response: ApiResponse) => {
          this.isFriend = response.data;
        },
        error: (error) => {
          console.error('Error checking friendship status', error);
          this.isFriend = false;
        },
      });
  }
}
