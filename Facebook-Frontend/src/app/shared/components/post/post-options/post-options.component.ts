import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-post-options',
  templateUrl: './post-options.component.html',
  styleUrl: './post-options.component.css'
})
export class PostOptionsComponent {
  @Input() isOpen = false;
  @Output() editPost = new EventEmitter<void>();
  @Output() deletePost = new EventEmitter<void>();
  // Ngăn chặn việc đóng dropdown khi nhấn vào nó
  onOptionsClick(event: MouseEvent) {
    event.stopPropagation();
  } 
  onDelete() {
    console.log('Delete post');
    this.deletePost.emit();
  }
  onEdit() {
    this.editPost.emit();
  }
}
