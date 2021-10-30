package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
    String line = reader.readLine();
    while (line != null) {
      String[] split = line.split(";");
      list.add(new Object[]{new ContactData().withFirstname(split[0]).withLastname(split[1])
              .withAddress(split[2]).withPhoneHome(split[3]).withMobilePhone(split[4])
              .withWorkPhone(split[5]).withEmail(split[6]).withGroup(split[7])});
      line = reader.readLine();
    }
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
