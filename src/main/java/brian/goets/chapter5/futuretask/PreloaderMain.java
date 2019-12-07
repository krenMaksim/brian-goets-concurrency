package brian.goets.chapter5.futuretask;

public class PreloaderMain {
    public static void main(String[] args) {
        Preloader preloader = new Preloader();
        preloader.start();

        doSomethingInMainThread();

        try {
            Preloader.ProductInfo productInfo = preloader.get();
            System.out.println(String.format("productInfo: %s", productInfo));
        } catch (DataLoadException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("FINISH PreloaderMain %s", Thread.currentThread().getName()));
    }

    private static void doSomethingInMainThread() {
        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("i=%d; %s", i, Thread.currentThread().getName()));
        }
    }
}
