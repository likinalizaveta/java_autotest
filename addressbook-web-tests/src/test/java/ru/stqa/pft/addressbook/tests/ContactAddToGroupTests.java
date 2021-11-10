package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactAddToGroupTests extends TestBase {

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
  }

  @Test
  public void testContactAddToGroup() {
    Contacts beforeContact = app.db().contacts();
    Groups beforeGroups = app.db().groups();
    ContactData selectedContact = beforeContact.iterator().next();
    GroupData selectedGroup = beforeGroups.iterator().next();
    app.goTo().homePage();
    app.contact().addToGroup(selectedContact, selectedGroup);
    Contacts afterContact = app.db().contacts();
    assertThat(afterContact.iterator().next().getGroups(), equalTo(beforeContact.iterator().next().getGroups().withAdded(selectedGroup)));
    verifyContactListInUI();
  }


}
