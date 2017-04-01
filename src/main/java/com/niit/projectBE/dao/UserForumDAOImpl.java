package com.niit.projectBE.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.projectBE.model.UserForum;
import com.niit.projectBE.model.UserForumComments;

@EnableTransactionManagement
@Repository("userForumDao")

public class UserForumDAOImpl implements UserForumDAO{
	private static final Logger Log = LoggerFactory.getLogger(UserForumDAOImpl.class);
	
	@Autowired
	private SessionFactory sf;


	public UserForumDAOImpl() {

	}

	public UserForumDAOImpl(SessionFactory sessionFactory) {

		this.sf = sessionFactory;

	}

	@SuppressWarnings("unchecked")
	@Transactional


	
	public List<UserForum> listAllForums() {
		List<UserForum> allForums = null;
		try {
			Log.debug("list allforums()in userforumdaoimpl is running");
			allForums = sf.getCurrentSession().createQuery("FROM UserForum").list();
			if (allForums == null || allForums.isEmpty()) {
				Log.debug("Record not found in UserForum table");
			}
		} catch (Exception e) {
			Log.debug("Error is:" + e.getMessage());
			e.printStackTrace();
		}

		
		return allForums;
	}

	
	
		@Transactional
		public boolean addForum(UserForum userforum) {
			try {
				Log.debug("addforum () method is starting");
				sf.getCurrentSession().saveOrUpdate(userforum);
				sf.getCurrentSession().flush();
				return true;
			} catch (HibernateException ex) {

				Log.debug("Data Save Error:" + ex.getMessage());
				ex.printStackTrace();
				return false;

			}
		
	
	}

	
		

		
	


	public boolean getUpdateLike(int forumid) {
		try {
			Session session = sf.getCurrentSession();
			Query query = session.createQuery("Update UserForum set Likes+ 1 where id= '"  +forumid);
					

			return query.executeUpdate() == 1 ? true : false;
		} catch (HibernateException ex) {
			Log.debug("Data update Error:" + ex.getMessage());

			ex.printStackTrace();
			return false;

		
		}	
		
		
	}

	@Transactional
	
	public boolean updateApprove(int forumid, char flag) {
		try {
			Session session = sf.getCurrentSession();
			Query query = session.createQuery("Update UserForum set Approve= '" + flag + "'Where id='" +forumid);
					

			return query.executeUpdate() == 1 ? true : false;
		} catch (HibernateException ex) {
			Log.debug("Data update Error:" + ex.getMessage());

			ex.printStackTrace();
			return false;

		
		}	
	}

	
	public List<UserForumComments> listAllForumComments(int forumid) {
		 List<UserForumComments> allForumscmts = null;
	    	try
	    	{
	    		Log.debug("Method =>listallForumComments() execution is starting:");
	    		allForumscmts = sf.getCurrentSession().createQuery("From userForumcomments where forumid="+forumid).list();
	    		
	    		if (allForumscmts==null||allForumscmts.isEmpty())
	    		{
	    			Log.debug("Record not found in UserForumcomments table");
	    		}
	    	
	    	}
	    	catch(HibernateException ex){
	    		Log.debug("Data fetch Error:"+ex.getMessage());
	    		
	    		ex.printStackTrace();
	    	}
	    		return allForumscmts;
	    
	    	}
		
	
		
	public boolean addForumComment(UserForumComments userforumcmt){
	try {
		Log.debug("updateUserForumComment is strating");
		sf.getCurrentSession().update(userforumcmt);
		return true;
		
	} catch (HibernateException ex) {

		Log.debug("Data Save Error:" + ex.getMessage());
		ex.printStackTrace();
		return false;

	}

	
		
		
	}

	
	public void updateForumCommentsCount(int forumid) {
		
			try {
				Session session = sf.getCurrentSession();
				Query query = session.createQuery("update UserForum set countcmts= countcmts + 1 where id ="+forumid);
				query.executeUpdate();
				
			} catch (HibernateException ex) {

				Log.debug("Data Update Error:" + ex.getMessage());
				ex.printStackTrace();
				

			}
		
		
	}

	@Override
	public List<UserForum> getForumByID(int forumid) {
		 List<UserForum> allForums = null;
	    	try
	    	{
	    		Log.debug("Method => getForumByID() execution is starting:");
	    		allForums = sf.getCurrentSession().createQuery("From userForum where forumid="+forumid).list();
	    		
	    		if (allForums==null||allForums.isEmpty())
	    		{
	    			Log.debug("Record not found in UserForum table");
	    		}
	    	
	    	}
	    	catch(HibernateException ex){
	    		Log.debug("Data fetch Error:"+ex.getMessage());
	    		
	    		ex.printStackTrace();
	    	}
	    		return allForums;
	    
	    	}

	
	public boolean deleteForum(int forumid) {
		try {
			Session session = sf.getCurrentSession();
			Query query = session.createQuery("update UserForum set Approve= 0 where id ="+forumid);
			return query.executeUpdate()==1? true: false;
			
		} catch (HibernateException ex) {

			Log.debug("Data Update Error:" + ex.getMessage());
			ex.printStackTrace();
			

		
		return false;
	}
		
	
	}

}
	
