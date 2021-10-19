package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().returnToHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Jeremy", "Martinson", "455 Larkspur Dr. \nCalifornia Springs, CA 92926 \nUSA", "1-212-123 45 67", "jmartinson@yahoo.com", "test1"));
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getNavigationHelper().returnToHomePage();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().initContactModification();
    ContactData contact = new ContactData(before.get(before.size() - 1).getId(),"Jeremy", "Martinson", "455 Larkspur Dr. \nCalifornia Springs, CA 92926 \nUSA", "1-212-123 45 67", "jmartinson@yahoo.com", null);
    app.getContactHelper().fillNewContactForm(contact, false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }

}
