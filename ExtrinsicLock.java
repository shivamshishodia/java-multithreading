import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

class Worker implements Runnable {

	String name;
	ReentrantLock re;

	public Worker(ReentrantLock re, String name) {
		this.re = re;
		this.name = name;
	}

	@Override
	public void run() {

		boolean done = false;

		while (!done) {

			// Getting Outer Lock
			boolean ans = re.tryLock();

			// Returns True if lock is free
			if (ans) {
				try {
					Date d = new Date();
					SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
					System.out.println("Job Name: " + name + ". Outer lock acquired at " + ft.format(d) + ". Doing outer work before entering inner lock.");
					Thread.sleep(1500);

					// Getting Inner Lock
					re.lock();

					try {
						d = new Date();
						ft = new SimpleDateFormat("hh:mm:ss");
						System.out.println("Job Name: " + name + ". Inner lock acquired at " + ft.format(d) + ". Doing inner work.");
						System.out.println("Lock Hold Count: " + re.getHoldCount());
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						// Inner lock release
						System.out.println("Job Name: " + name + ". Releasing inner lock.");
						re.unlock();
					}

					System.out.println("Lock Hold Count: " + re.getHoldCount());
					System.out.println("Job Name: " + name + ". Work done.");

					done = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					// Outer lock release
					System.out.println("Job Name: " + name + ". Releasing outer lock.");
					re.unlock();
					System.out.println("Lock Hold Count: " + re.getHoldCount());
				}
			} else {
				System.out.println("Job Name: " + name + ". Waiting for lock.");

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

public class ExtrinsicLock {

	public static final int MAX_T = 2;

	public static void main(String[] args) {

		ReentrantLock rel = new ReentrantLock();
		ExecutorService pool = Executors.newFixedThreadPool(MAX_T);

		Runnable w1 = new Worker(rel, "Job-1");
		Runnable w2 = new Worker(rel, "Job-2");
		Runnable w3 = new Worker(rel, "Job-3");
		Runnable w4 = new Worker(rel, "Job-4");

		pool.execute(w1);
		pool.execute(w2);
		pool.execute(w3);
		pool.execute(w4);
		
		pool.shutdown();

	}

}