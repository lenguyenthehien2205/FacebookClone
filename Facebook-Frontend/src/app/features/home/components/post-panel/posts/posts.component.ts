import { Component, signal } from '@angular/core';
import { Post } from './post/post-model';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrl: './posts.component.css'
})
export class PostsComponent {
  posts = signal<Post[]>([
    {
      id: 1,
      avatar: 'thehien.jpg',
      username: 'Right Music',
      time: '24 phút',
      media: ['post-image.jpg'],
      content: 'Nhà hiền triết Tun Phạm lại có phát ngôn khó hiểu 🙂',
      privacy: 'public',
      interactionCount: 92,
      commentCount: 6,
      shareCount: 2,
    },
    {
      id: 2,
      avatar: 'thehien.jpg',
      username: 'Thúy Nga',
      time: '1 giờ',
      media: ['post-image.jpg','post-image.jpg'],
      content: 'Nhà hiền triết Tun Phạm lại có phát ngôn khó hiểu 🙂',
      privacy: 'private',
      interactionCount: 9,
      commentCount: 6,
      shareCount: 22,
    },
  ]);

  // // Phương thức để cập nhật danh sách posts
  // updatePosts(newPosts: Post[]) {
  //   this.posts.set(newPosts); // Giả định rằng signal có phương thức set để cập nhật giá trị
  // }

  // addPost(newPost: Post) {
  //   const currentPosts = this.posts();
  //   this.updatePosts([...currentPosts, newPost]);
  // }
}
