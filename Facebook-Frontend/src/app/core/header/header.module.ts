import { NgModule } from "@angular/core";
import { HeaderComponent } from "./header.component";
import { RoundedButtonComponent } from "./components/rounded-button/rounded-button.component";
import { HistoryComponent } from "./components/history/history.component";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { SharedModule } from "src/app/shared/shared.module"; // Import SharedModule

@NgModule({
    declarations: [
        HeaderComponent,
        RoundedButtonComponent,
        HistoryComponent
        // Xóa CustomTitleComponent khỏi đây
    ],
    imports: [
        CommonModule,
        RouterModule,
        SharedModule // Import SharedModule để sử dụng CustomTitleComponent
    ],
    exports: [
        HeaderComponent
    ]
})
export class HeaderModule { }