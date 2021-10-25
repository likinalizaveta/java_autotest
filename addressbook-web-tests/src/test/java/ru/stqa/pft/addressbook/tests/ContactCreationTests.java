package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.contact().gotoAddNewContactPage();
    ContactData contact = new ContactData().withFirstname("Jeremy").withLastname("Martinson").withAddress("455 Larkspur Dr. \nCalifornia Springs, CA 92926 \nUSA").withPhoneHome("11111").withMobilePhone("22222").withWorkPhone("33333").withEmail("jmartinson@yahoo.com").withGroup("test1");
    app.contact().fillNewContactForm(contact, true);
    app.contact().submitNewContactCreation();
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testBedContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.contact().gotoAddNewContactPage();
    ContactData contact = new ContactData().withFirstname("Jeremy'").withLastname("Martinson").withAddress("455 Larkspur Dr. \nCalifornia Springs, CA 92926 \nUSA").withPhoneHome("11111").withMobilePhone("22222").withWorkPhone("33333").withEmail("jmartinson@yahoo.com").withGroup("test1");
    app.contact().fillNewContactForm(contact, true);
    app.contact().submitNewContactCreation();
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }

}
