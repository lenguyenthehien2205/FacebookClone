import { HttpHeaders } from "@angular/common/http";

export function createHeaders(): HttpHeaders {
    return new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept-Language': 'vi',
    });
}
export function createHeadersForm(): HttpHeaders {
    return new HttpHeaders({
        'Content-Type': 'multipart/form-data',
        'Accept-Language': 'vi',
    });
}
  
export const apiConfig = {
    headers: createHeaders(),
};
export const apiConfigForm = {
    headers: createHeadersForm(),
};