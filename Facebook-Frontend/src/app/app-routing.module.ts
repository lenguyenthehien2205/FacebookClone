import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { routes } from "./app.routes";

@NgModule({
    imports: [
      RouterModule.forRoot(routes, 
        {
          bindToComponentInputs: true, 
          paramsInheritanceStrategy: 'always', 
          scrollPositionRestoration: 'enabled' // Kích hoạt tính năng giữ lại vị trí cuộn
        }
      )
    ],
    exports: [RouterModule],
  })
export class AppRoutingModule {}    