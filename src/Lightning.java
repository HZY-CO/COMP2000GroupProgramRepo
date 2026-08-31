public class Lightning extends Entity{
    int duration;

    public Lightning(int x, int y, int duration) {
        super(x, y);
        this.duration = duration;
    }

    public void update(){
    }

    int lightningIntensity(){
        return duration;
    }  
} 