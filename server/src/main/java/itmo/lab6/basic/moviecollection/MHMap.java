package itmo.lab6.basic.moviecollection;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The abstract class {@code MHMap} is a wrapper for the {@code HashMap} class.
 * It provides a set of methods for working with the {@code HashMap} class.
 * It is inherited by the {@code MovieCol} class.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author dorlneylon
 * @see MovieCollection
 */
public abstract class MHMap<K, V> {
    /**
     * A constant to represent the white color code in ANSI escape codes.
     */
    public static final String whcr = "\u001B[0m";

    /**
     * A constant to represent the purple color code in ANSI escape codes.
     */
    public static final String prcr = "\u001B[35m";
    private final ZonedDateTime initTime;
    /**
     * the map itself
     */
    private HashMap<K, V> map;

    /**
     * the default constructor
     */
    public MHMap() {
        this.map = new HashMap<>();
        this.initTime = ZonedDateTime.now();
    }

    /**
     * the insert method
     */
    public boolean insert(V value) {
        try {
            this.map.put(getKey(value), value);
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    /**
     * insert with the given key
     */
    public boolean insert(K key, V value) {
        if (!this.map.containsKey(key)) {
            this.map.put(key, value);
            return true;
        } else {
            System.out.println("The value with this key already exists. Please, use the update command.");
            return false;
        }
    }

    /**
     * get the key by the value
     */
    public abstract K getKey(V value);

    /**
     * remove the value by the key
     */
    public boolean removeByKey(K key) {
        if (containsKey(key)) {
            this.map.remove(key);
            return true;
        } else return false;
    }

    /**
     * get all the elements of the map in pairs ({@link K}, {@link V})
     */
    public Object[][] toArray() {
        Object[][] array = new Object[this.size()][2];
        int i = 0;
        for (K key : this.map.keySet()) {
            array[i][0] = key;
            array[i][1] = this.map.get(key);
            i++;
        }
        return array;
    }

    /**
     * remove the value from the map
     */
    public void rmByVal(V val) {
        this.removeByKey(this.getKey(val));
    }

    /**
     * assign the map
     */
    public void assign(MHMap<K, V> map) {
        this.map = map.getMap();
    }

    /**
     * get the value by the key
     */
    public V get(K key) {
        return this.map.get(key);
    }

    /**
     * sort the map elements by a comparator and print them
     */
    public abstract void printAscending();

    /**
     * reverse sort the map elements by a comparator and print them
     */
    public abstract void printDescending();

    /**
     * update the value by the key
     */
    public void update(K key, V value) {
        if (containsKey(key)) {
            this.map.put(key, value);
            System.out.println("Done!");
        } else System.out.println("No such value");
    }

    /**
     * update the value
     */
    public void update(V value) {
        this.update(this.getKey(value), value);
    }

    /**
     * get the init time
     */
    public java.time.ZonedDateTime getInitDate() {
        return this.initTime;
    }

    /**
     * clear the map
     */
    public void clear() {
        this.map.clear();
    }

    /**
     * get the size of the map
     */
    public int size() {
        return this.map.size();
    }

    /**
     * check if the map is empty
     */
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    /**
     * check if the map contains the key for the value
     */
    public abstract boolean contains(V value);

    /**
     * check if the map contains a certain key
     */
    protected boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    /**
     * check if the map contains the value
     */
    protected boolean containsValue(V value) {
        return this.map.containsValue(value);
    }

    /**
     * sort the values
     *
     * @return the sorted array of values
     */
    public abstract V[] sorted(boolean reverse);

    /**
     * get the map
     */
    public HashMap<K, V> getMap() {
        return this.map;
    }

    /**
     * get the keys of the map
     */
    public abstract V[] values();

    /**
     * Print the information about the collection:
     * elements' type, date of initialization, number of elements, etc.
     */
    public String info() {
        Class<?> a = this.map.isEmpty() ? null : this.map.entrySet().stream().toList().get(0).getKey().getClass();
        String keyTypeName = a == null ? "none" : a.getName();
        return  prcr + "Type: " + whcr + this.getClass().getName() + "\n" +
                prcr + "Key type: " + whcr + keyTypeName + "\n" +
                prcr + "Date of initialization: " + whcr + this.getInitDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "\n" +
                prcr + "Number of elements: " + whcr + this.size() + "\n";
    }
}
