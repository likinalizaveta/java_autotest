package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new ContactData().withFirstname("Jeremy1").withLastname("Martinson1")
            .withAddress("455 Larkspur Dr.\nCalifornia Springs, CA 92926\nUSA").withMobilePhone("11111")
            .withPhoneHome("22222").withWorkPhone("33333").withEmail("jmartinson@yahoo.com").withGroup("test1")});
    list.add(new Object[] {new ContactData().withFirstname("Jeremy2").withLastname("Martinson2")
            .withAddress("455 Larkspur Dr.\nCalifornia Springs, CA 92926\nUSA").withMobilePhone("11111")
            .withPhoneHome("22222").withWorkPhone("33333").withEmail("jmartinson@yahoo.com").withGroup("test2")});
    list.add(new Object[] {new ContactData().withFirstname("Jeremy3").withLastname("Martinson2")
            .withAddress("455 Larkspur Dr.\nCalifornia Springs, CA 92926\nUSA").withMobilePhone("11111")
            .withPhoneHome("22222").withWorkPhone("33333").withEmail("jmartinson@yahoo.com").withGroup("test3")});
    return list.iterator();

  }

  @Test(dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) throws Exception {
    File photo = new File("src/test/resources/image.png");
      app.goTo().homePage();
      Contacts before = app.contact().all();
      app.contact().gotoAddNewContactPage();
      app.contact().fillNewContactForm(contact, true);
      app.contact().submitNewContactCreation();
      app.goTo().homePage();
      assertThat(app.contact().count(), equalTo(before.size() + 1));
      Contacts after = app.contact().all();
      assertThat(after, equalTo(
              before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test(enabled = false)
  public void testBedContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.contact().gotoAddNewContactPage();
    ContactData contact = new ContactData().withFirstname("Jeremy'").withLastname("Martinson").withAddress("455 Larkspur Dr.\nCalifornia Springs, CA 92926\nUSA")
            .withPhoneHome("11111").withMobilePhone("22222").withWorkPhone("33333").withEmail("jmartinson@yahoo.com").withGroup("test1");
    app.contact().fillNewContactForm(contact, true);
    app.contact().submitNewContactCreation();
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }

//  @Test(enabled = false)
//  public void testCurrentDir() {
//    File currentDir = new File(".");
//    System.out.println(currentDir.getAbsolutePath());
//    File photo = new File("src/test/resources/image.png");
//    System.out.println(photo.getAbsolutePath());
//    System.out.println(photo.exists());
//  }





}
