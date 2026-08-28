public class TestWind {
  public static void main(String[] args) {
    Wind w = new Wind(0, 0, 10);
    w.changeDirection(100, 80, 70);
    System.out.println("x=" + w.x + ", y=" + w.y + ", power=" + w.windPower);
  }
}
