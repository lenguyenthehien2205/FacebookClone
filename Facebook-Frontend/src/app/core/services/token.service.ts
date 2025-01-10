import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { getName } from 'src/app/shared/utils/name-format-utils';
@Injectable({
    providedIn: 'root'
})
export class TokenService {
    private jwtHelperService = new JwtHelperService();
    private readonly TOKEN_KEY = 'access_token';
    private readonly TAB_ID_KEY = 'tab_id';

    // Lấy hoặc tạo Tab ID duy nhất
    getTabId(): string {
        let tabId = sessionStorage.getItem(this.TAB_ID_KEY);
        if (!tabId) {
            tabId = Math.random().toString(36).substr(2, 9); // Tạo ID ngẫu nhiên
            sessionStorage.setItem(this.TAB_ID_KEY, tabId);
        }
        return tabId;
    }

    // Lưu token vào cookie kèm Tab ID
    setToken(token: string): void {
        const tabId = this.getTabId();
        document.cookie = `${this.TOKEN_KEY}_${tabId}=${token}; path=/; Secure`;
        // const expires = new Date();
        // expires.setDate(expires.getDate() + 7); // Thêm 7 ngày từ hôm nay
        // document.cookie = `${this.TOKEN_KEY}_${tabId}=${token}; path=/; expires=${expires.toUTCString()}; Secure`;
    }
    // Lấy token từ cookie dựa trên Tab ID
    getToken(): string | null {
        const tabId = this.getTabId();
        const cookies = document.cookie.split('; ');
        const tokenCookie = cookies.find(row => row.startsWith(`${this.TOKEN_KEY}_${tabId}=`));
        return tokenCookie ? tokenCookie.split('=')[1] : null;
    }
    // Xóa token từ cookie dựa trên Tab ID
    removeToken(): void {
        const tabId = this.getTabId();
        document.cookie = `${this.TOKEN_KEY}_${tabId}=; max-age=0; path=/; Secure`;
    }
    
    getUserId(): number {
        let token = this.getToken();
        if (!token) {
            return 0;
        }
        let userObject = this.jwtHelperService.decodeToken(token);
        return 'userId' in userObject ? parseInt(userObject['userId']) : 0;
    }
    getProfileId(): number {
        let token = this.getToken();
        if (!token) {
            return 0;
        }
        let userObject = this.jwtHelperService.decodeToken(token);
        return 'currentPageId' in userObject ? parseInt(userObject['currentPageId']) : 0;
    }
    getAvatar(): string {
        let token = this.getToken();
        if(!token){
            return "";
        }
        let userObject = this.jwtHelperService.decodeToken(token);
        return 'avatar' in userObject ? userObject['avatar'] : "";
    }
    // fullnam for fanpage
    getFullNamePage(){
        let token = this.getToken();
        if(!token){
            return "";
        }
        let userObject = this.jwtHelperService.decodeToken(token);
        return 'fullName' in userObject ? userObject['fullName'] : "";
    }
    getFullNameProfile(){
        return getName(this.getFirstName(), this.getLastName(), this.getDisplayFormat());
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
    getPathname(): string{
        let token = this.getToken();
        if(!token){
            return "";
        }
        let userObject = this.jwtHelperService.decodeToken(token);
        return 'pathname' in userObject ? userObject['pathname'] : "";
    }
    isTokenExpired(): boolean { 
        if(this.getToken() == null) {
            return false;
        }       
        return this.jwtHelperService.isTokenExpired(this.getToken()!);
    }
}