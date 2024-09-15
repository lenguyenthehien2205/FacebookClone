import { Component } from '@angular/core';
import { User, UserService } from 'src/app/core/services/user.service';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/features/auth/responses/api.response';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrl: './contacts.component.css'
})
export class ContactsComponent {
  users: User[] = [];
  constructor(private userService: UserService) {

  }
  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getUsers().subscribe({
      next: (response: ApiResponse) => {
        const users = response?.data as User[];
        users.forEach((user: User) => {
          if(user){
            user.avatar = `${environment.apiBaseUrl}/users/images/${user.avatar}`;
          }
        });
        this.users = users;
      },
      error: (error) => {
        console.error('Lỗi khi tải danh sách người dùng:', error);
      }
    });
  }
}
