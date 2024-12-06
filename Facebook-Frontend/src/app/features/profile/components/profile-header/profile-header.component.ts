import { CommonModule } from '@angular/common';
import { Component, inject, input, OnInit, signal } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ProfileService } from 'src/app/core/services/profile.service';
import { TokenService } from 'src/app/core/services/token.service';
import { ApiResponse } from 'src/app/features/auth/responses/api.response';

@Component({
  selector: 'app-profile-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile-header.component.html',
  styleUrl: './profile-header.component.css'
})
export class ProfileHeaderComponent implements OnInit{
  pathname = input<string | null>(null);
  fullname = signal<string>('');
  totalFriends = signal<string>('');
  mutualFriends = signal<string>('');
  tokenService = inject(TokenService);
  profileService = inject(ProfileService);
  images = [
    {
      id: 1,
      url: 'assets/post-image/post-image-2.jpg'
    },
    {
      id: 2,
      url: 'assets/post-image/post-image-2.jpg'
    },
    {
      id: 3,
      url: 'assets/post-image/post-image-2.jpg'
    },
    {
      id: 4,
      url: 'assets/post-image/post-image-2.jpg'
    },
    {
      id: 5,
      url: 'assets/post-image/post-image-2.jpg'
    },
    {
      id: 6,
      url: 'assets/post-image/post-image-2.jpg'
    },
    {
      id: 7,
      url: 'assets/post-image/post-image-2.jpg'
    },
    {
      id: 8,
      url: 'assets/post-image/post-image-2.jpg'
    }
  ]
  ngOnInit(): void {
    this.loadProfileHeader();
  }
  loadProfileHeader(){
    this.profileService.getProfileHeaderByPathname(this.pathname()!, Number(this.tokenService.getProfileId())).subscribe({
      next: (response: ApiResponse) => {
        this.fullname.set(response.data.fullname);
        this.totalFriends.set(response.data.total_friends);
        this.mutualFriends.set(response.data.mutual_friends);
      }
    });
  }
}
