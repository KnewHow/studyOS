package ygh.study.os;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Allocation {

	/**
	 * A method to implement process execute
	 * @param list
	 * @param resource
	 */
	public void execte(List<Processers> list, Resource resource) {
		for (Processers process : list) {
			System.out.println(resource.toString());
			this.allocate(process, resource);
			process.execute();
			System.out.println("=======================================");
			this.release(process, resource);
		}

	}

	/**
	 * A method to allocate current system resource to process
	 * 
	 * @param processe
	 * @param resource
	 */
	public void allocate(Processers processe, Resource resource) {
		Map<String, Integer> need = processe.getNeed();
		Map<String, Integer> allocation = processe.getAllocation();
		Map<String, Integer> available = resource.getAvaiable();
		for (Entry<String, Integer> entry : need.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			Integer ava = available.get(key);
			Integer hasAllocation = allocation.get(key);
			Integer result = ava - value;
			Integer currentAllocation = hasAllocation + value;
			available.put(key, result);
			allocation.put(key, currentAllocation);
			value = 0;
			need.put(key, value);
		}
	}

	/**
	 * Release a process taking resource when this process has been execute
	 * 
	 * @param processe
	 *            A process has been executed
	 * @param resource
	 *            The resource current system provides
	 */
	public void release(Processers processe, Resource resource) {
		Map<String, Integer> allocation = processe.getAllocation();
		Map<String, Integer> available = resource.getAvaiable();
		for (Entry<String, Integer> entry : allocation.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			Integer ava = available.get(key);
			Integer result = ava + value;
			available.put(key, result);
		}

	}

	/**
	 * A method to judge whether current resource is enough to allocate for this
	 * process
	 * 
	 * @param processe
	 *            The process need to be allocated resource
	 * @param resource
	 *            The current system resource
	 * @return True will be return if current resource is enough for the
	 *         process, otherwise will return false
	 */
	public Boolean isSafe(Processers processe, Resource resource) {
		Boolean able = true;
		Map<String, Integer> need = processe.getNeed();
		for (Entry<String, Integer> entry : need.entrySet()) {
			String key = entry.getKey();
			if (need.get(key) > resource.getAvaiable().get(key)) {
				able = false;
				return able;
			}
		}
		return able;
	}
}