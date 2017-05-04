package process.manage.philosopher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A Class to demo philosopher eating question.
 * @author ygh
 * May 4, 2017
 */
public class PhilosopherEat {
	
	/**
	 * Setting the quantity of philosophers
	 */
	public static final int TOTAL_MOUNT = 100;

	/*
	 * Just a test method
	 */
	public static void main(String[] args) {
		
		Resource r = new Resource();
		/**
		 * A list to store the philosophers
		 */
		List<Philosopher> philosopherList = new ArrayList<Philosopher>();
		for(int i=1;i<=TOTAL_MOUNT;i++){
			Philosopher philosopher = new Philosopher("Philosopher "+i, i, false);
			philosopherList.add(philosopher);
		}
		
		/**
		 * A list to store threads with philosopher and resource
		 */
		List<Thread> threadList = new ArrayList<Thread>();
		for(int i=1;i<=TOTAL_MOUNT;i++){
			Thread thread = new Thread(new Eat(philosopherList.get(i-1), r));
			threadList.add(thread);
		}
		
		/**
		 * start thread method.
		 */
		for(int i=1;i<=TOTAL_MOUNT;i++){
			threadList.get(i-1).start();
		}
		
//		Philosopher p1 = new Philosopher("Philosopher 1", 1, false);
//		Philosopher p2 = new Philosopher("Philosopher 2", 2, false);
//		Philosopher p3 = new Philosopher("Philosopher 3", 3, false);
//		Philosopher p4 = new Philosopher("Philosopher 4", 4, false);
//		Philosopher p5 = new Philosopher("Philosopher 5", 5, false);
//		Thread t1 = new Thread(new Eat(p1, r));
//		Thread t2 = new Thread(new Eat(p2, r));
//		Thread t3 = new Thread(new Eat(p3, r));
//		Thread t4 = new Thread(new Eat(p4, r));
//		Thread t5 = new Thread(new Eat(p5, r));
//		t1.start();
//		t2.start();
//		t3.start();
//		t4.start();
//		t5.start();
	}

}

/**
 * Define a Eat class to implement Runnable
 * Eat action need two class: philosoper(person) and resource(food)
 * @author ygh
 * May 4, 2017
 */
class Eat implements Runnable {

	private Philosopher philosopher;
	private Resource resource;
	
	/**
	 * Constructor of the Eat class to initialize attributes
	 * @param philosopher The philosopher apply to eat
	 * @param resource	The resource eating need
	 */
	public Eat(Philosopher philosopher, Resource resource) {
		super();
		this.philosopher = philosopher;
		this.resource = resource;
	}

	/**
	 * The run method to execute many threads.
	 */
	public void run() {
		while (true) {
			resource.eat(philosopher);
		}
	}

}

/**
 * Define a resource class to initialize the chopsticks tag array and implement eat method
 * @author ygh
 * May 4, 2017
 */
class Resource {
	Lock lock = new ReentrantLock();
	private static int[] chopsticks = new int[PhilosopherEat.TOTAL_MOUNT];

	/**
	 * The no parameters constructor method of Resource to initialize chopticks tag array 
	 */
	public Resource() {
		super();
		for (int i = 0; i < chopsticks.length; i++) {
			chopsticks[i] = 1;
		}
	}

	/**
	 * The method to control a philosopher eating.
	 * If the chopsticks is enough, eating, else wait.
	 * @param philosopher The philosopher apply to eat action
	 */
	public void eat(Philosopher philosopher) {
		try {
			lock.lock();
			/**
			 * If the resource in not enough,let philosopher wait.
			 */
			while (philosopher.isEat() || !philosopher.isCanEat(chopsticks)) {
				wait();
			}
			philosopher.eat(chopsticks);
			philosopher.setEat(true);
//			Thread.sleep(40);
			philosopher.finishEat(chopsticks);
			notify();
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}
}

/**
 * 
 * @author ygh
 * May 4, 2017
 * Define a philosopher class to implement some functions.
 */
class Philosopher {

	/**
	 * The name is the name of the philosopher
	 */
	private String name;

	/**
	 * The id of the philosopher
	 */
	private int id;

	/**
	 * Whether the philosopher has eaten.
	 * true:the philosopher has eaten
	 * false:the philosopher has not eaten
	 */
	private boolean isEat;

	/**
	 * The constructor of Philosopher
	 * @param name The name is the name of the philosopher
	 * @param id The id of the philosopher
	 * @param isEat Whether the philosopher has eaten,true:the philosopher has eaten
	 * 	otherwise flase.
	 */
	public Philosopher(String name, int id, boolean isEat) {
		super();
		this.name = name;
		this.id = id;
		this.isEat = isEat;
	}

	/**
	 * get and set methods
	 */
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isEat() {
		return isEat;
	}

	public void setEat(boolean isEat) {
		this.isEat = isEat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * The philosopher eat functions, in the here we just print a input to console
	 * The functions if following:
	 * 	first: philosopher get two chopsticks that we will let chopsticks array that index are philosopher'one 
	 *  and philosopher's two set to be 0, the print a input to console.\
	 *  
	 * @param arr The array to store philosopher's chopsticks tags
	 */
	public void eat(int[] arr) {
		List<Integer> chopsList = this.getChopsticks();
		arr[chopsList.get(0) - 1] = 0;
		arr[chopsList.get(1) - 1] = 0;
		System.out.println(
				this.getName() + " is eating with " + "chopstick " + chopsList.get(0) + " and " + chopsList.get(1));
	}

	/**
	 * Finish eat function,we will release the chopstics the philosopher's has taken
	 * @param arr The array to store philosopher's chopsticks tags
	 */
	public void finishEat(int arr[]) {
		List<Integer> chopsList = this.getChopsticks();
		arr[chopsList.get(0) - 1] = 1;
		arr[chopsList.get(1) - 1] = 1;
	}

	/**
	 * Whether the philosopher can eat.We will set a N(N is the total quantity of the philosophers) length integer array to
	 * store the chopsticks tags, and we will initialize it all to 1.
	 * 1 indicates the index.th choptick can be used
	 * 0 indicates the index.th choptick is being used,can be used now!
	 * 
	 * If the arr[philosopher's one]==1 and arr[philosopher's two]==1 indicates
	 * the philosopher can eat, otherwise can't
	 * @param arr The integer array to store the chopticks tags
	 * @return True will be returned if the philosopher can eat otherwise return false
	 */
	public boolean isCanEat(int[] arr) {
		List<Integer> chopsList = getChopsticks();

		if (arr[chopsList.get(0) - 1] == 1 && arr[chopsList.get(1) - 1] == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the philosopher's chopsticks by philosopher's id
	 * when the philosopher's id not equal one,the chopstick one is the
	 * philosopher's id and two is the philosopher's id -1.
	 * But if the philosoper's id is equal one the chopstick one is the
	 * philosopher's id and philosopher total mounts.
	 * 
	 * @return a tow element list of integer to store the chopstick numbers which the philosopher
	 * will use.
	 */
	public List<Integer> getChopsticks() {
		int chop_one = this.getId();
		int chop_two = 0;
		if (chop_one == 1) {
			chop_two = PhilosopherEat.TOTAL_MOUNT;
		} else {
			chop_two = chop_one - 1;
		}
		List<Integer> chopsList = new ArrayList<Integer>();
		chopsList.add(chop_one);
		chopsList.add(chop_two);
		return chopsList;
	}

}