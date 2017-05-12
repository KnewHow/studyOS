package ygh.study.os;

import java.util.Map;
import java.util.Map.Entry;

/**
 * A class to describe the resource
 * 
 * @author ygh 2017年5月11日
 */
public class Resource {

	/**
	 * The resource quantity in system The map key is the id of resource,value
	 * is integer number to stand for the resource's quantity
	 */
	private Map<String, Integer> avaiable;

	public Resource(Map<String, Integer> avaiable) {
		super();
		this.avaiable = avaiable;
	}

	public Map<String, Integer> getAvaiable() {
		return avaiable;
	}

	public void setAvaiable(Map<String, Integer> avaiable) {
		this.avaiable = avaiable;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Current System resouce is ");
		for(Entry<String, Integer> entry:avaiable.entrySet()){
			sb.append(entry.getKey()).append(":").append(entry.getValue()).append("; ");
		}
		return sb.toString();
	}

}
