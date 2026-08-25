public class Fire extends Entity {
    int duration;
    int intensity;
    boolean burning;
    static final int SPREAD_RADIUS = 1;

    public Fire(int x, int y, int duration, int intensity) {
        super(x, y);
        this.duration = duration;
        this.intensity = intensity;
        this.burning = true;
    }

    void spread(){

    }

    int fireIntensity(){
        return duration;
    }
}