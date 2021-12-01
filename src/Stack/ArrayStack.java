package Stack;

public class ArrayStack<E> implements MyStack<E> {
    private static final int CAP = 1024;
    private E[] S;
    private int top;

    public ArrayStack() {
        S = (E[]) new Object[CAP];
        top = -1;
    }

    public ArrayStack(int cap) {
        S = (E[])new Object[cap];
        top = -1;
    }

    @Override
    public void push(E e) {
        if (top < S.length-1) {
            S[++top] = e;
        }
        else {
            // top >= S.length-1
            // Array Growth Strategy
            E[] newStack = (E[])new Object[2*S.length];
            System.arraycopy(S,0,newStack,0,top);
            S = newStack;
            this.push(e);
        }
    }

    @Override
    public E pop() throws StackEmptyException {
        if (top < 0) throw new StackEmptyException();
        else {
            // top >= 0
            E sTop = S[top];
            S[top--] = null;
            if (top <= S.length/4 && S.length > CAP) {
                E[] newStack = (E[])new Object[S.length/2];
                System.arraycopy(S,0,newStack,0,top);
                S = newStack;
            }
            return sTop;
        }
    }

    @Override
    public E top() throws StackEmptyException {
        if (top < 0) throw new StackEmptyException();
        else {
            // top >= 0
            return S[top];
        }
    }

    @Override
    public boolean isEmpty() {
        return top < 0;
    }
}
