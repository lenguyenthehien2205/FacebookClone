import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { TokenService } from "../services/token.service";
import { Observable } from "rxjs";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
    // đăng ký interceptor trong module
    tokenService = inject(TokenService);
    // interceptor sẽ thêm header Authorization vào mọi request
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = this.tokenService.getToken();
        if (token) {
            req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
        }
        return next.handle(req);
    }
}
