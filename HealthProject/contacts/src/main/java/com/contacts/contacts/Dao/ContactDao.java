package com.contacts.contacts.Dao;

import com.contacts.contacts.model.Contact;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class ContactDao implements ContactIDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    public int addContactToList(Contact contact){
       Session session=null;;
       try {
           session=entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
       } catch (HibernateException e) {
           session=entityManagerFactory.unwrap(SessionFactory.class).openSession();
       }
       session.beginTransaction();
       int newConatcat=(int) session.save(contact);
       session.getTransaction().commit();
       session.close();
       return newConatcat;
    }

    public List<Contact> getContactList(){
        Session session=null;
        try {
            session=entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
        } catch (HibernateException ex) {
            session=entityManagerFactory.unwrap(SessionFactory.class).openSession();
        }
        session.beginTransaction();
        CriteriaBuilder criteriaBuilder=session.getCriteriaBuilder();;
        CriteriaQuery<Contact> listQuery=criteriaBuilder.createQuery(Contact.class);
        Root<Contact> rootContact=listQuery.from(Contact.class);
        listQuery.select(rootContact);
        List<Contact> listContact=session.createQuery(listQuery).getResultList();
        return listContact;
    }

    @Override
    public Contact UpdateContact(Contact contact) {
        Session session=null;
        try {
            session=entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
        } catch (HibernateException ex){
            session=entityManagerFactory.unwrap(SessionFactory.class).openSession();
        }
       session.beginTransaction();
        session.saveOrUpdate(contact);
       session.getTransaction().commit();
       session.close();
       return contact;
    }

    @Override
    public int DeleteContact(int contactId) {
          Transaction trxt=null;
          int rowsDelete=0;
          try (Session session=entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            trxt= session.beginTransaction();
            CriteriaBuilder builder=session.getCriteriaBuilder();
            CriteriaDelete<Contact> criteriaDelete=builder.createCriteriaDelete(Contact.class);
            Root<Contact> rootContact=criteriaDelete.from(Contact.class);
            criteriaDelete.where(builder.equal(rootContact.get("contactId"),contactId));
            rowsDelete=session.createQuery(criteriaDelete).executeUpdate();
            trxt.commit();
        } catch (HibernateException e) {
            if(trxt!=null){
                trxt.rollback();
            }
            e.printStackTrace();
        }
    return rowsDelete;
    }

    @Override
    public int DeleteAllContacts() {
        Transaction trxt=null;
         int rowsDelete=0;
         try (Session session=entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
           trxt= session.beginTransaction();
        
            CriteriaBuilder builder=session.getCriteriaBuilder();
            CriteriaDelete<Contact> criteraDelete=builder.createCriteriaDelete(Contact.class);
            rowsDelete=session.createQuery(criteraDelete).executeUpdate();
            session.getTransaction().commit();
            } catch (HibernateException e) {
                if(trxt!=null){
                    trxt.rollback();
                }
                e.printStackTrace();
            }
        return rowsDelete;
    }

    @Override
    public int InactiveContact(int contactId) {
         Transaction trxt=null;
         int inActCount=0;
         try (Session session=entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
           trxt= session.beginTransaction();
           String query="Update Contact set status=:status"+ " where contactId=:contactId";
           Query hql=session.createQuery(query,Contact.class);
           hql.setParameter("status", "inactive");
           hql.setParameter("contacrId", contactId);
           inActCount = hql.executeUpdate();
           trxt.commit();
         } catch (HibernateException e) {
             if(trxt!=null){
                 trxt.rollback();
             }
             e.printStackTrace();
         }
        return inActCount;
    }

    @Override
    public Contact getContactById(int contactId) {
        Transaction trxt=null;
        Contact contact=null;
        try (Session session=entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            trxt=session.beginTransaction();
            contact=(Contact)session.get(Contact.class,contactId);
            trxt.commit();
        } catch (HibernateException e) {
            if(trxt!=null){
                trxt.rollback();
            }
            e.printStackTrace();
        }
        return contact;
    }
}