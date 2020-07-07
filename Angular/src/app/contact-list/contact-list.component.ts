import { Component, OnInit } from '@angular/core';
import { ContactService } from '../contact.service';
import { Contact } from '../contact';  
import { Observable,Subject } from "rxjs";  
  
import {FormControl,FormGroup,Validators} from '@angular/forms'; 
@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.css']
})
export class ContactListComponent implements OnInit {

  constructor(private contactservice:ContactService) { }

  contactArray: any[] = [];  
  dtOptions: DataTables.Settings = {};  
  dtTrigger: Subject<any>= new Subject();  
  
  contacts: Observable<Contact[]>;  
  contact : Contact=new Contact();  
  deleteMessage=false;  
  contactlist:Object[]=[];  
  isupdated = false;      
   
  
  ngOnInit() {  
    this.isupdated=false;  
    this.dtOptions = {  
      pageLength: 6,  
      stateSave:true,  
      lengthMenu:[[6, 16, 20, -1], [6, 16, 20, "All"]],  
      processing: true  
    };     
    this.contactservice.getContactList().subscribe(data =>{  
    this.contacts =data;  
    this.dtTrigger.next();  
    })  
  }  
    
  deleteContact(id: number) {  
    this.contactservice.deleteContact(id)  
      .subscribe(  
        data => {  
          console.log(data);  
          this.deleteMessage=true;  
          this.contactservice.getContactList().subscribe(data =>{  
            this.contacts =data  
            })  
        },  
        error => console.log(error));  
  }  
  
  updateContact(id: number){  
    this.contactservice.getContact(id)  
      .subscribe(  
        data => {  
         // console.log("update contact list"+data.valueOf);
         this.contactlist=[];
          this.contactlist.push(data);   
          console.log(this.contactlist)     
        },  
        error => console.log(error));  
  }  
  
  contactupdateform=new FormGroup({  
    contact_id:new FormControl(),  
    first_name:new FormControl(),  
    last_name:new FormControl(),  
    contact_email:new FormControl(),
    contact_phone:new FormControl(),
    contact_status:new FormControl()  
  });  
  
  updateCont(updstu:any){  
    this.contact=new Contact(); 
    if(this.ContactId!=null)  
   this.contact.contactId=this.ContactId.value;  
   if(this.FirstName!=null)
   this.contact.firstName=this.FirstName.value;  
   if(this.LastName!=null)
   this.contact.lastName=this.LastName.value;  
   if(this.ContactEmail!=null)
   this.contact.email=this.ContactEmail.value;  
   if(this.ContactPhone)
   this.contact.phoneNumber=this.ContactPhone.value; 
   if(this.ContactStatus) 
   this.contact.status=this.ContactStatus.value;
   if(this.ContactStatus!=null)
   console.log(this.ContactStatus.value);  
     
  console.log("Contact Id"+this.contact.contactId+"  contact "+this.contact.firstName);
   this.contactservice.updateContact(this.contact.contactId,this.contact).subscribe(  
    data => {       
      this.isupdated=true;  
      this.contactservice.getContactList().subscribe(data =>{  
        console.log("Update Call"+data);
        this.contacts =data  
        })  
    },  
    error => console.log(error));  
  }  
  
  get FirstName(){  
    return this.contactupdateform.get('first_name');  
  }  
  get LastName(){  
    return this.contactupdateform.get('last_name');  
  }  

  get ContactPhone(){  
    return this.contactupdateform.get('contact_phone');  
  } 
  get ContactEmail(){  
    return this.contactupdateform.get('contact_email');  
  }  
  
  get ContactStatus(){  
    return this.contactupdateform.get('contact_status');  
  }  
  
  get ContactId(){  
    return this.contactupdateform.get('contact_id');  
  }  
  
  changeisUpdate(){  
    this.isupdated=false;  
  }  
}  