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


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    CustomTitleComponent,
    HistoryComponent,
    HomeComponent,
    LeftPanelComponent,
    InfiniteScrollComponent
  ],
  imports: [BrowserModule, AppRoutingModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}