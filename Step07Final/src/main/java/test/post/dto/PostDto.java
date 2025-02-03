package test.post.dto;

public class PostDto {
	private long num; //글번호 long타입...int로 하면 글이 20억개를 못 넘어
	private String writer;
	private String title;
	private String content;
	private int viewCount;
	private String createdAt;
	private String updatedAt;
	//페이징 처리할때 필요한 필드
	private int startRowNum;
	private int endRowNum;
	private String condition; //검색조건 writer 또는 title 또는 title+content
	private String keyword;	//검색 키워드
	private long prevNum;	//이전글의 글번호
	private long nextNum;	//다음글의 글번호
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public int getStartRowNum() {
		return startRowNum;
	}
	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}
	public int getEndRowNum() {
		return endRowNum;
	}
	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public long getPrevNum() {
		return prevNum;
	}
	public void setPrevNum(long prevNum) {
		this.prevNum = prevNum;
	}
	public long getNextNum() {
		return nextNum;
	}
	public void setNextNum(long nextNum) {
		this.nextNum = nextNum;
	}
	
	

	
}
