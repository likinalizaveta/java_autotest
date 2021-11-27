package ru.stqa.pft.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

public class DbHelper {

  private final SessionFactory sessionFactory;

  public DbHelper() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public Groups groups() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<GroupData> result = session.createQuery("from GroupData").list();
    session.getTransaction().commit();
    session.close();
    return new Groups(result);
  }

  public Contacts contacts() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> result = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
    session.getTransaction().commit();
    session.close();
    return new Contacts(result);
  }

  public Contacts contactsInGroupById(Integer groupId) {
    Contacts actualContactsInGroup = new Contacts();
    Contacts allContactsInGroup = groupById(groupId).getContacts();
    Session session = sessionFactory.openSession();
    session.beginTransaction();

    for (ContactData contact : allContactsInGroup) {
      List<ContactData> resultOfActualityContact = session.createQuery("from ContactData where id = '" + contact.getId()
              + "' and deprecated = '0000-00-00'").list();
      if (resultOfActualityContact.size() == 1) {
        actualContactsInGroup.add(resultOfActualityContact.get(0));
      }
    }

    session.getTransaction().commit();
    session.close();

    return actualContactsInGroup;
  }

  public GroupData groupById(Integer groupId) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<GroupData> result = session.createQuery("from GroupData where group_id = '" + groupId + "'").list();
    session.getTransaction().commit();
    session.close();
    return result.get(0);
  }

  public ContactData contactById(Integer id) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> result = session.createQuery("from ContactData where id = '" + id + "'").list();
    session.getTransaction().commit();
    session.close();
    return result.get(0);
  }


}
