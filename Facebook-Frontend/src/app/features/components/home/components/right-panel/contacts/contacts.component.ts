import { Component } from '@angular/core';
import { User, UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrl: './contacts.component.css'
})
export class ContactsComponent {
  users: User[] = [];

  constructor(private userService: UserService) {

  }
  // ngOnInit() {
  //   this.loadUsers();
  // }

  // loadUsers() {
  //   this.userService.getUsers().subscribe((users) => {
  //     this.users = users;
  //   },
  //   (error) => {
  //     console.error('Lỗi khi tải danh sách người dùng:', error);
  //   });
  // }
}
