import { NgModule } from "@angular/core";
import { HeaderComponent } from "./header.component";
import { RoundedButtonComponent } from "./components/rounded-button/rounded-button.component";
import { HistoryComponent } from "./components/history/history.component";
import { CommonModule } from "@angular/common";
import { CustomTitleComponent } from "../../shared/components/custom-title/custom-title.component";
import { NavbarButtonComponent } from "../../shared/components/navbar-button/navbar-button.component";
import { RouterModule } from "@angular/router";

@NgModule({
    declarations: [
        HeaderComponent,
        RoundedButtonComponent,
        HistoryComponent,
        CustomTitleComponent,
        NavbarButtonComponent
    ],
    imports: [
        CommonModule,
        RouterModule
    ],
    exports: [
        HeaderComponent
    ]
})
export class HeaderModule { }