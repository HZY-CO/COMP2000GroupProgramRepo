public class Fire extends Entity {
    int duration;
    int intensity;
    boolean burning;

    public Fire(int x, int y, int duration, int intensity) {
        super(x, y);
        this.duration = duration;
        this.intensity = intensity;
        this.burning = true;
    }

    public void update(){

    }

    int fireIntensity(){
        return duration;
    }
}