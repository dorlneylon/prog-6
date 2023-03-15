package itmo.lab6.basic.utils.generators;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * IdGenerator class. Used to generate unique ids.
 *
 * @author kxrxh
 */
public final class IdGenerator {
    /**
     * Unique id. Starts from 1
     */
    private static final AtomicLong longIdCounter = new AtomicLong(new Random().nextLong() & Long.MAX_VALUE);
    /**
     * Unique id. Starts from 1
     */
    private static final AtomicInteger intIdCounter = new AtomicInteger(new Random().nextInt() & Integer.MAX_VALUE);

    /**
     * Generate unique id of type long
     *
     * @return unique id
     */
    public static Long generateLongId() {
        return longIdCounter.incrementAndGet();
    }

    /**
     * Generate unique id of type int
     *
     * @return unique id
     */
    public static Integer generateIntId() {
        return intIdCounter.incrementAndGet();
    }
}
