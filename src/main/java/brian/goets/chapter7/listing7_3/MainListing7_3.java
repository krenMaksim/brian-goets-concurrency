package brian.goets.chapter7.listing7_3;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MainListing7_3 {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<>(1);
        BrokenPrimeProducer producer = new BrokenPrimeProducer(queue);

        new Thread(producer).start();

        // Thread.sleep(1_000);

        producer.cancel();
        System.out.println("finish main thread");

    }
}
