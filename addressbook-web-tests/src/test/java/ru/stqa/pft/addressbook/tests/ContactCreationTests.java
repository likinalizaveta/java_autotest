package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().gotoAddNewContactPage();
    app.getContactHelper().fillNewContactForm(new ContactData("Jeremy", "Martinson", "455 Larkspur Dr. \nCalifornia Springs, CA 92926 \nUSA", "1-212-123 45 67", "jmartinson@yahoo.com", "test1"), true);
    app.getContactHelper().submitNewContactCreation();
    app.getNavigationHelper().returnToHomePage();
  }


}
