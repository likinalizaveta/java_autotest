package ru.stqa.autotest.sandbox;

public class Point {
  private final double x;
  private final double y;

  public Point(double x1, double y1) {
    this.x = x1;
    this.y = y1;
  }

  public double distance(Point p2) {
    return Math.sqrt((p2.x- this.x) * (p2.x-this.x) + (p2.y - this.y) * (p2.y - this.y));
  }


}