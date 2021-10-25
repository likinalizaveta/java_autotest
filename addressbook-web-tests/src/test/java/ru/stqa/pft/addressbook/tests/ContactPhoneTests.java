package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() ==0) {
      app.contact().create(new ContactData().withFirstname("Jeremy").withLastname("Martinson").withAddress("455 Larkspur Dr. \nCalifornia Springs, CA 92926 \nUSA").withPhoneHome("1-212-123 45 67").withEmail("jmartinson@yahoo.com").withGroup("test1"));
    }
  }

  @Test
  public void testContactPhones() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getPhoneHome(), equalTo(cleaned(contactInfoFromEditForm.getPhoneHome())));
    assertThat(contact.getMobilePhone(), equalTo(cleaned(contactInfoFromEditForm.getMobilePhone())));
    assertThat(contact.getWorkPhone(), equalTo(cleaned(contactInfoFromEditForm.getWorkPhone())));
  }

    public String cleaned(String phone) {
      return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }


}
