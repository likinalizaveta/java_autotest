package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() ==0) {
      app.contact().create(new ContactData().withFirstname("Jeremy").withLastname("Martinson").withAddress("455 Larkspur Dr. \nCalifornia Springs, CA 92926 \nUSA").withPhonehome("1-212-123 45 67").withEmail("jmartinson@yahoo.com").withGroup("test1"));
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstname("Jeremy").withLastname("Martinson").withAddress("455 Larkspur Dr. \nCalifornia Springs, CA 92926 \nUSA").withPhonehome("1-212-123 45 67").withEmail("jmartinson@yahoo.com").withGroup("test1");
    app.goTo().homePage();
    app.contact().modify(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.withoutAdded(modifiedContact).withAdded(contact)));
  }


}
