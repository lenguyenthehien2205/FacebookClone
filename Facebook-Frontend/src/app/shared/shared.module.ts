import { NgModule } from "@angular/core";
import { CardComponent } from "./components/card/card.component";
import { MoreActionItemComponent } from "./components/more-action-item/more-action-item.component";
import { NavbarButtonComponent } from "./components/navbar-button/navbar-button.component";
import { SectionWithHeaderComponent } from "./components/section-with-header/section-with-header.component";
import { CustomTitleComponent } from "./components/custom-title/custom-title.component";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";

@NgModule({
  declarations: [
    CardComponent,
    MoreActionItemComponent,
    CustomTitleComponent,
    SectionWithHeaderComponent,
    NavbarButtonComponent
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
    NavbarButtonComponent
  ]
})
export class SharedModule {}