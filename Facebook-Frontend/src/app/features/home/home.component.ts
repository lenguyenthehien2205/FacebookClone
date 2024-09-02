import { Component, ViewChild } from '@angular/core';
import { PostPanelComponent } from './components/post-panel/post-panel.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  @ViewChild(PostPanelComponent) postPanelComponent!: PostPanelComponent;

  handleScroll(event: Event): void {
    const element = event.target as HTMLElement;
    const scrollPosition = element.scrollTop + element.clientHeight;
    const scrollThreshold = element.scrollHeight*0.7;
    console.log('scrollPosition', scrollPosition);
    console.log('scrollThreshold', scrollThreshold);
    if (scrollPosition >= scrollThreshold) {
      this.postPanelComponent.loadMorePosts();
    }
  }
}
