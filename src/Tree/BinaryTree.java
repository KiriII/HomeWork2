package Tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    public static class Node<T> {
        public final T value;

        public Node<T> left = null;

        public Node<T> right = null;

        public Node(T value) {
            this.value = value;
        }

        public T minValue(){
            if (left == null){
                return value;
            }
            return this.left.minValue();
        }

        public T maxValue(){
            if (right == null){
                return value;
            }
            return this.right.maxValue();
        }
    }

    public Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    @Override
    public boolean remove(Object o) {
        //throw new UnsupportedOperationException();
        if (root == null) throw new NoSuchElementException();
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> node = find(t);
        Node<T> father = findFather(root, node.value);
        if (node.left == null && node.right == null) {
            if (father.left == node) father.left = null;
            else father.right = null;
            return true;
        }
        if (node.left != null && node.right != null){
            T min = node.right.minValue();
            remove(find(node.right.minValue()).value);
            if (father.left == node) father.left = new Node<T>(min);
            else father.right = new Node<T>(min);
        }
        else {
            if (father.left == node) {
                father.left = (node.left != null) ? node.left : node.right;
            }
            if (father.right == node) {
                father.right = (node.left != null) ? node.left : node.right;
            }
        }
        node = null;
        size--;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    public Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    public Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        public Node<T> current = null;

        private BinaryTreeIterator() {}

        public Node<T> findNext() {
            Node<T> next ;
            Node<T> father = findFather(root , current.value);
            if (current.value == last()) return null;
            if (current == null) return find(first());
            else if (current.right == null && father.left == current)
                return father;
            else if (current.right != null){
                next = find(current.right.minValue());
            }
            else {
                next = current;
                while (next.value.compareTo(findFather(root , next.value).value) < 0){
                    next = findFather(root , next.value);
                }
                return next;
            }
            return next;
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement){
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    public Node<T> findFather(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (start.left != null){
            if (start.left.value == value) return start;
        }
        if (start.right != null) {
            if (start.right.value == value) return start;
        }
        if (comparison == 0) {

            return start;
        } else if (comparison < 0 ) {
            if (start.left != null)
                return findFather(start.left, value);
        } else {
            if (start.right != null )
            return findFather(start.right, value);
        }
        return start;
    }


}