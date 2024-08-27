import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HeaderComponent } from './components/shared/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { CustomTitleComponent } from "./components/shared/custom-title/custom-title.component";
import { HistoryComponent } from "./components/shared/header/history/history.component";
import { RouterOutlet } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CustomTitleComponent,
    HistoryComponent,
    RouterOutlet
],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
