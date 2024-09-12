import { NgModule } from "@angular/core";
import { HeaderComponent } from "./header.component";
import { RoundedButtonComponent } from "./rounded-button/rounded-button.component";
import { HistoryComponent } from "./history/history.component";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { SharedModule } from "src/app/shared/shared.module"; 
import { HistoryItemComponent } from "./history/history-item/history-item.component";

@NgModule({
    declarations: [
        HeaderComponent,
        RoundedButtonComponent,
        HistoryComponent,
        HistoryItemComponent
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