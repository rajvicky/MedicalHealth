import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';  
import { Observable } from 'rxjs';  
@Injectable({
  providedIn: 'root'
})
export class ContactService {
  private baseUrl = 'http://localhost:8082/';
  constructor(private http:HttpClient) { }
  getContactList(): Observable<any> {  
    return this.http.get(`${this.baseUrl}`+'getcontactlist');  
  }  
  
  createContact(contact: object): Observable<object> {  
    return this.http.post(`${this.baseUrl}`+'addcontact',contact );  
  }  
  
  deleteContact(id: number): Observable<any> {  
    return this.http.delete(`${this.baseUrl}/deletecontact/${id}`, { responseType: 'text' });  
  }  
  
  getContact(id: number): Observable<Object> {  
    return this.http.get(`${this.baseUrl}/contact/${id}`);  
  }  
  
  updateContact(id: number, value: any): Observable<Object> {  
    console.log(value);
    const headers = new HttpHeaders()
    return this.http.put(`${this.baseUrl}/updatecontact/${id}`,value);  
  }  
    
}
