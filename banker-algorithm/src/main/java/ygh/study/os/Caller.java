package ygh.study.os;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Caller {

	private Map<Long, Processers> map;

	private Resource resource;

	private Allocation allocation;

	public Caller(Map<Long, Processers> map, Resource resource) {
		super();
		this.map = map;
		this.resource = resource;
		this.allocation = new Allocation();
	}

	public List<Processers> getSafeExecuteOrder(List<Processers> list) {
		List<Processers> orderProcess = new ArrayList<Processers>();
		List<Integer> indexList = new ArrayList<Integer>();
		if (list == null) {
			list = new ArrayList<Processers>();
			for (Entry<Long, Processers> entry : map.entrySet()) {
				Processers process = entry.getValue();
				list.add(process);
			}
		}

		for (int i = 0; i < list.size(); i++) {
			if (indexList.contains(i)) {
				continue;
			} else {
				Processers process = list.get(i);
				if (allocation.isSafe(process, resource)) {
					allocation.allocate(process, resource);
					allocation.release(process, resource);
					orderProcess.add(process);
					indexList.add(i);
					i = -1;
				}
			}

		}

		if (list.size() != orderProcess.size()) {
			return null;
		} else {
			return orderProcess;
		}
	}

}
