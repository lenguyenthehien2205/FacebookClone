import { ChangeDetectionStrategy, Component, inject, OnInit, signal, ViewChild } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, ResolveFn, Router, RouterStateSnapshot } from '@angular/router';
import { ProfileService } from 'src/app/core/services/profile.service';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { map } from 'rxjs';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfileComponent implements OnInit{
  pathname: string | null = '';
  route = inject(ActivatedRoute);
  router = inject(Router);
  title = inject(Title);
  activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.pathname = this.route.snapshot.paramMap.get('pathname');
    this.activatedRoute.data.subscribe(data => {
      const fullname = data['fullname'];
      // Cập nhật tiêu đề
      this.title.setTitle(fullname + " | Facebook");
    });
  }
  ngOnDestroy(): void {
    this.title.setTitle('Facebook'); 
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
