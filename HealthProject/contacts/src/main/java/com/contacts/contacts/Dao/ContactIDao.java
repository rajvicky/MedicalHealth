package com.contacts.contacts.Dao;

import java.util.List;
import com.contacts.contacts.model.Contact;

import org.springframework.stereotype.Component;

@Component
public interface ContactIDao {
    public int addContactToList(Contact contact);
    public List<Contact> getContactList();
    public Contact UpdateContact(Contact contact);
    public int DeleteContact(int contactId);
    public int  DeleteAllContacts();
    public int InactiveContact(int contactId);
    public Contact getContactById(int contactId);
}