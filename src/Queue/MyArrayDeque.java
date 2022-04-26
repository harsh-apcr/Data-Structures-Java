package Queue;

public class MyArrayDeque<E> implements MyDeque<E> , MyQueue<E> {
    private static final int CAP = 1024;
    private final E[] Q;
    private final int cap;
    private int r;
    private int f;

    @SuppressWarnings("unchecked")
    public MyArrayDeque() {
        Q = (E[])new Object[CAP];
        r = 0;f = 0;
        cap = CAP;
    }

    @SuppressWarnings("unchecked")
    public MyArrayDeque(int cap) {
        Q = (E[])new Object[cap];
        this.cap = cap;
        r = 0;f = 0;
    }

    @Override
    public void insertFirst(E e) throws QueueFullException {
        if (this.size() == this.cap-1) throw new QueueFullException();
        else {
            // size < cap-1
            Q[f] = e;
            f = (cap+f-1)%cap;
        }

    }

    @Override
    public void insertLast(E e) throws QueueFullException {
        if (this.size() == this.cap-1) throw new QueueFullException();
        else {
            // size < cap-1
            Q[r] = e;
            r = (r+1)%cap;
        }
    }

    @Override
    public E removeFirst() throws QueueEmptyException {
        if (this.isEmpty()) throw new QueueEmptyException();
        else {
            // non-empty queue
            E toReturn = Q[f];
            Q[f] = null;
            f = (f+1)%cap;
            return toReturn;
        }
    }

    @Override
    public E removeLast() throws QueueEmptyException {
        if (this.isEmpty()) throw new QueueEmptyException();
        else {
            // non-empty queue
            E toReturn = Q[r];
            Q[r] = null;
            r = (cap+r-1)%cap;
            return toReturn;
        }
    }

    @Override
    public E first() throws QueueEmptyException {
        return Q[f];
    }

    @Override
    public E last() throws QueueEmptyException {
        return Q[r];
    }

    @Override
    public boolean isEmpty() {
        return r == f;
    }

    @Override
    public int size() {
        return (cap + r - f)%cap;
    }
}
