package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().list().size() ==0) {
      app.contact().create(new ContactData().withFirstname("Jeremy").withLastname("Martinson").withAddress("455 Larkspur Dr. \nCalifornia Springs, CA 92926 \nUSA").withPhonehome("1-212-123 45 67").withEmail("jmartinson@yahoo.com").withGroup("test1"));
    }
  }

  @Test
  public void testContactModification() {
    List<ContactData> before = app.contact().list();
    int index = before.size() -1;
    ContactData contact = new ContactData()
            .withId(before.get(before.size() - 1).getId()).withFirstname("Jeremy").withLastname("Martinson").withAddress("455 Larkspur Dr. \nCalifornia Springs, CA 92926 \nUSA").withPhonehome("1-212-123 45 67").withEmail("jmartinson@yahoo.com").withGroup("test1");
    app.goTo().homePage();
    app.contact().modify(index, contact);
    app.goTo().homePage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }



}
