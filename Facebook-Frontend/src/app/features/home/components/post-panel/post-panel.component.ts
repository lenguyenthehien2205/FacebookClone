import { Component, AfterViewInit, ElementRef, ViewChild, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import { PostsComponent } from './posts/posts.component';

@Component({
  selector: 'app-post-panel',
  templateUrl: './post-panel.component.html',
  styleUrls: ['./post-panel.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PostPanelComponent {
  @ViewChild(PostsComponent) postsComponent!: PostsComponent;


  loadMorePosts() {
    this.postsComponent.loadPosts();
  }
}
