package com.niit.projectBE.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.projectBE.model.Friends;

@EnableTransactionManagement
@Repository("frienddao")



public class FriendDAOImpl implements FriendDAO{
	private static final Logger Log = LoggerFactory.getLogger(FriendDAOImpl.class);
	
	boolean update= false;
	@Autowired
	private SessionFactory sf;
   public FriendDAOImpl() {
	   
   }

	public FriendDAOImpl(SessionFactory sessionFactory) {

		this.sf = sessionFactory;

	}
	
	public boolean sendFriendRequest(Friends friend) {
		try {
			Log.debug("sendfriendrequest method  execution is strating");
			sf.getCurrentSession().saveOrUpdate(friend);
			
			return true;
		} catch (HibernateException ex) {

			Log.debug("Data Save Error:" + ex.getMessage());
			ex.printStackTrace();
		return false;
	}
	}

	
	public boolean confirmRequest(String fromUser, String toUser) {
		return getStatusUpdate(toUser, fromUser,'Y');
	}
		
	
	public boolean checkAlreadyFriendStatus(String fromUser, String toUser) {
		Friends frnds = (Friends) sf.getCurrentSession().createQuery("From Friends where reuser = '"+ fromUser + "'and touser = '" +toUser + "'").list();
		
	update = false;
	if(frnds!=null){
		update = frnds.getFollow()=='Y' ? true : false;
	}
		return false;
	}

	@SuppressWarnings("unchecked")
	
	public List<Friends> viewFriends(String userName) {
		
		 
		return sf.getCurrentSession().createSQLQuery("FROM Friends where touser = '" + userName + "' and status = 'Y' and"
				+"follow='Y'").list();
	}

	
	public List<Friends> viewRequestedUsers(String userName) {
		
		
		return sf.getCurrentSession().createSQLQuery("FROM Friends where touser = '" + userName + "'").list();
				
	}

	
	public boolean updateUnFollow(String fromUser, String toUser) {
		return getUnFollowUpdate(toUser, fromUser, 'Y');
		
	}
	
  private boolean getStatusUpdate(String toUser, String fromUser, char flag){
	  try{
		  Log.debug("confirmrequest() execution is starting");
		  String SQL = "update FRIENDS set status='" + flag + "'where requser='" + toUser + "' and toUser ='"+ fromUser+"'";
		  Session session = sf.getCurrentSession();
		  session.createQuery(SQL).executeUpdate();
		  update = true;
		  
	  }
	  catch (HibernateException ex) {

			Log.debug("Data Save Error:" + ex.getMessage());
			ex.printStackTrace();
		return false;
	}
	  return update;
  }
  private boolean getUnFollowUpdate(String toUser, String fromUser, char flag){
	  try{
		  Log.debug("confirmrequest() execution is starting");
		  String SQL = "update FRIENDS set status='" + flag+ "'where requser='" + toUser + "' and toUser ='"+ fromUser+"'";
		  Session session = sf.getCurrentSession();
		  session.createQuery(SQL).executeUpdate();
		  update = true;
		  
	  }
	  catch (HibernateException ex) {

			Log.debug("Data Save Error:" + ex.getMessage());
			ex.printStackTrace();
		return false;
	}
	  return update;
  }
  
	
}
