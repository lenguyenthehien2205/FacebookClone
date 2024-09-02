import { Component, AfterViewInit, ElementRef, ViewChild, Output, EventEmitter } from '@angular/core';
import { PostsComponent } from './posts/posts.component';

@Component({
  selector: 'app-post-panel',
  templateUrl: './post-panel.component.html',
  styleUrls: ['./post-panel.component.css']
})
export class PostPanelComponent {
  @ViewChild(PostsComponent) postsComponent!: PostsComponent;


  loadMorePosts() {
    this.postsComponent.loadMorePosts();
  }
}
