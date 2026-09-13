public class ForestDimensionException extends RuntimeException {
    private final int width;
    private final int height;
 
    public ForestDimensionException(int width, int height) {
        super("Forest dimensions must be positive, got width=" + width + ", height=" + height);
        this.width = width;
        this.height = height;
    }
 
    public int getWidth() {
        return width;
    }
 
    public int getHeight() {
        return height;
    }
}
 
