public class Forest {
    private EntityManager<Tree> trees = new EntityManager<Tree>();
    private EntityManager<Fire> fires = new EntityManager<Fire>();
    private EntityManager<Lightning> lightnings = new EntityManager<Lightning>();
    private EntityManager<Wind> winds = new EntityManager<Wind>();

    public void addTree(Tree tree) {
        trees.add(tree);
    }

    public void addFire(Fire fire){
        fires.add(fire);
    }

    public void addLightning(Lightning lightning) {
        lightnings.add(lightning);
    }

    public void addWind(Wind wind) {
        winds.add(wind);
    }

    public EntityManager<Tree> getTrees() {
        return trees;
    }

    public EntityManager<Fire> getFires() {
        return fires;
    }

    public EntityManager<Lightning> getLightnings() {
        return lightnings;
    }

    public EntityManager<Wind> getWinds() {
        return winds;
    }

    public void update() {
        
    }
}
