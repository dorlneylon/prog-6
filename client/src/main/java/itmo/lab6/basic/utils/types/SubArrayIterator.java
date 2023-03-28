package itmo.lab6.basic.utils.types;
import java.util.*;

public class SubArrayIterator<T> implements Iterator<T[]> {
    private T[] array;
    private int batchSize;
    private int index = 0;

    public SubArrayIterator(T[] array, int batchSize) {
        this.array = array;
        this.batchSize = batchSize;
    }

    @Override
    public boolean hasNext() {
        return index < array.length;
    }

    @Override
    public T[] next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        int endIndex = Math.min(index + batchSize, array.length);
        T[] batch = Arrays.copyOfRange(array, index, endIndex);
        index += batchSize;

        return batch;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
