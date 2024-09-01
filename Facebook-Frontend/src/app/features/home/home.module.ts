import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HomeComponent } from "./home.component";
import { LeftPanelComponent } from "./components/left-panel/left-panel.component";
import { RightPanelComponent } from "./components/right-panel/right-panel.component";
import { PostPanelComponent } from "./components/post-panel/post-panel.component";
import { StoryComponent } from "./components/post-panel/story/story.component";
import { NewStoryComponent } from "./components/post-panel/new-story/new-story.component";
import { SponsoredComponent } from "./components/right-panel/sponsored/sponsored.component";
import { TagsComponent } from "./components/left-panel/tags/tags.component";
import { TagItemComponent } from "./components/left-panel/tags/tag-item/tag-item.component";
import { SponsoredItemComponent } from "./components/right-panel/sponsored/sponsored-item/sponsored-item.component";
import { CardComponent } from "../../shared/components/card/card.component";
import { SectionWithHeaderComponent } from "../../shared/components/section-with-header/section-with-header.component";
import { InfiniteScrollComponent } from '../../shared/components/infinite-scroll/infinite-scroll.component';
import { YourMindComponent } from './components/post-panel/your-mind/your-mind.component';
import { StoriesComponent } from './components/post-panel/stories/stories.component';
import { PostsComponent } from './components/post-panel/posts/posts.component';
import { PostComponent } from './components/post-panel/posts/post/post.component';
import { PostHeaderComponent } from './components/post-panel/posts/post/post-header/post-header.component';
import { PostContentComponent } from './components/post-panel/posts/post/post-content/post-content.component';
import { PostInteractionsComponent } from './components/post-panel/posts/post/post-interactions/post-interactions.component';
import { PostActionsComponent } from './components/post-panel/posts/post/post-actions/post-actions.component';

@NgModule({
    declarations: [
        HomeComponent,
        LeftPanelComponent,
        RightPanelComponent,
        PostPanelComponent,
        StoryComponent,
        NewStoryComponent,
        SponsoredComponent,
        TagsComponent,
        TagItemComponent,
        SponsoredItemComponent,
        CardComponent,
        SectionWithHeaderComponent,
        InfiniteScrollComponent,
        YourMindComponent,
        StoriesComponent,
        PostsComponent,
        PostComponent,
        PostHeaderComponent,
        PostContentComponent,
        PostInteractionsComponent,
        PostActionsComponent
    ],
    imports: [
        CommonModule,
        RouterModule
    ],
    exports: [
        HomeComponent
    ]
})
export class HomeModule { }