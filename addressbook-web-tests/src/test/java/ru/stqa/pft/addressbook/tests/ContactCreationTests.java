package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().returnToHomePage();
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().gotoAddNewContactPage();
    app.getContactHelper().fillNewContactForm(new ContactData("Jeremy", "Martinson", "455 Larkspur Dr. \nCalifornia Springs, CA 92926 \nUSA", "1-212-123 45 67", "jmartinson@yahoo.com", "test1"), true);
    app.getContactHelper().submitNewContactCreation();
    app.getNavigationHelper().returnToHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before + 1);
  }


}
