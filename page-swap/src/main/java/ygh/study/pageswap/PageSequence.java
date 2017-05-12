package ygh.study.pageswap;

import java.util.ArrayList;
import java.util.List;

public class PageSequence {

	private List<Page> pageList;
	
	

	public PageSequence(Long[] idArr) {
		super();
		this.pageList = new ArrayList<Page>();
		for(int i=0;i<idArr.length;i++){
			pageList.add(createPageById(idArr[i]));
		}
		
	}

	public List<Page> getPageList() {
		return pageList;
	}

	public void setPageList(List<Page> pageList) {
		this.pageList = pageList;
	}
	
	public Page createPageById(Long id){
		return new Page(id);
	}
	
	
}
