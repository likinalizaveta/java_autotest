package ru.stga.autotest.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.autotest.sandbox.Point;

public class PointTests {

  @Test
// Позитивный тест на полное совпадение значения
  public void test1() {
    Point p1 = new Point(2, 3);
    Point p2 = new Point(7, 5);
    Assert.assertEquals(p2.distance(p1), 5.385164807134504);
  }

  @Test
  // Проверить, что результат не равен 0
  public void test2() {
    Point p1 = new Point(2, 3);
    Point p2 = new Point(7, 5);
    Assert.assertNotEquals(p2.distance(p1), 0);
  }

  }

