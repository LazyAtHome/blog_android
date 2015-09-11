package com.ldj.myblog.model;

public class Blog {
	String id;
	String title;
	String content;
	String userId;
	String viewCount;
	String version;
	String deletedFlag;
	String createdDate;
	String updatedDate;
	String createdBy;
	String updatedBy;
	String commentCount;
	
	
	
	public Blog() {
		super();
	}
	
	

	public Blog(String content, String viewCount, String createdDate,
			String createdBy, String commentCount) {
		super();
		this.content = content;
		this.viewCount = viewCount;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.commentCount = commentCount;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", content=" + content
				+ ", userId=" + userId + ", viewCount=" + viewCount
				+ ", version=" + version + ", deletedFlag=" + deletedFlag
				+ ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + ", commentCount=" + commentCount + ", getId()="
				+ getId() + ", getTitle()=" + getTitle() + ", getContent()="
				+ getContent() + ", getUserId()=" + getUserId()
				+ ", getViewCount()=" + getViewCount() + ", getVersion()="
				+ getVersion() + ", getDeletedFlag()=" + getDeletedFlag()
				+ ", getCreatedDate()=" + getCreatedDate()
				+ ", getUpdatedDate()=" + getUpdatedDate()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getUpdatedBy()="
				+ getUpdatedBy() + ", getCommentCount()=" + getCommentCount()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	

}
