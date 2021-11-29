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
import static org.testng.Assert.assertEquals;

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
    app.goTo().homePage();
  }

  @Test
  public void testContactDeleteFromGroup () {
    GroupData group = selectGroupToTest();
    Contacts groupContactsBefore = app.db().contactsInGroupById(group.getId());

    ContactData contact = groupContactsBefore.iterator().next();
    Groups contactGroupsBefore = app.db().contactById(contact.getId()).getGroups();

    app.goTo().groupPage(group.getId());
    app.contact().deleteFromGroup(contact);

    Contacts groupContactsAfter = app.db().contactsInGroupById(group.getId());
    Groups contactGroupsAfter = app.db().contactById(contact.getId()).getGroups();

    assertEquals(contactGroupsAfter.size(), contactGroupsBefore.size() - 1);
    assertThat(contactGroupsAfter, equalTo(contactGroupsBefore.withoutAdded(app.db().groupById(group.getId()))));

    assertEquals(groupContactsAfter.size(), groupContactsBefore.size() - 1);
    assertThat(groupContactsAfter, equalTo(groupContactsBefore.withoutAdded(app.db().contactById(contact.getId()))));

  }

  private GroupData selectGroupToTest() {
    Groups groups = app.db().groups();
    for (GroupData group : groups) {
      if (app.db().contactsInGroupById(group.getId()).size() > 0) {
        return group;
      }
    }
    GroupData groupForTest = groups.iterator().next();
    Contacts contacts = app.db().contacts();
    app.contact().addToGroup(contacts.iterator().next(), groupForTest);
    app.goTo().homePage();
    return groupForTest;
  }

}
