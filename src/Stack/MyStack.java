package Stack;

public interface MyStack<E> {
    void push(E e);
    E pop() throws StackEmptyException;
    E top() throws StackEmptyException;
    boolean isEmpty();

}

