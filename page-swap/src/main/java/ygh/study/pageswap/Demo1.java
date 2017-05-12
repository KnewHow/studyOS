package ygh.study.pageswap;

/**
 * A class to test 
 * @author ygh
 * 2017年5月12日
 */
public class Demo1 {
	public static void main(String[] args) {
		Long[] pageIds={7L,0L,1L,2L,0L,3L,0L,4L,2L,3L,0L,3L,2L,1L,2L,0L,1L,7L,0L,1L};
		PageSequence pageSequence = new PageSequence(pageIds);
		System.out.println(pageSequence.getPageList().size());
		Blocks blocks = new Blocks(pageSequence);
		blocks.LRU();
		blocks.FIFO();
	}
	
}
