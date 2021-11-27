package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.GroupData;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void homePage() {
    click(By.linkText("home"));
  }

  public void addNewPage() {
    click(By.linkText("add new"));
  }

  public void groupPage(GroupData group) {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText(group.getName());
  }

}
