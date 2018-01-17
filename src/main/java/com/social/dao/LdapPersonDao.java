package com.social.dao;

import java.util.List;

import org.springframework.ldap.NamingException;

import com.social.domain.Person;



public interface LdapPersonDao {
	
	void create(Person person) throws NamingException;

	void update(Person person) throws NamingException;

	void delete(String fullname) throws NamingException;

	List getAllPersonNames() throws NamingException;

	List findAll() throws NamingException;

	boolean isUserCnExist(String cn);
	
	boolean isUserSocialIdExist(String socialId);
	
	Person findByPrimaryKey(String fullname); 



}