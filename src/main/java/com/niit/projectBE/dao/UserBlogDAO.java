package com.niit.projectBE.dao;

import java.util.List;

import com.niit.projectBE.model.UserBlog;

public interface UserBlogDAO {
	
	public List <UserBlog> getAllBlogs();
	
	public boolean saveUserBlog(UserBlog ubobj);
	
	public boolean updateApprove(int blgid, char flag);
	
	public UserBlog getBlogByID(int blgid);
	
	public  boolean getUpdateLike(int blgid);
	
	public boolean getDelete(int blgid);
	

}
