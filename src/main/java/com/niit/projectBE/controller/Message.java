package com.niit.projectBE.controller;

public class Message { 
	private String message;
	private int id;
	
	public Message(){
		
	}
	
	public Message(int id, String message){
		this.id= id;
		this.message= message;
		
	}
	public String getMessage(){
		return message;
	}

	public int getId() {
		return id;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
