import { NgModule } from "@angular/core";
import { ProfileComponent } from "./page/profile.component";
import { ProfileHeaderComponent } from "./components/profile-header/profile-header.component";
import { RouterModule } from "@angular/router";
import { SharedModule } from "src/app/shared/shared.module";
import { BrowserModule } from "@angular/platform-browser";
import { CommonModule } from "@angular/common";

@NgModule({
    declarations: [
        ProfileComponent,
        ProfileHeaderComponent
    ],
    imports: [RouterModule, SharedModule, CommonModule],
    exports: [ProfileComponent],
  })
export class ProfileModule{

}