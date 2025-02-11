package test.post.dto;

public class CommentDto {
	private long num;
	private String writer;
	private String content;
	private String targetWriter;
	private long postNum;
	private long parentNum;
	private String deleted;
	private String createdAt;
	private String profileImage; 
	//페이징 처리를 위한 필드
	private int startRowNum;
	private int endRowNum;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTargetWriter() {
		return targetWriter;
	}
	public void setTargetWriter(String targetWriter) {
		this.targetWriter = targetWriter;
	}
	public long getPostNum() {
		return postNum;
	}
	public void setPostNum(long postNum) {
		this.postNum = postNum;
	}
	public long getParentNum() {
		return parentNum;
	}
	public void setParentNum(long parentNum) {
		this.parentNum = parentNum;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
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

	

	
}