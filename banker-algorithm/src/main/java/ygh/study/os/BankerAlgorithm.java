package ygh.study.os;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankerAlgorithm {
	public static void main(String[] args) {
		Map<Long, Processers> processMap = initProcessersMap();
		List<Processers> processList = initProcessersList();
		Resource resource = initResource(3, 3, 2);
		Caller caller = new Caller(processMap, resource);
		List<Processers> orderProcessList = caller.getSafeExecuteOrder(processList);
		resource = initResource(3, 3, 2);
		if (orderProcessList == null) {
			System.out.println("The system is not safe");
		} else {
			System.out.println("The system is safe");
			new Allocation().execte(orderProcessList, resource);
		}
	}

	/**
	 * Initialize some process to test
	 * 
	 * @return
	 */
	public static Map<Long, Processers> initProcessersMap() {
		Map<Long, Processers> map = new HashMap<Long, Processers>();
		map.put(0L, initProcess(0L, 7, 5, 3, 7, 4, 3, 0, 1, 0));
		map.put(1L, initProcess(1L, 3, 2, 2, 1, 2, 2, 2, 0, 0));
		map.put(2L, initProcess(2L, 9, 0, 2, 6, 0, 0, 3, 0, 2));
		map.put(3L, initProcess(3L, 2, 2, 2, 0, 1, 1, 2, 1, 1));
		map.put(4L, initProcess(4L, 4, 3, 3, 4, 3, 1, 0, 0, 2));
		return map;
	}

	public static List<Processers> initProcessersList() {
		List<Processers> list = new ArrayList<Processers>();
		list.add(initProcess(0L, 7, 5, 3, 7, 4, 3, 0, 1, 0));
		list.add(initProcess(1L, 3, 2, 2, 1, 2, 2, 2, 0, 0));
		list.add(initProcess(2L, 9, 0, 2, 6, 0, 0, 3, 0, 2));
		list.add(initProcess(3L, 2, 2, 2, 0, 1, 1, 2, 1, 1));
		list.add(initProcess(4L, 4, 3, 3, 4, 3, 1, 0, 0, 2));
		return list;
	}

	public static Processers initProcess(Long id, int maxA, int maxB, int maxC, int needA, int needB, int needC,
			int allocA, int allocB, int allocC) {
		Map<String, Integer> max = new HashMap<String, Integer>();
		max.put("A", maxA);
		max.put("B", maxB);
		max.put("C", maxC);

		Map<String, Integer> need = new HashMap<String, Integer>();
		need.put("A", needA);
		need.put("B", needB);
		need.put("C", needC);

		Map<String, Integer> allocation = new HashMap<String, Integer>();
		allocation.put("A", allocA);
		allocation.put("B", allocB);
		allocation.put("C", allocC);
		Processers p = new Processers(id, max, allocation, need, false);
		return p;

	}

	public static Resource initResource(int availaleA, int availaleB, int availaleC) {
		Map<String, Integer> avaiable = new HashMap<String, Integer>();
		avaiable.put("A", availaleA);
		avaiable.put("B", availaleB);
		avaiable.put("C", availaleC);
		Resource resource = new Resource(avaiable);
		return resource;
	}

}
