package cn.dbsec.app.pojo;


public class UserMsg {
	private String id;
	private String userId;
	private String content = "";
	private String time = "";
	private int userReaded = 0; //用户是否已读  0=消息未读，1=已读
	private int adminReaded = 0; //管理员是否已读 0=消息未读，1=已读
	private String replyUserName = "";//回复者，如果有的话，表示客服回复
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getUserReaded() {
		return userReaded;
	}
	public void setUserReaded(int userReaded) {
		this.userReaded = userReaded;
	}
	public String getReplyUserName() {
		return replyUserName;
	}
	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}
	public int getAdminReaded() {
		return adminReaded;
	}
	public void setAdminReaded(int adminReaded) {
		this.adminReaded = adminReaded;
	}
		
}
