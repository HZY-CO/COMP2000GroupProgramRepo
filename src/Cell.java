public class Cell extends Position {
    private Tree tree;

    public Cell(int x, int y) {
        super(x, y);
    }

    public Cell(Position position) {
        this(position.getX(), position.getY());
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }
}
