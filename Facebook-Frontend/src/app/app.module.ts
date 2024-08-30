import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HeaderComponent } from './components/shared/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { CustomTitleComponent } from './components/shared/custom-title/custom-title.component';
import { HistoryComponent } from './components/shared/header/history/history.component';
import { AppRoutingModule } from './app-routing.module';
import { LeftPanelComponent } from './components/home/left-panel/left-panel.component';
import { InfiniteScrollComponent } from './components/shared/infinite-scroll/infinite-scroll.component';
import { CardComponent } from './components/shared/card/card.component';
import { SectionWithHeaderComponent } from './components/shared/section-with-header/section-with-header.component';
import { PostPanelComponent } from './components/home/post-panel/post-panel.component';
import { StoryComponent } from './components/home/post-panel/story/story.component';
import { ButtonComponent } from './components/shared/button/button.component';
import { NewStoryComponent } from './components/home/post-panel/new-story/new-story.component';
import { RightPanelComponent } from './components/home/right-panel/right-panel.component';
import { RoundedButtonComponent } from './components/shared/header/rounded-button/rounded-button.component';
import { SponsoredComponent } from './components/home/right-panel/sponsored/sponsored.component';
import { SponsoredItemComponent } from './components/home/right-panel/sponsored/sponsored-item/sponsored-item.component';
import { TagItemComponent } from './components/home/left-panel/tags/tag-item/tag-item.component';
import { TagsComponent } from './components/home/left-panel/tags/tags.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    CustomTitleComponent,
    HistoryComponent,
    HomeComponent,
    LeftPanelComponent,
    InfiniteScrollComponent,
    SectionWithHeaderComponent,
    PostPanelComponent,
    ButtonComponent,
    CardComponent,
    StoryComponent,
    NewStoryComponent,
    RightPanelComponent,
    RoundedButtonComponent,
    SponsoredComponent,
    SponsoredItemComponent,
    TagItemComponent,
    TagsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
