import { Component, inject } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ProfileHeaderComponent } from '../components/profile-header/profile-header.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  pathname: string | null = '';
  route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.pathname = this.route.snapshot.paramMap.get('pathname');
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
