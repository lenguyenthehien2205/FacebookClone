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
import { PostComponent } from "./components/post/post.component";
import { PostHeaderComponent } from "./components/post/post-header/post-header.component";
import { PostActionsComponent } from "./components/post/post-actions/post-actions.component";
import { PostContentComponent } from "./components/post/post-content/post-content.component";
import { PostInteractionsComponent } from "./components/post/post-interactions/post-interactions.component";
import { PostOptionsComponent } from "./components/post/post-options/post-options.component";
import { PostFormComponent } from "./components/post-form/post-form.component";
import { ReactiveFormsModule } from "@angular/forms";

@NgModule({
  declarations: [
    CardComponent,
    MoreActionItemComponent,
    CustomTitleComponent,
    SectionWithHeaderComponent,
    NavbarButtonComponent,
    PostSkeletonComponent,
    ContactSkeletonComponent,
    ProfileHeaderSkeletonComponent,
    PostComponent,
    PostHeaderComponent,
    PostActionsComponent,
    PostContentComponent,
    PostInteractionsComponent,
    PostOptionsComponent,
    PostFormComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule
  ],
  exports: [
    CardComponent,
    MoreActionItemComponent,
    CustomTitleComponent,
    SectionWithHeaderComponent,
    NavbarButtonComponent,
    PostSkeletonComponent,
    ContactSkeletonComponent,
    ProfileHeaderSkeletonComponent,
    PostComponent,
    PostActionsComponent,
    PostHeaderComponent,
    PostContentComponent,
    PostInteractionsComponent,
    PostFormComponent
  ]
})
export class SharedModule {}