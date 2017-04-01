package com.niit.projectBE.controller;

import java.util.Date;

public class OutPutMessage extends Message{
	
	private Date time;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		time = time;
	}
	
	public OutPutMessage(Message original, Date time)
	{
		super(original.getId(),original.getMessage());
		this.time = time;
	}

}
