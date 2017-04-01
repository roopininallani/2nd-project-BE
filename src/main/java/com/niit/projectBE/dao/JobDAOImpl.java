package com.niit.projectBE.dao;

import java.io.UncheckedIOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.projectBE.model.Job;
import com.niit.projectBE.model.JobApplication;
import com.niit.projectBE.model.UserProfile;
import com.niit.projectBE.dao.*;

@EnableTransactionManagement
@Repository("jobdao")

public class JobDAOImpl implements JobDAO{
	private static final Logger Log = LoggerFactory.getLogger(JobDAOImpl.class);
	
	@Autowired
	private SessionFactory sf;

	public JobDAOImpl() {

	}

	public JobDAOImpl(SessionFactory sessionFactory) {

		this.sf = sessionFactory;

	}
	@Transactional

   public boolean updatejob(Job job) {
		try {
			
			sf.getCurrentSession().update(job);
		
		} catch (HibernateException ex) {
			ex.printStackTrace();
			return false;

		}
		return true;
		
		
	}
	public List<Job> getAllVacancies() {
		

			List<Job> allJobs = null;
			try {
				Log.debug("get Allvacancies() execution is starting");
				allJobs = sf.getCurrentSession().createQuery("FROM Job where status='V'").list();
				if (allJobs == null || allJobs.isEmpty()) {
					Log.debug("Record not found in JobDAO table");
				}
			} catch (Exception e) {
				Log.debug("Error is:" + e.getMessage());
				e.printStackTrace();
			}

			return allJobs;	
	}

	
	public boolean applyforjob(JobApplication jobapplication) {
		try {
			
			sf.getCurrentSession().save(jobapplication);
			sf.getCurrentSession().flush();
			
		} catch (HibernateException ex) {

			Log.debug("Data Save Error:" + ex.getMessage());
			ex.printStackTrace();
			return false;

		}
		return true;

		
	
	}

	
	public boolean updatejobapplication(JobApplication jobapplication) {
		try {
			
			sf.getCurrentSession().update(jobapplication);
			} catch (HibernateException ex) {
          
			ex.printStackTrace();
			return false;

		}
		return true;
		}

	
	public JobApplication getJobApplication(String useremail, int jobid) {
		JobApplication obj = (JobApplication) sf.getCurrentSession().createQuery("Select *FRom JobApplication where useremail='"+useremail
				+ "and jobid ="+jobid).list();
		
		return obj;
	}

@SuppressWarnings("Unchecked")
@Transactional
	public List<JobApplication> listAllAppliedJobs(String useremail) {
	List<JobApplication> allAppIdJobs = null;
	try {
		Log.debug("get allVacencies execution is starting");
		allAppIdJobs = sf.getCurrentSession().createQuery("FROM UserProfile").list();
		if (allAppIdJobs == null || allAppIdJobs.isEmpty()) {
			Log.debug("Record not found in JobApplication table");
		}
	} catch (Exception e) {
		Log.debug("Error is:" + e.getMessage());
		e.printStackTrace();
	}

	return allAppIdJobs;
}

	@Transactional
	public boolean postjob(Job job) {
      try {
			
			sf.getCurrentSession().save(job);
		
		} catch (HibernateException ex) {
			ex.printStackTrace();
			return false;

		}
		return true;
		
}	
	}


