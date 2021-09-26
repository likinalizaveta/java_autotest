package ru.stqa.autotest.sandbox;

public class CalculatingDistance {

  public static void main(String[] args) {

    Point p1 = new Point(2,3);
    Point p2 = new Point(7,5);
    System.out.println("Расстояние между точками (" + p1.x +";" + p1.y + ") и (" + p2.x +";" + p2.y + ") = " + distance(p1,p2));
  }

  public static double distance(Point p1, Point p2) {
    return  Math.sqrt((p2.x- p1.x) * (p2.x-p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
  }

}




