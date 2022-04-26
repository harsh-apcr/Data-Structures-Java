package Queue;
// Adapter Pattern
public interface MyQueue<E> extends MyDeque<E> {
    default void enqueue(E e) throws QueueFullException {
        insertLast(e);
    }
    default E dequeue() throws QueueEmptyException {
        return removeFirst();
    }

}
