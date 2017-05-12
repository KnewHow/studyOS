package ygh.study.os;

import java.util.Map;
import java.util.Map.Entry;

/**
 * A Class to describe the process
 * 
 * @author ygh 2017年5月11日
 */
public class Processers {

	/**
	 * The id of the process
	 */
	private Long id;
	/**
	 * The max resource the process need to execute The map key is the id of
	 * resource,value is integer number to stand for the resource's quantity
	 */
	private Map<String, Integer> max;

	/**
	 * The resource the system has allocate The map key is the id of
	 * resource,value is integer number to stand for the resource's quantity
	 */
	private Map<String, Integer> allocation;

	/**
	 * The remaining resource the process need The map key is the id of
	 * resource,value is integer number to stand for the resource's quantity
	 */
	private Map<String, Integer> need;

	/**
	 * Whether the process has been executed
	 */
	private Boolean hasExecute;

	/**
	 * A constructor of the process,it will be used in process initialization
	 * 
	 * @param id
	 * @param max
	 * @param allocation
	 * @param need
	 */
	public Processers(Long id, Map<String, Integer> max, Map<String, Integer> allocation, Map<String, Integer> need,
			Boolean hasExecuted) {
		super();
		this.id = id;
		this.max = max;
		this.allocation = allocation;
		this.need = need;
		this.hasExecute = hasExecuted;
	}

	public Boolean getHasExecute() {
		return hasExecute;
	}

	public Long getId() {
		return id;
	}

	public Map<String, Integer> getAllocation() {
		return allocation;
	}

	public void setAllocation(Map<String, Integer> allocation) {
		this.allocation = allocation;
	}

	public Map<String, Integer> getNeed() {
		return need;
	}

	public void setNeed(Map<String, Integer> need) {
		this.need = need;
	}

	public Map<String, Integer> getMax() {
		return max;
	}

	/**
	 * Whether the process can execute,if the need is all equal 0,indicate this
	 * process can execute otherwise not
	 * 
	 * @return True will be return if the process can be execute, otherwise
	 *         return false
	 */
	public Boolean isExecute() {
		Boolean flag = true;
		for (Entry<String, Integer> entry : need.entrySet()) {
			if (entry.getValue() != 0) {
				flag = false;
				return flag;
			}
		}
		return flag;
	}

	public void execute() {
		if (isExecute() && !this.getHasExecute()) {
			System.out.println("Thread-" + id + " is executing,it take " + toAllocationString());
			this.hasExecute = true;
		}
	}

	/**
	 * Get a string of the allocated resource,just to test execute method
	 * 
	 * @return
	 */
	public String toAllocationString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Integer> entry : allocation.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			sb.append(key).append(" resouce ").append(value).append("; ");
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return "Processers [id=" + id + ", max=" + max + ", allocation=" + allocation + ", need=" + need
				+ ", hasExecute=" + hasExecute + "]";
	}

}
