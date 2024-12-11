import { NgModule } from "@angular/core";
import { ProfileComponent } from "./page/profile.component";
import { ProfileHeaderComponent } from "./components/profile-header/profile-header.component";
import { RouterModule } from "@angular/router";
import { SharedModule } from "src/app/shared/shared.module";
import { BrowserModule } from "@angular/platform-browser";
import { CommonModule } from "@angular/common";
import { ProfilePostsComponent } from "./components/profile-posts/profile-posts.component";
import { ProfileAboutComponent } from "./components/profile-about/profile-about.component";

@NgModule({
    declarations: [
        ProfileComponent,
        ProfileHeaderComponent,
        ProfilePostsComponent,
        ProfileAboutComponent
    ],
    imports: [RouterModule, SharedModule, CommonModule],
    exports: [ProfileComponent],
  })
export class ProfileModule{

}