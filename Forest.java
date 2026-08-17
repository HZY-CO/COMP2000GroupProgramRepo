public class Forest {
    int timeElapsed;
    Tree[] trees;
    Fire[] fires;
    Lightning[] lightnings;

    Forest(Tree[] trees, Fire[] fires, Lightning[] lightnings) {
        this.timeElapsed = 0;
        this.trees = trees;
        this.fires = fires;
        this.lightnings = lightnings;
    }

    void timeElapsed() {
        // tree grows by 1 
        // new tree grows
        // new fire happens
    }

    void clear() {
        
    }
}
