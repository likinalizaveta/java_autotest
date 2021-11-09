package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.json.TypeToken;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))){
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType()); //list<ContactData>.class
      return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }
  }

  @BeforeMethod
  public void ensurePreconditions () {
    if (app.db().groups().size() == 0) {
      app.group().gotoGroupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) throws Exception {
      File photo = new File("src/test/resources/image.png");
      Groups groups = app.db().groups();
      app.goTo().homePage();
      Contacts before = app.db().contacts();
      app.contact().gotoAddNewContactPage();
      app.contact().fillNewContactForm(contact, true);
      app.contact().submitNewContactCreation();
      app.goTo().homePage();
      assertThat(app.contact().count(), equalTo(before.size() + 1));
      Contacts after = app.db().contacts();
      assertThat(after, equalTo(
              before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
      verifyContactListInUI();
  }

  @Test(enabled = false)
  public void testBedContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    app.contact().gotoAddNewContactPage();
    ContactData contact = new ContactData().withFirstname("Jeremy'").withLastname("Martinson")
            .withAddress("455 Larkspur Dr.\nCalifornia Springs, CA 92926\nUSA")
            .withPhoneHome("11111").withMobilePhone("22222").withWorkPhone("33333")
            .withEmail("jmartinson@yahoo.com").withEmail2("jmartinson2@yahoo.com")
            .withEmail3("jmartinson3@yahoo.com");
    app.contact().fillNewContactForm(contact, true);
    app.contact().submitNewContactCreation();
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before));
  }



}
