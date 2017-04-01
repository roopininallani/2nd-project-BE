package com.niit.projectBE.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.h2.server.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.niit.projectBE.dao.UserProfileDAO;
import com.niit.projectBE.model.UserProfile;

@RestController
public class UserProfileController {
	@Autowired
	UserProfileDAO service;

	private static final Logger Log = LoggerFactory.getLogger(UserProfileController.class);

	@RequestMapping(value = "/adduserprofile/", method = RequestMethod.POST)

	public ResponseEntity<UserProfile> createdUserProfile(@RequestBody UserProfile userprofile) 
	{
		Log.debug("calling => createUserProfile()method");

		if (service.checkUserEmail(userprofile.getUseremail()) == false) {
			Log.debug("error in calling=> createuserProfile() method");
			return new ResponseEntity<UserProfile>(userprofile, HttpStatus.CONFLICT);

		} else {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			Date date = new Date();

			userprofile.setRegdate(dateFormat.format(date));
			userprofile.setLastmodifiedddate(dateFormat.format(date));
			userprofile.setApproved('N');
			userprofile.setUseronline('N');

			switch (userprofile.getUseridentity()) {

			case "Student":
				userprofile.setCurrentrole("Role Student");
				break;

			case "Aluminit":
				userprofile.setCurrentrole("Role Alumini");
				break;

			case "External":
				userprofile.setCurrentrole("Role External");
				break;

			}

			service.saveUserprofile(userprofile);
			Log.debug("update new user type");
			return new ResponseEntity<UserProfile>(userprofile, HttpStatus.OK);

		}
	}

	@RequestMapping(value = "/updateuserprofile/", method = RequestMethod.POST)
	public ResponseEntity<UserProfile> updateUserProfile(@RequestBody UserProfile userProfile) {
		Log.debug("calling=> updateUserProfile()method ");
		service.saveUserprofile(userProfile);
		Log.debug("Update userProfile");
		return new ResponseEntity<UserProfile>(userProfile, HttpStatus.OK);

	}

	@RequestMapping(value = "/allusers", method = RequestMethod.GET)
	public ResponseEntity<List<UserProfile>> listAllUsers() {
		Log.debug("calling => UserProfile()method");
		List<UserProfile> lsts = service.getAllUsers();
		Log.debug("total records:" + lsts.size());
		if (lsts.isEmpty()) {
			return new ResponseEntity<List<UserProfile>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserProfile>>(lsts, HttpStatus.OK);

	}

	@RequestMapping(value = "/getuserbyemail/{useremail:.*}", method = RequestMethod.GET)
	public ResponseEntity<UserProfile> getuserbyemail(@PathVariable("useremail") String useremail) {
		Log.debug("calling=> getuserbyemail()method" + useremail);
		UserProfile usrprofile = service.getUserProfileByEmail(useremail);

		if (usrprofile == null) {
			return new ResponseEntity<UserProfile>(HttpStatus.NO_CONTENT);

		}
		Log.debug("Record:" + usrprofile.getUseremail());
		return new ResponseEntity<UserProfile>(usrprofile, HttpStatus.OK);

	}

	@RequestMapping(value = "/getuserapprove/{useremail}/{yesno}", method = RequestMethod.POST)
	public ResponseEntity<UserProfile> getuserapprove

	(@PathVariable("useremail") String useremail, @PathVariable("yesno") char yesno) {

		Log.debug("calling => getuserbyemail() method");
		boolean flag = service.updateApprove(useremail,

				yesno);
		if (!flag) {
			return new ResponseEntity<UserProfile>

			(HttpStatus.BAD_REQUEST);
		}
		UserProfile usrprofile = service.getUserProfileByEmail

		(useremail);
		return new ResponseEntity<UserProfile>(usrprofile,

				HttpStatus.OK);
	}

	@RequestMapping(value = "/authenticate", method =

	RequestMethod.POST)
	public ResponseEntity<UserProfile> authenticate(@RequestBody

	UserProfile userprofile, HttpSession session) {
		Log.debug("calling => udpateUserProfile() method");
		UserProfile userobj = service.authenticate

		(userprofile.getUseremail(), userprofile.getPassword());
		if (userobj == null) {
			userobj = new UserProfile();
		} else {
			service.updateOnOffLine

			(userprofile.getUseremail(), 'Y');
			session.setAttribute("profile", userobj);
			session.setAttribute("Loggeduser",

					userobj.getUseremail());
		}
		return new ResponseEntity<UserProfile>(userobj,

				HttpStatus.OK);
	}

	@RequestMapping(value = "/Logout", method = RequestMethod.GET)
	public ResponseEntity<UserProfile> Logoutuser(HttpSession session) {
		String Loggeduser = (String) session.getAttribute

		("Loggeduser");
		session.invalidate();
		System.out.println("Logged user :" + Loggeduser);
		service.updateOnOffLine(Loggeduser, 'N');
		return new ResponseEntity<UserProfile>

		(HttpStatus.OK);
	}

}
