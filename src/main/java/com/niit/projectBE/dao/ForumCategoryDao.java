package com.niit.projectBE.dao;

import java.util.List;

import com.niit.projectBE.model.ForumCategory;

public interface ForumCategoryDao {
public List<ForumCategory> getAllForumCategory();
	
	public boolean forumCategoryUpdate(ForumCategory forumcategory);
		
	public ForumCategory getForumCategoryByID(int fcid);


}
