public class Tree{
    int x;
    int y;
    int maxAge;
    int age;
    boolean isBurning;

    public Tree(int x, int y, int maxAge) {
        this.x = x;
        this.y = y;
        this.maxAge = maxAge;
        this.age = 0;
        this.isBurning = false;
    }

    void growing() {
        if (this.age < this.maxAge) {
            this.age++;
        }
    }

    void burning() {
        if (this.age > 0) {
            this.age--;
        }
    }

}