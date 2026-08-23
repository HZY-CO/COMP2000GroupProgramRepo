public class Fire extends Entity {
    int duration;

    public Fire(int x, int y, int duration) {
        super(x, y);
        this.duration = duration;
    }

    void spread(){

    }

    int fireIntensity(){
        return duration;
    }
}