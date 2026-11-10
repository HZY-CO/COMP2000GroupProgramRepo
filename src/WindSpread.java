public class WindSpread implements Spread {
    @Override 
    public double chance(Fire fire, Tree tree, Wind wind) {
        double base = fire.fireIntensity() / 100.0;
        return Math.min (base + windInfluence(fire, tree, wind), 1.0); // cap at 100%
    }

    private double windInfluence(Fire fire, Tree tree, Wind wind){
        int dx = tree.getPosition().x - fire.getPosition().x;
        int dy = tree.getPosition().y - fire.getPosition().y;
        if (dx == 0 && dy == 0) {
            return 0; // same psotion no influence
        }

        double length = Math.sqrt(dx*dx + dy*dy);
        double dirX = dx/length;
        double dirY = dy/length;

        double windX = wind.getX();
        double windY = wind.getY();
        double windLength = Math.sqrt(windX * windX + windY * windY);

        if (windLength == 0){
            return 0; // no wind, no influence
        }

        windX /= windLength;
        windY /= windLength;

        double allignment = (dirX * windX) + (dirY * windY);
        double normalisedPower = wind.getWindPower() / 100.0;

        return Math.max(allignment, 0) * normalisedPower * 0.3; // the 0.3 factor is a scaling factor to reduce the influence of wind on fire spread
    }
    
}
