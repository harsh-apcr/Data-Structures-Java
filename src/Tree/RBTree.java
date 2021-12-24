package Tree;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class RBTree<T> extends BinarySearchTree<T>{
    private int blackHeight;

    public RBTree(Comparator<T> comparator) {
        super(comparator);
    }

    private static <E> void rightRotate(@NotNull BinaryTreeNode<E> x,@NotNull BinaryTreeNode<E> y) {
        // y.parent != null (Restriction)
            if (y.getParent().getLeft() == y) y.getParent().setLeft(x);
            else y.getParent().setRight(x);
            x.setParent(y.getParent());
            y.setParent(x);
            if (x.getRight()!=null) x.getRight().setParent(y);
            y.setLeft(x.getRight());
            x.setRight(y);
    }

    private static <E> void leftRotate(@NotNull BinaryTreeNode<E> x,@NotNull BinaryTreeNode<E> y) {
        // x.parent != null (Restriction)
            if (x.getParent().getLeft() == x) x.getParent().setLeft(y);
            else x.getParent().setRight(y);
            y.setParent(x.getParent());
            x.setParent(y);
            if (y.getLeft()!=null) y.getLeft().setParent(x);
            x.setRight(x.getLeft());
            y.setLeft(x);
    }

    @Override
    public void insert(T value) {
        if(this.root.getRight() ==null) {
            this.root.setRight(new BinaryTreeNode<>(value));
            this.root.getRight().setParent(root);
        }
        else {
            boolean isRight = false;
            BinaryTreeNode<T> currNode = this.root.getRight();
            while (true) {
                if (this.comparator.compare(value, currNode.getValue()) > 0) {
                    if (currNode.getRight() != null) {
                        currNode = currNode.getRight();
                    } else {
                        isRight = true;
                        currNode.setRight(new BinaryTreeNode<>(value,false));
                        currNode.getRight().setParent(currNode);
                        break;
                    }
                } else {
                    if (currNode.getLeft() != null) {
                        currNode = currNode.getLeft();
                    } else {
                        currNode.setLeft(new BinaryTreeNode<>(value,false));
                        currNode.getLeft().setParent(currNode);
                        break;
                    }
                }
            }
            if(currNode.isBlack()) return;
            else {
                // currNode.getParent() != this.root as currNode.color == red
                BinaryTreeNode<T> newNode = isRight ? currNode.getRight() : currNode.getLeft();
                do {
                    if (currNode.getParent().getLeft() == currNode) {
                        if (!currNode.getParent().getRight().isBlack()) {
                            // black uncle case
                            currNode.getParent().getRight().setColorBlack(true);
                            currNode.getParent().setColorBlack(false);
                            currNode.setColorBlack(true);
                            newNode = currNode.getParent();
                            currNode = newNode.getParent();
                        } else {
                            if (isRight) leftRotate(currNode, newNode);
                            rightRotate(newNode,newNode.getParent());
                            // newNode's parent is now currNode's parent
                            // we have already asserted currNode.getParent() != this.root
                            newNode.getParent().setColorBlack(false);
                            newNode.setColorBlack(true);
                            break;
                        }
                    }
                    else {
                        // repeat this code
                    }
                    if (currNode == this.root) {
                        newNode.setColorBlack(true);
                        break;
                    }
                } while(!currNode.isBlack() && !currNode.getParent().isBlack());
            }
        }
    }
}
