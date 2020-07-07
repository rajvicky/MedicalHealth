import { Component, OnInit } from '@angular/core';
import { ContactService } from '../contact.service';  
import {FormControl,FormGroup,Validators} from '@angular/forms';
import{Contact} from '../contact'
@Component({
  selector: 'app-add-contact',
  templateUrl: './add-contact.component.html',
  styleUrls: ['./add-contact.component.css']
})
export class AddContactComponent implements OnInit {

  constructor(private contactservice:ContactService) { }
  contact: Contact=new Contact();
  submitted = false;  
  ngOnInit(): void {
    this.submitted=false; 
  }
 
  contactsaveform=new FormGroup({  
    first_name:new FormControl('' , [Validators.required , Validators.minLength(5) ] ),  
    last_name:new FormControl(''),
    contact_email:new FormControl('',[Validators.required,Validators.email]),   
    contact_phone:new FormControl(''),
    contact_status:new FormControl('')
  });  
  
  saveContact(saveContact:any){  
    console.log("Contact creating....");
    this.contact=new Contact();  
    if(this.FirstName!=null)   
    this.contact.firstName=this.FirstName.value;  
    if(this.LastName!=null)
    this.contact.lastName=this.LastName.value;
    if(this.ContactEmail)
    this.contact.email=this.ContactEmail.value;
    if(this.ContactPhone)
    this.contact.phoneNumber=this.ContactPhone.value;  
    if(this.ContactStatus)
    this.contact.status=this.ContactStatus.value; 
    this.submitted = true;  
    this.save();  
  }  
  
    
  
  save() {  
    this.contactservice.createContact(this.contact)  
      .subscribe(data => console.log(data), error => console.log(error));  
    this.contact = new Contact();  
  }  
  
  get FirstName(){  
    return this.contactsaveform.get('first_name');  
  }  
  
  get LastName(){  
    return this.contactsaveform.get('last_name');  
  }  
  get ContactEmail(){  
    return this.contactsaveform.get('contact_email');  
  }  
  
  get ContactPhone(){  
    return this.contactsaveform.get('contact_phone');  
  }  
  
  get ContactStatus(){  
    return this.contactsaveform.get('contact_status');  
  } 
  addContactForm(){  
    this.submitted=false;  
    this.contactsaveform.reset();  
  }  
} 