//Readers-Writers Problem 

import java.util.concurrent.Semaphore;

class ReadersPreference{

    static Semaphore mutex = new Semaphore(1);
    static Semaphore resource = new Semaphore(1);
    static int readCount = 0;

    static class Read implements Runnable {
        public void run() {        	
            try {
                //Acquire Section
                mutex.acquire();
                readCount++;
                if (readCount == 1) {
                    resource.acquire();
                }
                mutex.release();

                //Reading section
                System.out.println(""+Thread.currentThread().getName() + " is READING");
                Thread.sleep(2000);
                System.out.println(""+Thread.currentThread().getName() + " has FINISHED READING");

                //Releasing section
                mutex.acquire();
                readCount--;
                if(readCount == 0) {
                    resource.release();
                }
                mutex.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Write implements Runnable {
        public void run() {
        	try {
                resource.acquire();
                System.out.println(""+Thread.currentThread().getName() + " is WRITING");
                Thread.sleep(2500);
                System.out.println(""+Thread.currentThread().getName() + " has finished WRITING");
                resource.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
    	
    	 Read read = new Read();
         Write write = new Write();
         Thread t1 = new Thread(read);
         t1.setName("thread1");
         Thread t2 = new Thread(read);
         t2.setName("thread2");
         Thread t3 = new Thread(write);
         t3.setName("thread3");
         Thread t4 = new Thread(read);
         t4.setName("thread4");
         t1.start();
         t3.start();
         t2.start();
         t4.start();
    }
}
