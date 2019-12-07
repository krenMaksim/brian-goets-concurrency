package brian.goets.chapter5.semaphore;

public class BoundedHashSetMain {
    public static void main(String[] args) throws InterruptedException {
        BoundedHashSet<String> boundedHashSet = new BoundedHashSet<String>(10);

        for (int i = 1; i < 100; i++) {
            boundedHashSet.add(System.currentTimeMillis() + "");
            System.out.println(String.format("i=%d; %s; set: %s", i, Thread.currentThread().getName(), boundedHashSet));
        }
    }
}
