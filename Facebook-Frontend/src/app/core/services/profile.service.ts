import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/app/environments/environment";
import { ApiResponse } from "src/app/features/auth/responses/api.response";

@Injectable({
    providedIn: 'root'
})
export class ProfileService {
    private profileHeaderApiUrl = `${environment.apiBaseUrl}/profiles/header`;
    private apiConfig = {
        headers: this.createHeaders(),
    };
    private createHeaders(): HttpHeaders {
        return new HttpHeaders({
            'Content-Type': 'application/json',
            'Accept-Language': 'vi',
        });
    }
    constructor(private http: HttpClient) { }
    getProfileHeaderByPathname(pathname: string, myProfileId: number): Observable<ApiResponse> {
        return this.http.get<ApiResponse>(`${this.profileHeaderApiUrl}/${pathname}/${myProfileId}`, this.apiConfig);
    }
}