//Readers-Writers Problem 

import java.util.concurrent.Semaphore;

class WritersPreference{

    static Semaphore rmutex = new Semaphore(1);
    static Semaphore wmutex = new Semaphore(1);
    static Semaphore resource = new Semaphore(1);
    static Semaphore readTry = new Semaphore(1);
    static int readCount = 0;
    static int writeCount = 0;
    
    static class Write implements Runnable {
        public void run() {
        	try {
        		   //Acquire Section
                wmutex.acquire();
                writeCount++;
                if (writeCount == 1) {
                    readTry.acquire();
                }
                wmutex.release();

                //Reading section
                resource.acquire();
                System.out.println(""+Thread.currentThread().getName() + " is WRITING");
                Thread.sleep(2500);
                System.out.println(""+Thread.currentThread().getName() + " has FINISHED WRITING");
                resource.release();
                
                //Releasing section
                wmutex.acquire();
                writeCount--;
                if(writeCount == 0) {
                    readTry.release();
                }
                wmutex.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Read implements Runnable {
        public void run() {        	
            try {
                //Acquire Section
            	readTry.acquire();
                rmutex.acquire();
                readCount++;
                if (readCount == 1) {
                    resource.acquire();
                }
                rmutex.release();
                readTry.release();

                //Reading section
                System.out.println(""+Thread.currentThread().getName() + " is READING");
                Thread.sleep(2000);
                System.out.println(""+Thread.currentThread().getName() + " has FINISHED READING");

                //Releasing section
                rmutex.acquire();
                readCount--;
                if(readCount == 0) {
                	resource.release();
                }
                rmutex.release();
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
         Thread t4 = new Thread(write);
         t4.setName("thread4");
         t1.start();
         t3.start();
         t2.start();
         t4.start();
    }
}
