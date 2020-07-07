package com.contacts.contacts.controller;

import java.util.List;

import com.contacts.contacts.Dao.ContactIDao;
import com.contacts.contacts.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class ContactController {

    @Autowired
    private ContactIDao contactIDao;
    @GetMapping(value = "hello")
    public String Hello(){
        return "Hello Vikas";
    }
    @PostMapping(value = "addcontact")
    public ResponseEntity<String> addToContactList(@RequestBody Contact contact){
       int count=contactIDao.addContactToList(contact);
       if(count==1){
           return ResponseEntity.status(HttpStatus.CREATED).body("Sucessfully Added in the contactList");
       }
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something syntax error");
    }
   /* @GetMapping(value="getcontactlist")
    public ResponseEntity<List<Contact>>  getContactList(){
        List<Contact> contactList=contactIDao.getContactList();
        if(contactList.size()>0){
            return ResponseEntity.ok().body(contactList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }*/
    @GetMapping(value="getcontactlist")
    public List<Contact> getContactList(){
        List<Contact> list=contactIDao.getContactList();
        System.out.println(list);
        return list;
        /*if(contactList.size()>0){
            return ResponseEntity.ok().body(contactList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);*/
    }
    
    @GetMapping(value="contact/{contactId}")
    public Contact getContactById(@PathVariable int contactId){
        return  contactIDao.getContactById(contactId);
    }


    @PutMapping(value="updatecontact/{contactId}")
    public Contact updateContact(@RequestBody Contact contact, @PathVariable int contactId){
        contact.setContactId(contactId);
        return contactIDao.UpdateContact(contact);
    }

    @DeleteMapping(value="deletecontact/{contactId}")
    public int deleteContact(@PathVariable int contactId){
        return contactIDao.DeleteContact(contactId);
    }
    
    @DeleteMapping(value="deleteallcontact")
    public int deleteAllContact(){
        return contactIDao.DeleteAllContacts();
    }
    
    @PutMapping(value="inactive/{contactId}")
    public int inActiveContact(@PathVariable int contactId){
        return contactIDao.InactiveContact(contactId);
    }
}