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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
