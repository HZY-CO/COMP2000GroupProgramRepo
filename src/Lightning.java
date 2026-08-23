public class Lightning extends Entity{
    int duration;

    public Lightning(int x, int y, int duration) {
        super(x, y);
        this.duration = duration;
    }

    void strike(){

    }

    int lightningIntensity(){
        return duration;
    }  
} 