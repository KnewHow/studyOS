
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The class is to show read process and writer process manage
 * 
 * @author ygh 2017
 */
class ReadAndWriter {
    public static final int SIZE = 1024 * 1024;

    public static final String filePath = "/workerspace/javaWorkSpace/OS-workspace/read-writer/demo0/desfile.txt";

    public static final String conetent = "t";

    public static void main(String[] args) {
        Resource r = new Resource();
        File file = new File(filePath);
        ReaderProcess re = new ReaderProcess(r, file);
        WriterProcess wr = new WriterProcess(r, file);
        Thread t0 = new Thread(re);
        Thread t1 = new Thread(re);

        Thread t2 = new Thread(wr);
        Thread t3 = new Thread(wr);
        t0.setPriority(10);
        t1.setPriority(10);
        t2.setPriority(1);
        t3.setPriority(1);

        t0.start();
        t1.start();
        t2.start();
        t3.start();

    }
}

/**
 * The read process implement class
 * 
 * @author ygh 2017
 */
class ReaderProcess implements Runnable {
    Resource r;

    File file;

    public ReaderProcess(Resource r, File file) {
        this.r = r;
        this.file = file;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r.read(file);
        }
    }
}

/**
 * the writer class implement class
 * 
 * @author ygh 2017 sdsd
 */
class WriterProcess implements Runnable {
    Resource r;

    File file;

    public WriterProcess(Resource r, File file) {
        this.r = r;
        this.file = file;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r.write(file);
        }
    }

}

/**
 * The class is to provide method for resource operate
 * 
 * @author ygh 2017
 */
class Resource {
    private Semaphore wmutex = new Semaphore(1);

    private Semaphore rwutex = new Semaphore(2);

    private static int readerCounter = 0;

    public void read(File file) {
        FileInputStream input = null;
        try {
            rwutex.acquire();
            if (readerCounter == 0) {
                wmutex.acquire();
            }
            readerCounter++;
            rwutex.release();
            input = new FileInputStream(file);
            byte[] buffer = new byte[ReadAndWriter.SIZE];
            while (input.read(buffer) != -1) {
                System.out.println(Thread.currentThread().getName() + "reader : "
                        + new String(buffer, "utf-8").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                rwutex.acquire();
                readerCounter--;
                if(readerCounter==0){
                    wmutex.release();
                }
                rwutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(File file) {
        Writer writer = null;
        try {
            wmutex.acquire();
            writer = new FileWriter(file, true);
            System.out.println(Thread.currentThread().getName() + ": writer");
            writer.write(ReadAndWriter.conetent);
        } catch (Exception e) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            wmutex.release();
        }
    }
}
