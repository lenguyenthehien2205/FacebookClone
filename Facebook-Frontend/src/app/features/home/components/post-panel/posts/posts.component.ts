import { Component, EventEmitter, Output, signal } from '@angular/core';
import { Post } from './post/post-model';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrl: './posts.component.css'
})
export class PostsComponent {
  private allPosts: Post[] = [
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
      media: ['post-image.jpg'],
      content: 'Nhà hiền triết Tun Phạm lại có phát ngôn khó hiểu 🙂',
      privacy: 'only me',
      interactionCount: 9,
      commentCount: 6,
      shareCount: 22,
    },
    {
      id: 3,
      avatar: 'thehien.jpg',
      username: 'Thúy Nga',
      time: '1 giờ',
      media: ['post-image.jpg'],
      content: 'Nhà hiền triết Tun Phạm lại có phát ngôn khó hiểu 🙂',
      privacy: 'only me',
      interactionCount: 9,
      commentCount: 6,
      shareCount: 22,
    },
    {
      id: 4,
      avatar: 'thehien.jpg',
      username: 'Thúy Nga',
      time: '1 giờ',
      media: ['post-image.jpg'],
      content: 'Nhà hiền triết Tun Phạm lại có phát ngôn khó hiểu 🙂',
      privacy: 'only me',
      interactionCount: 9,
      commentCount: 6,
      shareCount: 22,
    },
    {
      id: 5,
      avatar: 'thehien.jpg',
      username: 'Thúy Nga',
      time: '1 giờ',
      media: ['post-image.jpg'],
      content: 'Nhà hiền triết Tun Phạm lại có phát ngôn khó hiểu 🙂',
      privacy: 'only me',
      interactionCount: 9,
      commentCount: 6,
      shareCount: 22,
    },
    {
      id: 6,
      avatar: 'thehien.jpg',
      username: 'Thúy Nga',
      time: '1 giờ',
      media: ['post-image.jpg'],
      content: 'Nhà hiền triết Tun Phạm lại có phát ngôn khó hiểu 🙂',
      privacy: 'only me',
      interactionCount: 9,
      commentCount: 6,
      shareCount: 22,
    },
    {
      id: 7,
      avatar: 'thehien.jpg',
      username: 'Thúy Nga',
      time: '1 giờ',
      media: ['post-image.jpg'],
      content: 'Nhà hiền triết Tun Phạm lại có phát ngôn khó hiểu 🙂',
      privacy: 'only me',
      interactionCount: 9,
      commentCount: 6,
      shareCount: 22,
    },
    {
      id: 8,
      avatar: 'thehien.jpg',
      username: 'Thúy Nga',
      time: '1 giờ',
      media: ['post-image.jpg'],
      content: 'Nhà hiền triết Tun Phạm lại có phát ngôn khó hiểu 🙂',
      privacy: 'only me',
      interactionCount: 9,
      commentCount: 6,
      shareCount: 22,
    },
  ];
  posts = signal<Post[]>(this.allPosts.slice(0, 2)); // Bắt đầu với 2 bài đăng đầu tiên
  private currentIndex = 2;
  private isLoading = false;

  // Phương thức để tải thêm bài đăng
  loadMorePosts() {
    if (this.isLoading || this.currentIndex >= this.allPosts.length) return;
    this.isLoading = true;
    setTimeout(() => {
      const newPosts = this.allPosts.slice(this.currentIndex, this.currentIndex + 2);
      this.posts.update(posts => [...posts, ...newPosts]);
      this.currentIndex += 2;
      this.isLoading = false;
    }, 200); // Giả lập thời gian tải dữ liệu (0.2 giây)
  }

  // // Phương thức để cập nhật danh sách posts
  // updatePosts(newPosts: Post[]) {
  //   this.posts.set(newPosts); // Giả định rằng signal có phương thức set để cập nhật giá trị
  // }

  // addPost(newPost: Post) {
  //   const currentPosts = this.posts();
  //   this.updatePosts([...currentPosts, newPost]);
  // }
}
