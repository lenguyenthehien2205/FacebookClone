import { ChangeDetectionStrategy, ChangeDetectorRef, Component, computed, inject, OnInit, Optional, signal, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { concatMap } from 'rxjs';
import { ImageService } from 'src/app/core/services/image.service';
import { ProfileService } from 'src/app/core/services/profile.service';
import { TokenService } from 'src/app/core/services/token.service';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { ImageProfileResponse, ImageResponse, ImageWithFullnameResponse, ProfileFriendsReponse } from 'src/app/shared/responses/common/image.response';
import { ProfileComponent } from '../../page/profile.component';
import { ProfileHeaderResponse } from 'src/app/shared/responses/profile/profile-header.response';
import { LOADING_TIME } from 'src/app/shared/constants/app-config';

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
  profileImageResponses = signal<ImageProfileResponse[]>([]);
  profileFriendsResponse = signal<ProfileFriendsReponse>(new ProfileFriendsReponse());
  route = inject(ActivatedRoute);
  cdr = inject(ChangeDetectorRef);
  profileId = signal<number>(0);

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
      }
    });
  }
  getAvatar(url: string): string {
    return this.imageService.getAvatar(url);
  }
  
}
