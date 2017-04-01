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

import com.niit.projectBE.model.UserBlog;

@EnableTransactionManagement
@Repository("userBlogDao")


public class UserBlogDAOImpl implements UserBlogDAO{
	private static final Logger Log = LoggerFactory.getLogger(UserBlogDAOImpl.class);
	
	@Autowired
	private SessionFactory sf;
    public UserBlogDAOImpl() {

	}
   public UserBlogDAOImpl(SessionFactory sessionFactory) {
     this.sf = sessionFactory;

	}
    @SuppressWarnings("unchecked")
	@Transactional

	
	public List<UserBlog> getAllBlogs() {
		List<UserBlog> allBlogs = null;
		try{
			
			Log.debug("get allBlogs()execution is starting");
			 allBlogs= sf.getCurrentSession().createQuery("FROM UserBlog").list();
			 if (allBlogs==null||allBlogs.isEmpty()){
				 
				 Log.debug("Record not Found in UserBlog table ");
			 }
			 
		}
		catch(Exception e){
			Log.debug("Fetch Error:"+e.getMessage());
			e.printStackTrace();
		}
		
		return allBlogs;
	}

	@Transactional
	public boolean saveUserBlog(UserBlog ubobj) {
		try {
			Log.debug("saveuserblog execution is strating");
			sf.getCurrentSession().saveOrUpdate(ubobj);
			
			return true;
		} catch (HibernateException ex) {

			Log.debug("Data Save Error:" + ex.getMessage());
			ex.printStackTrace();
		
		return false;
	}
	}


	public boolean updateApprove(int blgid, char flag) {
		try {
			
			Session session = sf.getCurrentSession();
			Query query = session.createQuery("update userblog set approve='"+flag+"'where id="+blgid);
			return query.executeUpdate()==1?true:false;
		} catch (HibernateException ex) {

			Log.debug("Data Save Error:" + ex.getMessage());
			ex.printStackTrace();
		
		return false;
		}
	}

	@Transactional
	public UserBlog getBlogByID(int blgid) {
		try
    	{
    		Log.debug("Method => getBLogByID() execution is starting:");
    		return(UserBlog) sf.getCurrentSession().get(UserBlog.class, blgid);
    	}
    	catch(HibernateException ex){
    		Log.debug("Data fetch Error:"+ex.getMessage());
    		
    		ex.printStackTrace();
    		
		
		return null;
	}
	}


public boolean getDelete(int blgid) {
	try {

		Session session = sf.getCurrentSession();
		Query query = session.createQuery("update userblog set Approve= D where id="+blgid);
		return query.executeUpdate()==1?true:false;
	} catch (HibernateException ex) {

		Log.debug("Data Save Error:" + ex.getMessage());
		ex.printStackTrace();
	
	return false;
	}
		
}


public boolean getUpdateLike(int blgid) {
	try {
		
		Session session = sf.getCurrentSession();
		Query query = session.createQuery("update userblog set likes= likes+ 1 where id="+blgid);
		return query.executeUpdate()==1?true:false;
	} catch (HibernateException ex) {

		Log.debug("Data Save Error:" + ex.getMessage());
		ex.printStackTrace();
	
	return false;
	}
	
	
}
}
