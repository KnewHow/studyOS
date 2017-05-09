package schudule.algorithm.EDF;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A class to show EDF(Earliest Deadline First)
 * 
 * @author ygh 2017年5月8日
 */
public class LLFDemo {

	/**
	 * Just a test method
	 * @param args
	 */
	public static void main(String[] args) {
		Crotrol crotrol = new Crotrol();
		crotrol.execute(initProcess());

	}

	/**
	 * Initialize some  process to test
	 * @return a <code>Map</code> with key is the process id and the value is 
	 * the process
	 */
	public static Map<Long, Progresser> initProcess() {
		int a = 8;
		int b = 3;
		long counter=1L;
		Map<Long, Progresser> map = new HashMap<Long, Progresser>();
		for (int i = 1; i <= a; i++) {
			Progresser p = new Progresser(counter, "A-" + i, 0, 0, 10, i * 20, 0, false);
			map.put(p.getId(), p);
			counter++;
		}

		for (int i = 1; i <= b; i++) {
			Progresser p = new Progresser(counter, "B-" + i, 0, 0, 25, i * 50, 0, false);
			map.put(p.getId(), p);
			counter++;
		}
		return map;
	}
}

/**
 * A class to control the 
 * @author ygh
 * 2017年5月9日
 */
class Crotrol {
	private Executer execute = new Executer();
	
	/**
	 * A method to control the CPU deal machine
	 * @param map
	 */
	public void execute(Map<Long, Progresser> map) {
		long counter=0;
		while (!map.isEmpty()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			deleteFinishProgrocesser(map);
			counter=counter+1;
			Long id = getLeastLaxity(map, counter);
			Progresser prog = map.get(id);
			if (prog!=null) {
				execute.run(prog);
			} else {
//				throw new RuntimeException("the process is null");
			}
		}
	}

	/**
	 * Get least laxity process id from processes pool
	 * @param map The map to store the processed
	 * @param currentTime The current time using millisecond
	 * @return A process id that is least laxity
	 */
	public Long getLeastLaxity(Map<Long, Progresser> map, long currentTime) {
		long min = Long.MAX_VALUE;
		long laxity = 0;
		Long processId = -1L;
		for (Entry<Long, Progresser> entry : map.entrySet()) {
			Long key = entry.getKey();
			Progresser prog = entry.getValue();
			laxity = prog.getFinishExecuteTime() - prog.getResultTime() - currentTime;
			if (laxity < min) {
				min = laxity;
				processId = key;
			}
		}
		return processId;
	}

	/**
	 * Delete the process which has finished from processes pool
	 * @param map The map to store the process
	 */
	public void deleteFinishProgrocesser(Map<Long, Progresser> map) {
		Long id=-1L;
		for (Entry<Long, Progresser> entry : map.entrySet()) {
			if (map.get(entry.getKey()).isFinish()) {
				id=entry.getKey();
			}
		}
		if(id!=-1L){
			map.remove(id);
		}
	}
}


/**
 * A class to execute process
 * @author ygh
 * 2017年5月9日
 */
class Executer{

	/**
	 * A method is responsible for process run
	 * @param prog
	 */
	public void run(Progresser prog) {
		if (prog == null) {
			throw new RuntimeException("The process is null");	
		} else {
			prog.execute();
		}
	}
}


/**
 * A class describe progress
 * 
 * @author ygh 2017年5月8日
 */
class Progresser {

	/*
	 * The id of the process
	 */
	private Long id;

	/*
	 * The name of the process
	 */
	private String processName;

	/*
	 * The time the process arrive
	 */
	private long arriveTime;

	/*
	 * The start time the process must be executed
	 */
	private long startExecuteTime;

	/*
	 * The time the process need to execute
	 */
	private long executeTime;

	/*
	 * The time the process must be finished
	 */
	private long finishExecuteTime;

	/*
	 * The time the process has been executed
	 */
	private long hasExecuteTime;

	/*
	 * Whether the process has been finished
	 */
	private boolean isFinish;

	/**
	 * Get the process id
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * A constructor of <code>Processer</code>
	 * 
	 * @param id
	 * @param processName
	 * @param arriveTime
	 * @param startExecuteTime
	 * @param finishExecuteTime
	 * @param hasExecuteTime
	 * @param isFinish
	 */
	public Progresser(Long id, String processName, long arriveTime, long startExecuteTime, long executeTime,
			long finishExecuteTime, long hasExecuteTime, boolean isFinish) {
		super();
		this.id = id;
		this.processName = processName;
		this.arriveTime = arriveTime;
		this.startExecuteTime = startExecuteTime;
		this.executeTime = executeTime;
		this.finishExecuteTime = finishExecuteTime;
		this.hasExecuteTime = hasExecuteTime;
		this.isFinish = isFinish;
	}

	/**
	 * Whether the process has been finished
	 * 
	 * @return True will be return if the process has been finish, otherwise
	 *         return false
	 */
	public boolean isFinish() {
		return isFinish;
	}

	/**
	 * How much time t the process has been executed
	 * 
	 * @return
	 */
	public long getHasExecuteTime() {
		return hasExecuteTime;
	}

	/**
	 * Get process name
	 * 
	 * @return A name of the process
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * Get the time of the process arrive the process queue
	 * 
	 * @return
	 */
	public long getArriveTime() {
		return arriveTime;
	}

	/**
	 * Get time the process must be finished executing
	 * 
	 * @return
	 */
	public long getFinishExecuteTime() {
		return finishExecuteTime;
	}

	/**
	 * Get the time the process must be started executing
	 * 
	 * @return
	 */
	public long getStartExecuteTime() {
		return startExecuteTime;
	}

	/**
	 * Get the time the process need time
	 * 
	 * @return
	 */
	public long getExecuteTime() {
		return executeTime;
	}
	
	/**
	 * Get the time the process result
	 * @return
	 */
	public long getResultTime(){
		return (this.getExecuteTime()-this.getHasExecuteTime());
	}

	/**
	 * The process execute method
	 */
	public void execute() {
		if (!this.isFinish) {
			this.hasExecuteTime++;
			System.out.println(this.getProcessName() + "has been executed time:" + this.getHasExecuteTime());
			if (this.getHasExecuteTime() == (this.getExecuteTime())) {
				this.isFinish = true;
			}
		}

	}
}