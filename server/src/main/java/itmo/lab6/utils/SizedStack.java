package itmo.lab6.utils;

import java.util.Stack;

public class SizedStack<T> extends Stack<T> {
    private final long maxSize;

    /**
     * Instantiates a new Sized stack.
     *
     * @param maxSize the max size
     */
    public SizedStack(long maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Pushes an item onto the top of this stack and removes the first element if stack size is greater than maxSize.
     *
     * @param item the item to be pushed onto this stack.
     */
    @Override
    public T push(T item) {
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push(item);
    }
}
