package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().returnToHomePage();
    app.getContactsHelper().selectContact();
    app.getContactsHelper().initContactModification();
    app.getContactsHelper().fillNewContactForm(new ContactData("Jeremy", "Martinson", "455 Larkspur Dr. \nCalifornia Springs, CA 92926 \nUSA", "1-212-123 45 67", "jmartinson@yahoo.com", null), false);
    app.getContactsHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
  }

}
