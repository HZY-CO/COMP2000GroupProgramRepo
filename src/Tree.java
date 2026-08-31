public class Tree extends Entity{
    int maxAge;
    int age;
    boolean isBurning;

    public Tree(int x, int y, int maxAge, int age, boolean isBurning) {
        super(x, y);
        this.maxAge = maxAge;
        this.age = age;
        this.isBurning = isBurning;
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

    public void update() {
        
    }

}