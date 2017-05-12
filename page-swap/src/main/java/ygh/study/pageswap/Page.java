package ygh.study.pageswap;

/**
 * A class to describe the page
 * @author ygh
 * 2017年5月12日
 */
public class Page {
	
	/**
	 * The id of a page
	 */
	private Long id;	

	public Page() {
		super();
	}

	public Page(Long id) {
		super();
		this.id = id;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Page [id=" + id + "]";
	}
	
	
	
}
