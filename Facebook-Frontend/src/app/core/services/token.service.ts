import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
@Injectable({
    providedIn: 'root'
})
export class TokenService {
    private readonly TOKEN_KEY = 'access_token';
    private jwtHelperService = new JwtHelperService();
    getToken(): string | null {
        console.log(localStorage.getItem(this.TOKEN_KEY));
        return localStorage.getItem(this.TOKEN_KEY);
    }
    setToken(token: string) {
        localStorage.setItem(this.TOKEN_KEY, token);
    }
    removeToken() {
        localStorage.removeItem(this.TOKEN_KEY);
    }
    getUserId(): number {
        let token = this.getToken();
        if (!token) {
            return 0;
        }
        let userObject = this.jwtHelperService.decodeToken(token);
        return 'userId' in userObject ? parseInt(userObject['userId']) : 0;
    }
    getAvatar(): string {
        let token = this.getToken();
        if(!token){
            return "";
        }
        let userObject = this.jwtHelperService.decodeToken(token);
        return 'avatar' in userObject ? userObject['avatar'] : "";
    }
    getFullName(){
        let token = this.getToken();
        if(!token){
            return "";
        }
        let userObject = this.jwtHelperService.decodeToken(token);
        return 'fullName' in userObject ? userObject['fullName'] : "";
    }
    getFirstName(){
        let token = this.getToken();
        if(!token){
            return "";
        }
        let userObject = this.jwtHelperService.decodeToken(token);
        return 'firstName' in userObject ? userObject['firstName'] : "";
    }
    getLastName(){
        let token = this.getToken();
        if(!token){
            return "";
        }
        let userObject = this.jwtHelperService.decodeToken(token);
        return 'lastName' in userObject ? userObject['lastName'] : "";
    }
    getPageType(){
        let token = this.getToken();
        if(!token){
            return "";
        }
        let userObject = this.jwtHelperService.decodeToken(token);
        return 'pageType' in userObject ? userObject['pageType'] : "";
    }
    getDisplayFormat(): string{
        let token = this.getToken();
        if(!token){
            return "";
        }
        let userObject = this.jwtHelperService.decodeToken(token);
        return 'displayFormat' in userObject ? userObject['displayFormat'] : "";
    }
    isTokenExpired(): boolean { 
        if(this.getToken() == null) {
            return false;
        }       
        return this.jwtHelperService.isTokenExpired(this.getToken()!);
    }
}