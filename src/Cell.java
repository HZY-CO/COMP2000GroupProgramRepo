public class Cell {
    private final Position position;

    private Tree tree;

    public Cell(Position position) {
        this.position = position;
        this.tree = null;
    }

    public Position getPosition() {
        return position;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }
}