package ygh.study.pageswap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;

public class Blocks {

	public static final Integer DEFAULT_SIZE = 3;

	private Integer size = null;

	private PageSequence pageSequence;

	public Blocks(int size, PageSequence pageSequence) {
		super();
		this.size = size;
		this.pageSequence = pageSequence;
	}

	public Blocks(PageSequence pageSequence) {
		super();
		this.size = DEFAULT_SIZE;
		this.pageSequence = pageSequence;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public PageSequence getPageSequence() {
		return pageSequence;
	}

	public void setPageSequence(PageSequence pageSequence) {
		this.pageSequence = pageSequence;
	}

	public List<Long> getBlocks() {
		return (new ArrayList<Long>(this.size));
	}

	/*
	 * A method to implement aptimal agorithm
	 */
	public void LRU() {
		List<Page> pageList = this.pageSequence.getPageList();
		List<Long> blocks = getBlocks();
		Map<Long, Long> liveTime = new HashMap<Long, Long>();
		int swapConter = 0;
		for (Page page : pageList) {
			Long id = page.getId();
			if (blocks.contains(id)) {
				flushLiveTime(liveTime);
				liveTime.put(id, 0L);
				continue;
			} else {
				if (!isBlockFull(blocks)) {
					blocks.add(id);
					liveTime.put(id, 0L);
					flushLiveTime(liveTime);
				} else {
					flushLiveTime(liveTime);
					Long pre = getMaxLiveTime(liveTime);
					int index = blocks.indexOf(pre);
					blocks.set(index, id);
					liveTime.put(id, 0L);
					swapConter++;
				}
			}
		}
		printResult(swapConter, pageList.size());

	}

	private Long getMaxLiveTime(Map<Long, Long> liveTime) {
		Long maxTime=-1L;
		Long key=null;
		for (Entry<Long, Long> entry : liveTime.entrySet()) {
			if(entry.getValue()>maxTime){
				maxTime=entry.getValue();
				key=entry.getKey();
			}
		}
		liveTime.remove(key);
		return key;
	}

	public void flushLiveTime(Map<Long, Long> liveTime) {
		for (Entry<Long, Long> entry : liveTime.entrySet()) {
			Long key = entry.getKey();
			Long value = entry.getValue();
			liveTime.put(key, value+1L);
		}
	}

	public void FIFO() {
		List<Page> pageList = this.pageSequence.getPageList();
		List<Long> blocks = getBlocks();
		Queue<Long> queue = new ArrayDeque<Long>();
		int swapConter = 0;
		for (Page page : pageList) {
			Long id = page.getId();
			if (blocks.contains(id)) {
				continue;
			} else {
				if (!isBlockFull(blocks)) {
					blocks.add(id);
					queue.offer(id);
				} else {
					Long pre = queue.poll();
					int index = blocks.indexOf(pre);
					blocks.set(index, id);
					queue.offer(id);
					swapConter++;
				}
			}
		}
		printResult(swapConter, pageList.size());
	}

	public boolean isBlockFull(List<Long> blocks) {
		return (blocks.size() == this.size);
	}

	public double calculateSwapPrecent(int swapConter, int size) {
		return (double) swapConter / size * 100;
	}

	public void printResult(int swapConter, int size) {
		System.out.print("total size is " + size + ", ");
		System.out.print(" swap times is " + swapConter + ", ");
		System.out.println(" swap precent is " + calculateSwapPrecent(swapConter, size) + "%");
	}

}
