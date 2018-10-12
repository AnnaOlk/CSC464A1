public class DiningPhilosopher {

    public static void main(String[] args) {
        Object forks[] = new Object[5];
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }


        Philosopher A = new Philosopher(forks, 'A');
        Philosopher B = new Philosopher(forks, 'B');
        Philosopher C = new Philosopher(forks, 'C');
        Philosopher D = new Philosopher(forks, 'D');
        Philosopher E = new Philosopher(forks, 'E');

        A.start();
        B.start();
        C.start();
        D.start();
        E.start();

    }
}

class Philosopher extends Thread {
    Object forks[];
    Object leftFork;
    Object rightFork;
    char name;

    Philosopher(Object forks[], char name) {
        this.forks = forks;
        this.name = name;
        switch (name) {
            case 'A':
                leftFork = forks[0];
                rightFork = forks[1];
                break;
            case 'B':
                leftFork = forks[1];
                rightFork = forks[2];
                break;
            case 'C':
                leftFork = forks[2];
                rightFork = forks[3];
                break;
            case 'D':
                leftFork = forks[3];
                rightFork = forks[4];
                break;
            case 'E':
                //Order is flipped for this philosopher
                leftFork = forks[0];
                rightFork = forks[4];
                break;
            default:
                System.out.println("Invalid philosopher name!");
                System.exit(1);
        }
    }


    public void run() {
        int time;
        while (true) {
            try {
                System.out.println("Philosopher " + name + ": attempt to acquire fork to left");
                synchronized (leftFork) {
                    System.out.println("Philosopher " + name + ": acquired left fork");
                    System.out.println("Philosopher " + name + ": attempt to acquire fork to right");
                    synchronized (rightFork) {
                        System.out.println("Philosopher " + name + ": acquired right fork");
                        time = (int) (Math.random() * 10 + 1);
                        System.out.println("Philosopher " + name + ": eating for " + time + " seconds");
                        sleep(time * 1000);
                    }
                    System.out.println("Philosopher " + name + ": dropped right fork");
                }
                System.out.println("Philosopher " + name + ": dropped left fork");
                time = (int) (Math.random() * 10 + 1);
                System.out.println("Philosopher " + name + ": thinking for " + time + " seconds");
                sleep(time * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
