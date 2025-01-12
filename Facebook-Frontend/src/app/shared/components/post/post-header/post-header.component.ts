import { ChangeDetectionStrategy, Component, ElementRef, EventEmitter, HostListener, inject, input, Output } from '@angular/core';
import { PostService } from 'src/app/core/services/post.service';

@Component({
  selector: 'app-post-header',
  templateUrl: './post-header.component.html',
  styleUrl: './post-header.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PostHeaderComponent {
  postId = input.required<number>();
  avatar = input.required<string>();
  username = input.required<string>();
  pathname = input.required<string>();
  time = input.required<string>();
  timeAgo = input.required<string>();
  isOnline = input.required<boolean>();
  hiddenIcon = input.required<boolean>();
  options = input.required<boolean>();
  isShowPostForm: boolean = false;
  isOptionsOpen : boolean = false;
  postService = inject(PostService);
  privacyIcons = [
    {
      mode: 'public',
      icon: 'fa-solid fa-earth-americas'
    },
    {
      mode: 'friends',
      icon: 'fa-solid fa-user-friends'
    },
    {
      mode: 'only me',
      icon: 'fa-solid fa-lock'
    }
  ]
  privacy = input.required<string>();
  getPrivacyIcon(privacy: string) {
    return this.privacyIcons.find(icon => icon.mode === privacy)?.icon;
  }
  getConvertedPrivacy(privacy: string){
    if(privacy === 'friends'){
      return 'Bạn bè';
    }else if(privacy === 'public'){
      return 'Công khai';
    }else {
      return 'Chỉ mình tôi';
    }
  }
  toggleOptions() {
    this.isOptionsOpen = !this.isOptionsOpen;
  }

  closeOptions() {
    this.isOptionsOpen = false;
  }

  // Nghe sự kiện click ra ngoài để đóng dropdown
  @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent) {
    const optionButton = document.getElementById('option-button'+this.postId());
    const options = document.getElementById('options'+this.postId());
    if (optionButton && options && !options.contains(event.target as Node) && !optionButton.contains(event.target as Node)) {
      this.closeOptions();
    }
  }

  onShowPostForm() {
    this.isShowPostForm = true;
  }
  onHidePostForm(){
    this.isShowPostForm = false;
  }

  
  onDelete() {
    console.log('Delete post:', this.postId());
    this.postService.deletePost(this.postId()).subscribe({
      next: (response: any) => {
        window.location.reload();
      },
    });
  }
}
