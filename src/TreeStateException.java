public class TreeStateException extends RuntimeException {
 
    private final Tree.TreeState currentState;
 
    public TreeStateException(Tree.TreeState currentState, String attemptedAction) {
        super("Cannot " + attemptedAction + " while tree is in state " + currentState);
        this.currentState = currentState;
    }
 
    public Tree.TreeState getCurrentState() {
        return currentState;
    }
}
