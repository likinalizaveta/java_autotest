package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactDeleteFromGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstname("Jeremy")
              .withLastname("Martinson").withAddress("455 Larkspur Dr.\nCalifornia Springs, CA 92926\nUSA")
              .withPhoneHome("11111").withMobilePhone("22222").withWorkPhone("33333")
              .withEmail("jmartinson@yahoo.com").withEmail2("jmartinson2@yahoo.com").withEmail3("jmartinson3@yahoo.com"));
    }
    if (app.db().groups().size() == 0) {
      app.group().gotoGroupPage();
      app.group().create(new GroupData().withName("test1"));
    }
    if (app.db().contacts().iterator().next().getGroups().size() == 0) {
      Groups groups = app.db().groups();
      GroupData selectedGroup = groups.iterator().next();
      Contacts contacts = app.db().contacts();
      ContactData selectedContact = contacts.iterator().next();
      app.contact().addToGroup(selectedContact, selectedGroup);
    }
  }

  @Test
  public void testContactDeleteFromGroup () {
    Contacts beforeContact = app.db().contacts();
    Groups beforeGroups = app.db().groups();
    ContactData selectedContact = beforeContact.iterator().next();
    GroupData selectedGroup = beforeGroups.iterator().next();
    app.goTo().homePage();
    if (selectedContact.getGroups().isEmpty() || !selectedContact.getGroups().contains(selectedGroup)) {
      app.contact().selectDisplayGroup("[all]");
      app.contact().addToGroup(selectedContact, selectedGroup);
      assertThat(selectedContact.getGroups().withAdded(selectedGroup), equalTo(app.db().contacts().stream().
              filter((c) -> c.getId() == selectedContact.getId()).collect(Collectors.toList()).get(0).getGroups()));
      app.goTo().homePage();
    }
    app.contact().deleteFromGroup(selectedContact, selectedGroup);
    Contacts afterContact = app.db().contacts();
    assertThat(selectedContact.getGroups().withoutAdded(selectedGroup), equalTo(app.db().contacts().stream().
            filter((c) -> c.getId() == selectedContact.getId()).collect(Collectors.toList()).get(0).getGroups()));
    verifyContactListInUI();
  }

}
