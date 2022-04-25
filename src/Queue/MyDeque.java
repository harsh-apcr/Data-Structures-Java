package Queue;

public interface MyDeque<E> {
    void insertFirst(E e) throws QueueFullException;
    void insertLast(E e) throws QueueFullException;
    E removeFirst() throws QueueEmptyException;
    E removeLast() throws QueueEmptyException;
    E first() throws QueueEmptyException;
    E last() throws QueueEmptyException;
    boolean isEmpty();
    int size();
}
