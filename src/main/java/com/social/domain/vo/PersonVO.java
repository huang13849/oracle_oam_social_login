package com.social.domain.vo;

import com.social.domain.Person;

public class PersonVO extends Person {
	
	private String surname_link;
	private String firstname_link;
	private String username_link;
	private String password_link;
	private String phoneNum_link;
	private String mail_link;
	private String request_id;
	private String social_id;
	private String social_access_token;
	private String social_type;
	public String getSurname_link() {
		return surname_link;
	}
	public void setSurname_link(String surname_link) {
		this.surname_link = surname_link;
	}
	public String getFirstname_link() {
		return firstname_link;
	}
	public void setFirstname_link(String firstname_link) {
		this.firstname_link = firstname_link;
	}
	public String getUsername_link() {
		return username_link;
	}
	public void setUsername_link(String username_link) {
		this.username_link = username_link;
	}
	public String getPassword_link() {
		return password_link;
	}
	public void setPassword_link(String password_link) {
		this.password_link = password_link;
	}
	public String getPhoneNum_link() {
		return phoneNum_link;
	}
	public void setPhoneNum_link(String phoneNum_link) {
		this.phoneNum_link = phoneNum_link;
	}
	public String getMail_link() {
		return mail_link;
	}
	public void setMail_link(String mail_link) {
		this.mail_link = mail_link;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public String getSocial_id() {
		return social_id;
	}
	public void setSocial_id(String social_id) {
		this.social_id = social_id;
	}
	public String getAccess_token() {
		return social_access_token;
	}
	public void setAccess_token(String access_token) {
		this.social_access_token = access_token;
	}
	public String getSocial_type() {
		return social_type;
	}
	public void setSocial_type(String social_type) {
		this.social_type = social_type;
	}
	
	
}
 