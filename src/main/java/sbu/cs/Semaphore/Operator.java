package sbu.cs.Semaphore;

import java.time.LocalTime;
import java.util.concurrent.Semaphore;

public class Operator extends Thread {

    public Operator(String name) {
        super(name);
    }

    Semaphore semaphore = new Semaphore(2);

    @Override
    public void run() {
        for (int i = 0; i < 10; i++)
        {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Resource.accessResource();         // critical section - a Maximum of 2 operators can access the resource concurrently
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                semaphore.release();
                LocalTime currentTime = LocalTime.now();
                System.out.println(Thread.currentThread().getName() + " " + currentTime);
            }
        }
    }
}
