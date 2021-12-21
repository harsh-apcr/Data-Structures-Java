package Tree;

public class BinaryTreeNode<T> {
    private BinaryTreeNode<T> parent,left,right;
    private T value;

    BinaryTreeNode(T value) {
        this.value = value;
        parent = null;
        left = null;
        right = null;
    }

    public BinaryTreeNode<T> getParent() {
        return parent;
    }

    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    public BinaryTreeNode<T> getRight() {
        return right;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) { this.value = value; }

    public void setParent(BinaryTreeNode<T> parent) {
        this.parent = parent;
    }

    public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }

    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }
}
