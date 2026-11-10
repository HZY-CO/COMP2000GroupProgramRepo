public class FlatSpread implements Spread {
    @Override
    public double chance(Fire fire, Tree tree, Wind wind) {
        return fire.fireIntensity() / 100.0; //ignore wind entirely
    }
}
