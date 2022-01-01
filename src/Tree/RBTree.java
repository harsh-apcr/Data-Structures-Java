package Tree;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;


public class RBTree<T> extends BinarySearchTree<T>{

    public RBTree(Comparator<T> comparator) {
        super(comparator);
    }

    private static <E> void rightRotate(@NotNull BinaryTreeNode<E> x,@NotNull BinaryTreeNode<E> y) {
        // y.parent != null (Restriction)
        if (y.getParent().getLeft() == y) y.getParent().setLeft(x);
        else y.getParent().setRight(x);
        x.setParent(y.getParent());
        y.setParent(x);
        if (x.getRight() != null) x.getRight().setParent(y);
        y.setLeft(x.getRight());
        x.setRight(y);
    }

    private static <E> void leftRotate(@NotNull BinaryTreeNode<E> x,@NotNull BinaryTreeNode<E> y) {
        // x.parent != null (Restriction)
        if (x.getParent().getLeft() == x) x.getParent().setLeft(y);
        else x.getParent().setRight(y);
        y.setParent(x.getParent());
        x.setParent(y);
        if (y.getLeft() != null) y.getLeft().setParent(x);
        x.setRight(y.getLeft());
        y.setLeft(x);
    }

    public static <E> boolean RBtreeSanityCheck(@NotNull RBTree<E> tree) {
        BinaryTreeNode<E> node = tree.root.getRight();
        if (node == null) return true; // vacuously true
        if (!node.isBlack()) return false;
        else
            return BinarySearchTree.BSTreeSanityCheck(tree)
                && colorCheck(node)
                && blackHeight(node) != -1;
    }

    private static <E> int blackHeight(BinaryTreeNode<E> node) {
        if (node == null) return 0;
        else {
            final int blackHeightLeft = blackHeight(node.getLeft());
            final int blackHeightRight = blackHeight(node.getRight());
            if (blackHeightLeft != -1 && blackHeightRight != -1) {
                if (!node.isBlack())
                    return (blackHeightLeft == blackHeightRight) ?
                            blackHeightLeft :
                            -1;
                else
                    return (blackHeightLeft == blackHeightRight) ?
                            blackHeightLeft + 1 :
                            -1;
            }
            else return -1;
        }
    }

    private static <E> boolean colorCheck(BinaryTreeNode<E> node) {
        if (node == null) return true;
        else {
            try {
                if (!node.isBlack()
                        && (!node.getLeft().isBlack() || !node.getRight().isBlack())) return false;
                // node.isBlack() || ( (node.getLeft().isBlack) && (node.getRight().isBlack()) )
            } catch (NullPointerException e) {
                // !node.isBlack()
                if (node.getLeft() == null) {
                    if (node.getRight() != null && !node.getRight().isBlack()) return false;
                    //else - node.right == null || node.right.isBlack() , check subtrees
                }
//                if (node.getRight() == null) {
//                    // since || is short circuit node.left!=null && node.left.isblack = true
//                    // just check the left subtree and right subtree
//                }
            }
            return colorCheck(node.getRight()) && colorCheck(node.getLeft());
        }
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
            if(!currNode.isBlack()) {
                // red-red clash
                // currNode.getParent() != this.root as currNode.color == red
                BinaryTreeNode<T> newNode = isRight ? currNode.getRight() : currNode.getLeft();
                do {
                    if (currNode.getParent().getLeft() == currNode) {
                        if (currNode.getParent().getRight() != null && !currNode.getParent().getRight().isBlack()) {
                            // red uncle case
                            currNode.getParent().getRight().setColorBlack(true);
                            currNode.getParent().setColorBlack(false);
                            currNode.setColorBlack(true);
                            newNode = currNode.getParent();
                            currNode = newNode.getParent();
                            isRight = currNode.getRight() == newNode;
                        } else {
                            // black uncle case
                            if (isRight) {
                                leftRotate(currNode, newNode);
                                newNode = currNode;
                                currNode = currNode.getParent();
                            }
                            currNode.getParent().setColorBlack(false);
                            currNode.setColorBlack(true);
                            rightRotate(currNode,currNode.getParent());
                            // newNode's parent is now currNode's parent
                            // we have already asserted currNode.getParent() != this.root
                            break;
                        }
                    }
                    else {
                        // currNode.parent.right == currNode
                        if (currNode.getParent().getLeft() != null && !currNode.getParent().getLeft().isBlack()) {
                            // red uncle case
                            currNode.getParent().getLeft().setColorBlack(true);
                            currNode.getParent().setColorBlack(false);
                            currNode.setColorBlack(true);
                            newNode = currNode.getParent();
                            currNode = newNode.getParent();
                            isRight = currNode.getRight() == newNode;
                        } else {
                            // black uncle case
                            if (!isRight) {
                                rightRotate(newNode,currNode);
                                newNode = currNode;
                                currNode = currNode.getParent();
                            }
                            currNode.getParent().setColorBlack(false);
                            currNode.setColorBlack(true);
                            leftRotate(currNode.getParent(),currNode);
                            // newNode's parent is now currNode's parent
                            // we have already asserted currNode.getParent() != this.root
                            break;
                        }
                    }
                } while(!newNode.isBlack() && !currNode.isBlack());
                if (currNode == this.root && !newNode.isBlack()) newNode.setColorBlack(true);
            }
        }
    }

    // currNode.left == null || currNode.right == null
    private static <T> void deleteNodeAtMostOneChild(@NotNull BinaryTreeNode<T> currNode) {
        // currNode.parent != null
        boolean isRight = currNode.getParent().getRight() == currNode;
        // currNode has at most one child
        // if not a leaf node then it must be black parent of a red node
        if (currNode.getLeft() != null) {
            currNode.getLeft().setColorBlack(true);
            currNode.getLeft().setParent(currNode.getParent());
            if (isRight) currNode.getParent().setRight(currNode.getLeft());
            else currNode.getParent().setLeft(currNode.getLeft());
        } else if (currNode.getRight() != null) {
            currNode.getRight().setColorBlack(true);
            currNode.getRight().setParent(currNode.getParent());
            if (isRight) currNode.getParent().setRight(currNode.getRight());
            else currNode.getParent().setLeft(currNode.getRight());
        } else {
            // currNode is a leaf
            // red-leaf
            if (!currNode.isBlack()) {
                if (isRight) currNode.getParent().setRight(null);
                else currNode.getParent().setLeft(null);
            } else {
                // black-leaf
                deleteBlackLeaf(currNode);
            }
        }
    }

    private static <T> void deleteBlackLeaf(@NotNull BinaryTreeNode<T> currNode) {
        // deletion of a black leaf
        BinaryTreeNode<T> parNode = currNode.getParent();
        boolean isRight = parNode.getRight() == currNode;
        if(parNode.getParent() == null) parNode.setRight(null);
        else {
            do {
                if (isRight) {
                    BinaryTreeNode<T> sibling = parNode.getLeft();
                    if (sibling.isBlack()) {
                        // case 1 - sibling is black and has a red child
                        // case 1.1 - forming a straight line
                        if (sibling.getLeft()!= null && !sibling.getLeft().isBlack()) {
                            sibling.setColorBlack(parNode.isBlack());
                            currNode.setColorBlack(true);
                            parNode.setColorBlack(true);
                            sibling.getLeft().setColorBlack(true);
                            rightRotate(sibling, parNode);
                            break;
                        }
                        // case 1.2 - not forming a straight line
                        else if (sibling.getRight()!=null && !sibling.getRight().isBlack()) {
                            BinaryTreeNode<T> siblingChild = sibling.getRight();
                            sibling.setColorBlack(false);
                            siblingChild.setColorBlack(true);
                            leftRotate(sibling, siblingChild);
                            siblingChild.setColorBlack(parNode.isBlack());
                            currNode.setColorBlack(true);
                            parNode.setColorBlack(true);
                            sibling.setColorBlack(true);
                            rightRotate(siblingChild, parNode);
                            break;
                        } else {
                            // (sibling.left == null || sibling.left = black)&&(sibling.right == null || sibling.right = black)
                            // case 2 - sibling is black and both children are black
                            currNode.setColorBlack(true);
                            sibling.setColorBlack(false);
                            if (!parNode.isBlack()) {
                                parNode.setColorBlack(true);
                                break;
                            }
                            else {
                                currNode = parNode; // continue resolving for currNode
                                parNode = currNode.getParent();
                                isRight = parNode.getRight() == currNode;
                            }
                        }
                    } else {
                        // sibling is red
                        // parNode is black
                        sibling.setColorBlack(true);
                        parNode.setColorBlack(false);
                        rightRotate(sibling, parNode);
                        // repeat resolving for currNode
                    }
                } else {
                    BinaryTreeNode<T> sibling = parNode.getRight();
                    if (sibling.isBlack()) {
                        // case 1.1 - forming a straight line
                        if (sibling.getRight()!=null && !sibling.getRight().isBlack()) {
                            sibling.setColorBlack(parNode.isBlack());
                            currNode.setColorBlack(true);
                            parNode.setColorBlack(true);
                            sibling.getRight().setColorBlack(true);
                            leftRotate(parNode, sibling);
                            break;
                        }
                        // case 1.2 - not forming a straight line
                        else if (sibling.getLeft()!=null && !sibling.getLeft().isBlack()) {
                            BinaryTreeNode<T> siblingChild = sibling.getLeft();
                            sibling.setColorBlack(false);
                            siblingChild.setColorBlack(true);
                            rightRotate(siblingChild,sibling);
                            siblingChild.setColorBlack(parNode.isBlack());
                            currNode.setColorBlack(true);
                            parNode.setColorBlack(true);
                            sibling.setColorBlack(true);
                            leftRotate(parNode,siblingChild);
                            break;
                        } else {
                            // (sibling.left == null || sibling.left = black)&&(sibling.right == null || sibling.right = black)
                            // case 2 - sibling is black and both children are black
                            currNode.setColorBlack(true);
                            sibling.setColorBlack(false);
                            if (!parNode.isBlack()) {
                                parNode.setColorBlack(true);
                                break;
                            }
                            else {
                                currNode = parNode; // continue resolving for currNode
                                parNode = currNode.getParent();
                                isRight = parNode.getRight() == currNode;
                            }
                        }
                    } else {
                        // sibling is red
                        // parNode is black
                        sibling.setColorBlack(true);
                        parNode.setColorBlack(false);
                        leftRotate(parNode, sibling);
                        // repeat resolving for currNode
                    }
                }
            } while (parNode.getParent() != null);
        }
    }

    @Override
    public void delete(T value) throws TreeEmptyException,ValueNotFoundException {
        if (this.root.getRight() == null) throw new TreeEmptyException();
        else {
            BinaryTreeNode<T> currNode = this.root.getRight();
            while (currNode != null) {
                if (comparator.compare(value, currNode.getValue()) > 0) {
                    currNode = currNode.getRight();
                } else if (comparator.compare(value, currNode.getValue()) < 0) {
                    currNode = currNode.getLeft();
                } else {
                    // found the node to be deleted
                    if(currNode.getLeft()==null || currNode.getRight() == null) deleteNodeAtMostOneChild(currNode);
                    else {
                        // parent of two children
                        BinaryTreeNode<T> newNode = currNode.getRight();
                        while(true) {
                            if (newNode.getLeft() != null) {
                                newNode = newNode.getLeft();
                            }
                            else {
                                // reached the successor
                                // newNode has at-most one child
                                deleteNodeAtMostOneChild(newNode);
                                break;
                            }
                        }
                    }
                    break;
                }
                if(currNode == null) throw new ValueNotFoundException();
            }
        }
    }






}




    

//    public static <E> void testColor(@NotNull RBTree<E> tree) {
//        testColor(tree.root.getRight());
//    }
//    private static <E> void testColor(@NotNull BinaryTreeNode<E> node) {
//        if (node.getLeft()!=null) testColor(node.getLeft());
//        System.out.print(node.isBlack() ? "black " : "red ");
//        if (node.getRight()!=null) testColor(node.getRight());
//
//    }

