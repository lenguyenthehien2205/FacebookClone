import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { LoginComponent } from "./page/login.component";
import { RegisterComponent } from "./components/register/register.component";
import { RouterModule } from "@angular/router";
import { RecentAccountComponent } from "./components/recent-account/recent-account.component";
import { LoginFormComponent } from "./components/login-form/login-form.component";
import { ReactiveFormsModule } from "@angular/forms";

@NgModule({
    declarations: [
        LoginComponent,
        RegisterComponent,
        RecentAccountComponent,
        LoginFormComponent
    ],
    imports: [
        CommonModule,
        RouterModule,
        ReactiveFormsModule
    ],
    exports: [
        LoginComponent,
        RegisterComponent,
    ],
})
export class AuthModule { }