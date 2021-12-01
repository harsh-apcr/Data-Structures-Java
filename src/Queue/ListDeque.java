package Queue;

public class ListDeque<E> implements MyDeque<E> , MyQueue<E> {
    private final DList<E> Q;

    public ListDeque() {
        this.Q = new DList<>();
    }

    @Override
    public void insertFirst(E e) {
        this.Q.insertAtFirst(e);
    }

    @Override
    public void insertLast(E e) {
        this.Q.insertAtLast(e);
    }

    @Override
    public E removeFirst() throws QueueEmptyException {
        try {
            return this.Q.deleteAtFirst();
        } catch (Queue.EmptyListException e) {
            throw new QueueEmptyException();
        }
    }

    @Override
    public E removeLast() throws QueueEmptyException {
        try {
        return this.Q.deleteAtLast();
        } catch (Queue.EmptyListException e) {
            throw new QueueEmptyException();
        }
    }

    @Override
    public E first() throws QueueEmptyException {
        try {
            return this.Q.first();
        } catch (Queue.EmptyListException e) {
            throw new QueueEmptyException();
        }
    }

    @Override
    public E last() throws QueueEmptyException {
        try {
            return this.Q.last();
        } catch (Queue.EmptyListException e) {
            throw new QueueEmptyException();
        }
    }

    @Override
    public boolean isEmpty() {
        return this.Q.isEmpty();
    }

    @Override
    public int size() {
        return this.Q.size();
    }
}
