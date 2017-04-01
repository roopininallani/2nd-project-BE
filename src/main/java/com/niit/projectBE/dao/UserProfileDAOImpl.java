package com.niit.projectBE.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.projectBE.model.UserProfile;

@EnableTransactionManagement
@Repository("userProfileDao")

public class UserProfileDAOImpl implements UserProfileDAO {

	private static final Logger Log = LoggerFactory.getLogger(UserProfileDAOImpl.class);
	private SessionFactory sf;

	public UserProfileDAOImpl() {

	}

	public UserProfileDAOImpl(SessionFactory sessionFactory) {

		this.sf = sessionFactory;

	}

	@SuppressWarnings("unchecked")
	@Transactional

	public List<UserProfile> getAllUsers() {

		List<UserProfile> allUsers = null;
		try {
			Log.debug("get allusers()in userprofiledaoimpl is running");
			allUsers = sf.getCurrentSession().createQuery("FROM UserProfile").list();
			if (allUsers == null || allUsers.isEmpty()) {
				Log.debug("Record not found in UserProfile table");
			}
		} catch (Exception e) {
			Log.debug("Error is:" + e.getMessage());
			e.printStackTrace();
		}

		return allUsers;
	}

	@Transactional
	public boolean saveUserprofile(UserProfile userprofile) {
		try {
			Log.debug("saveUserProfileDAoImpl is running");
			sf.getCurrentSession().saveOrUpdate(userprofile);
			sf.getCurrentSession().flush();
			return true;
		} catch (HibernateException ex) {

			Log.debug("Data Save Error:" + ex.getMessage());
			ex.printStackTrace();
			return false;

		}

	}

	@Transactional
	public boolean updateApprove(String useremail, char flag) {
		try {
			Session session = sf.getCurrentSession();
			String message = "";
			if (flag == 'Y') {
				message = "You profile has been accepted";

			} else {
				message = "you profile has bben rejected";
			}

			Query query = session.createQuery("Update UserProfile set Approved= '" + flag + "',reason='" + message
					+ "' where useremail like'" + useremail + "'%'");

			return query.executeUpdate() == 1 ? true : false;
		} catch (HibernateException ex) {
			Log.debug("Data update Error:" + ex.getMessage());

			ex.printStackTrace();
			return false;
		}
	}
    @Transactional
	public UserProfile getUserProfileByEmail(String useremail) {
    	try
    	{
    		Log.debug("Method => getBLogByID() execution is starting:"+useremail);
    		return(UserProfile) sf.getCurrentSession().get(UserProfile.class, useremail);
    	}
    	catch(HibernateException ex){
    		Log.debug("Data fetch Error:"+ex.getMessage());
    		
    		ex.printStackTrace();
    		return null;
    	
    	}

	
	}
     @SuppressWarnings("unchecked")
     @Transactional
	public boolean checkUserEmail(String useremail) {
    	 String SQL="FROM Userprofile where upper(useremail)='"+useremail.toUpperCase()+"'";
        Log.debug("SQL:"+SQL);
        List<UserProfile>obj= sf.getCurrentSession().createQuery(SQL).list();
        
		return obj.isEmpty()?true:false;
	}
    @SuppressWarnings("unchecked")
    @Transactional
    
	public UserProfile authenticate(String useremail, String password) {
    	Query query = sf.getCurrentSession().createQuery("from UserProfile where useremail='"+useremail+"'and password=''");
    	List<UserProfile> userProfile= query.list();
    	if(userProfile!=null&&!userProfile.isEmpty()){
    		updateOnOffLine(useremail,'Y');
    		return userProfile.get(0);
    	}

		return null;
	}
    @Transactional
	public boolean updateOnOffLine(String useremail, char onOff) {
    	try{
    		Session session = sf.getCurrentSession();
    				Query query= session.createQuery("update UserProfile set useronline = '" + onOff + "'where useremail like'"+useremail+"%");
    						return query.executeUpdate()==1?true:false;
    				
    	}
    	catch(HibernateException ex){
    		Log.debug("Data update Error:"+ex.getMessage());
    		
    		ex.printStackTrace();
    		return false;
    	}

		
	}

}
