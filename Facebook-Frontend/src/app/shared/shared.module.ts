import { NgModule } from "@angular/core";
import { CardComponent } from "./components/card/card.component";
import { MoreActionItemComponent } from "./components/more-action-item/more-action-item.component";
import { NavbarButtonComponent } from "./components/navbar-button/navbar-button.component";
import { SectionWithHeaderComponent } from "./components/section-with-header/section-with-header.component";
import { CustomTitleComponent } from "./components/custom-title/custom-title.component";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { PostSkeletonComponent } from "./components/skeletons/post-skeleton/post-skeleton.component";
import { ContactSkeletonComponent } from "./components/skeletons/contact-skeleton/contact-skeleton.component";
import { ProfileHeaderSkeletonComponent } from "./components/skeletons/profile-header-skeleton/profile-header-skeleton.component";

@NgModule({
  declarations: [
    CardComponent,
    MoreActionItemComponent,
    CustomTitleComponent,
    SectionWithHeaderComponent,
    NavbarButtonComponent,
    PostSkeletonComponent,
    ContactSkeletonComponent,
    ProfileHeaderSkeletonComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    CardComponent,
    MoreActionItemComponent,
    CustomTitleComponent,
    SectionWithHeaderComponent,
    NavbarButtonComponent,
    PostSkeletonComponent,
    ContactSkeletonComponent,
    ProfileHeaderSkeletonComponent
  ]
})
export class SharedModule {}