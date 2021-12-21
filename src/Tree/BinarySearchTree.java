package Tree;

import com.sun.source.tree.Tree;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class BinarySearchTree<T> extends BinaryTree<T> {
    private final Comparator<T> comparator;

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void insert(T value) {
        if(this.root.getRight() ==null) {
            this.root.setRight(new BinaryTreeNode<>(value));
            this.root.getRight().setParent(root);
        }
        else {
            BinaryTreeNode<T> currNode = this.root.getRight();
            while (true) {
                if (comparator.compare(value, currNode.getValue()) > 0) {
                    if (currNode.getRight() != null) {
                        currNode = currNode.getRight();
                    } else {
                        currNode.setRight(new BinaryTreeNode<>(value));
                        currNode.getRight().setParent(currNode);
                        break;
                    }
                } else {
                    if (currNode.getLeft() != null) {
                        currNode = currNode.getLeft();
                    } else {
                        currNode.setLeft(new BinaryTreeNode<>(value));
                        currNode.getLeft().setParent(currNode);
                        break;
                    }
                }
            }
        }
    }

    /**
     * @param value
     * @return the first occurrence of value (w.r.t level of tree)
     * @throws TreeEmptyException,ValueNotFoundException
     */
    public BinaryTreeNode<T> search(T value) throws TreeEmptyException,ValueNotFoundException {
        if (this.root.getRight() == null) throw new TreeEmptyException();
        else {
            BinaryTreeNode<T> currNode = this.root.getRight();
            while(currNode!=null) {
                if (currNode.getValue().equals(value)) return currNode;
                else if (comparator.compare(value,currNode.getValue()) > 0) {
                    currNode = currNode.getRight();
                }
                else {
                    currNode = currNode.getLeft();
                }
            }
            throw new ValueNotFoundException();
        }
    }

    private T getMax(@NotNull BinaryTreeNode<T> node) {
        while(true) {
            if(node.getRight()!=null) {
                node = node.getRight();
            }
            else {
                return node.getValue();
            }
        }
    }

    private T getMin(@NotNull BinaryTreeNode<T> node) {
        while(true) {
            if(node.getLeft()!=null) {
                node = node.getLeft();
            }
            else {
                return node.getValue();
            }
        }
    }


    /**
     * @param value
     * @return returns the largest value in BinarySearchTree<T> this with value <= value(input)
     * @throws TreeEmptyException
     * @throws ValueNotFoundException
     */
    public T predecessor(T value) throws TreeEmptyException,ValueNotFoundException {
        BinaryTreeNode<T> currNode = this.search(value);
        if (currNode.getLeft()!=null) {
            return getMax(currNode.getLeft());
        }
        else {
            // find an ancestor of currNode whose right child is also an ancestor
            BinaryTreeNode<T> parNode = currNode.getParent();
            while(parNode!=root && parNode.getLeft() == currNode) {
                currNode = parNode;
                parNode = currNode.getParent();
            }
            if (parNode != root) return parNode.getValue(); // parNode.getRight() == currNode
            else return null;
        }
    }

    /**
     * @param value
     * @return returns the largest value in BinarySearchTree<T> this with value > value(input)
     * @throws TreeEmptyException
     * @throws ValueNotFoundException
     */
    public T successor(T value) throws TreeEmptyException,ValueNotFoundException {
        BinaryTreeNode<T> currNode = this.search(value);
        if (currNode.getRight()!=null) {
            return getMin(currNode.getRight());
        }
        else {
            // find an ancestor of currNode whose left child is also an ancestor
            BinaryTreeNode<T> parNode = currNode.getParent();
            while(parNode!=root && parNode.getRight() == currNode) {
                currNode = parNode;
                parNode = currNode.getParent();
            }
            if (parNode != root) return parNode.getValue(); // parNode.getLeft() == currNode
            else return null;

        }
    }

    public void delete(T value) throws TreeEmptyException {
        if(this.root.getRight() ==null) throw new TreeEmptyException();
        else {
            BinaryTreeNode<T> currNode = this.root.getRight();
            while(currNode!=null && !currNode.getValue().equals(value)) {
                if (comparator.compare(value, currNode.getValue()) > 0) {
                    currNode = currNode.getRight();
                } else if (comparator.compare(value, currNode.getValue()) < 0) {
                    currNode = currNode.getLeft();
                }
                else {
                    // delete currNode
                    // case 1 - leaf
                    // case 2 - parent of a single child
                    // case 3 - has 2 children
                    // complete the block
                    
                }
            }
        }
    }








}