
import java.util.*;

public class LinkList<E> implements List<E> {

    private int size;
    private final Node<E> head;
    private Node<E> tail;

    public LinkList() {
        size = 0;
        head = nullNode();
        tail=head;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> curr = head.next;
        while (curr!= null) {
            sb.append(curr.val);
            if (curr.next!= null) {
                sb.append(", ");
            }
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        Node<E> newNode = new Node<>(e);
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        int i=0;
        Node<E> curr = head.next;
        while (curr!= null) {
            if (i==index) {
                return curr.val;
            }
            curr = curr.next;
            i++;
        }
        return null;
    }

    @Override
    public E set(int index, E element) {
        int i=0;
        Node<E> curr = head.next;
        while (curr!= null) {
            if (i==index) {
                E oldVal = curr.val;
                curr.val = element;
                return oldVal;
            }
            curr = curr.next;
            i++;
        }
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        Node<E> curr = head.next;
        int i=0;
        while (curr!= null) {
            if (curr.val.equals(o)) {
                return i;
            }
            curr = curr.next;
            i++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<E> curr = tail;
        int i=size-1;
        while (curr!= null&&curr.val!=null) {
            if (curr.val.equals(o)) {
                return i;
            }
            curr = curr.prev;
            i--;
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    private static class Node<E> {
        E val;
        Node next;
        Node prev;
        Node (E val) {
            this.val = val;
            next=null;
            prev=null;
        }
        Node (E val, Node next) {
            this.val = val;
            this.next = next;
            prev=null;
        }
        Node (E val, Node next, Node prev) {
            this.val = val;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<E> nullNode(){
        return new Node<E>(null);
    }

    public static void main(String[] args) {
        LinkList<Integer> linkList = new LinkList<>();
        for (int i = 0; i < 10; i++) {
            linkList.add(i);
        }

    }
}
