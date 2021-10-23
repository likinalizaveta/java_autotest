package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;


public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.group().gotoGroupPage();
    Set<GroupData> before = app.group().all();
    app.group().initGroupCreation();
    GroupData group = new GroupData().withName("test2");
    app.group().fillGroupForm(group);
    app.group().submitGroupCreation();
    app.group().returnToGroupPage();
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), before.size() + 1);

    group.withtId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    before.add(group);
    Assert.assertEquals(before, after);
  }

}
