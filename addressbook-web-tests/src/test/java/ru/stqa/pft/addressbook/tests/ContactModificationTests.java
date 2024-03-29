package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstname("Jeremy")
              .withLastname("Martinson").withAddress("455 Larkspur Dr.\nCalifornia Springs, CA 92926\nUSA")
              .withPhoneHome("11111").withMobilePhone("22222").withWorkPhone("33333")
              .withEmail("jmartinson@yahoo.com").withEmail2("jmartinson2@yahoo.com").withEmail3("jmartinson3@yahoo.com"));
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstname("Jeremy").withLastname("Martinson")
            .withAddress("California Springs").withPhoneHome("11111")
            .withMobilePhone("22222").withWorkPhone("33333").withEmail("jmartinson@yahoo.com")
            .withEmail2("jmartinson2@yahoo.com").withEmail3("jmartinson3@yahoo.com");
    app.goTo().homePage();
    app.contact().modify(contact);
    app.goTo().homePage();
    Contacts after = app.db().contacts();
    assertThat(app.contact().count(), equalTo(before.size()));
    assertThat(after, equalTo(before.withoutAdded(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
  }


}
