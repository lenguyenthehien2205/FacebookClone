import { HttpHeaders } from "@angular/common/http";

export function createHeaders(): HttpHeaders {
    return new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept-Language': 'vi',
    });
}
  
export const apiConfig = {
    headers: createHeaders(),
};