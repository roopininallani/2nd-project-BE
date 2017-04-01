package com.niit.projectBE.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.projectBE.model.ForumCategory;
import com.niit.projectBE.model.UserBlog;

@EnableTransactionManagement
@Repository("ForumCategoryDao")

public class ForumCategoryDAOImpl implements ForumCategoryDao{
	
	private static final Logger Log = LoggerFactory.getLogger(ForumCategoryDAOImpl.class);
 
	@Autowired
	 private SessionFactory sf;
	 
	 public ForumCategoryDAOImpl(){
		 
	 }

			public ForumCategoryDAOImpl(SessionFactory sessionFactory){
				this.sf= sessionFactory;
			}
			@SuppressWarnings("unchecked")
			@Transactional 
	 
	public List<ForumCategory> getAllForumCategory() {
				List<ForumCategory> allFrmcat = null;
				try {
					Log.debug("get allforumCategory execution is Strarting");
					allFrmcat = sf.getCurrentSession().createQuery("FROM ForumCategory").list();
					if (allFrmcat == null || allFrmcat.isEmpty()) {
						Log.debug("Record not found in ForumCategory  table");
					}
				} catch (HibernateException e) {
					Log.debug("Error is:" + e.getMessage());
					e.printStackTrace();
				}

				return allFrmcat;
			}
@Transactional
	
	public boolean forumCategoryUpdate(ForumCategory forumcategory) {
		try {
			Log.debug("ForumCategoryupdate method execution is Starting");
			sf.getCurrentSession().saveOrUpdate(forumcategory);
			return true;
		} catch (HibernateException ex) {

			Log.debug("Data Save Error:" + ex.getMessage());
			ex.printStackTrace();
			return false;


		}	
		
		
		
	}

	@Transactional
	public ForumCategory getForumCategoryByID(int fcid) {
		try
    	{
    		Log.debug("Method => getForumByID() execution is starting:");
    		return(ForumCategory) sf.getCurrentSession().get(ForumCategory.class, fcid);
    	}
    	catch(HibernateException ex){
    		Log.debug("Data Save Error:"+ex.getMessage());
    		
    		ex.printStackTrace();
    		
		
		return null;
	}
		
	
	}

}
