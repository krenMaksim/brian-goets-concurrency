package brian.goets.chapter7.listing7_5;

import java.util.concurrent.ArrayBlockingQueue;

public class MainListing7_5 {

    public static void main(String[] args) throws InterruptedException {
        PrimeProducer producer = new PrimeProducer(new ArrayBlockingQueue<>(1));
        producer.start();

        Thread.sleep(1_000);

        producer.cancel();
        System.out.println("finish main thread");
    }
}
