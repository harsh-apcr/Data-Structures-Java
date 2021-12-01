package Queue;

public class DListNode<T> {

    DListNode<T> next;
    DListNode<T> prev;
    T value;

    public DListNode() {
        value = null;
        next = null;prev = null;
    }

    public DListNode(T value) {
        this.value = value;
        this.next = null;this.prev = null;
    }
}
