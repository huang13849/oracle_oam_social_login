package com.social.dao.impl;

import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import com.social.dao.LdapPersonDao;
import com.social.domain.Person;


public class LdapPersonDaoImpl implements LdapPersonDao {
	@Autowired
	private LdapTemplate ldapTemplate;
	private static final Logger log = LoggerFactory.getLogger(LdapPersonDaoImpl.class);

	public void create(Person person) {
		Name dn = buildDn(person);
		DirContextAdapter context = new DirContextAdapter(dn);
		mapToContext(person, context, true);
		ldapTemplate.bind(dn, context, null);
	}

	public void update(Person person) {
		Name dn = buildDn(person);
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
		mapToContext(person, context, false);
		ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}

	public void delete(String fullname) {
		ldapTemplate.unbind(buildDn(fullname));
	}

	public List getAllPersonNames() {
		EqualsFilter filter = new EqualsFilter("objectclass", "inetOrgPerson");
		return ldapTemplate.search(DistinguishedName.EMPTY_PATH,
				filter.encode(), new AttributesMapper() {
			public Object mapFromAttributes(Attributes attrs)
					throws NamingException {
					return attrs.get("cn").get();
					}}
		);
	}

	public List findAll() {
		EqualsFilter filter = new EqualsFilter("objectclass", "inetOrgPerson");
		return ldapTemplate.search(DistinguishedName.EMPTY_PATH,filter.encode(), getContextMapper());
		}

	public Person findByPrimaryKey(String cn) {
		Person p = (Person) ldapTemplate.lookup("cn=" + cn, getContextMapper());
		return p;
	}

	
	private ContextMapper getContextMapper() {
		return new PersonContextMapper();
	}

	private DistinguishedName buildDn(Person person) {
		return buildDn(person.getCn());
		}

	private DistinguishedName buildDn(String cn) {
		DistinguishedName dn = new DistinguishedName();
		dn.add("cn", cn);
		if (log.isDebugEnabled())
			log.debug("dn=" + dn.toString());
		return dn;
	}
	
	public boolean isUserCnExist(String cn) {
		try {
			ldapTemplate.lookup("cn=" + cn);
		} catch (NameNotFoundException e) {
			log.info(" isUserCnExist:" + e.getCause());
			return false;
		}
		return true;
	}
	
	public boolean isUserSocialIdExist(String socialId) {
		Boolean JsonReturn = false;
		try {
			AndFilter andFilter = new AndFilter();
			EqualsFilter equalFilter1 = new EqualsFilter("o", socialId);
			EqualsFilter equalFilter2 = new EqualsFilter("objectClass", "person");
			andFilter.and(equalFilter1).and(equalFilter2);			
			List resultList = ldapTemplate.search(DistinguishedName.EMPTY_PATH,andFilter.encode(), getContextMapper());
			if(resultList.size()==1) {JsonReturn = Boolean.TRUE;}
			
		} catch (NameNotFoundException e) {
			log.info(" isUserCnExist:" + e.getCause());
			return false;
		}
		return JsonReturn;
	}

	/**
     * 添加所有属性 密码修改单独维护
     * 
     * @param person
     * @param context
     */
	private void mapToContext(Person person, DirContextAdapter context,boolean isCreate) {
		context.setAttributeValues("objectclass", new String[] { "top","organizationalUnit" });
		context.setAttributeValues("objectclass", new String[] { "organizationalPerson","inetOrgPerson" });
		context.setAttributeValue("cn", person.getCn());
		context.setAttributeValue("uid", person.getUid());
		context.setAttributeValue("sn", person.getSn());
		context.setAttributeValue("o", person.getSid());
		context.setAttributeValue("registeredAddress", person.getAccessToken());
		if (isCreate) {
			context.setAttributeValue("userPassword", person.getUserPassword());
		}
		context.setAttributeValue("userType", person.getUserType());
		context.setAttributeValues("mail", person.getMail());
		context.setAttributeValue("mobile", person.getMobile());
		context.setAttributeValue("sex", person.getSex());
		context.setAttributeValue("age", person.getAge());
	}

	/**
     * 查询不要返回密码信息
     */
	private static class PersonContextMapper implements ContextMapper {

		public Object mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			Person person = new Person();
			// context.getAttributes() 通过属性赋值 TODO 减少代码
			person.setCn(context.getStringAttribute("cn"));
			person.setSn(context.getStringAttribute("sn"));
			person.setUid(context.getStringAttribute("uid"));
			person.setSid(context.getStringAttribute("o"));
			person.setAccessToken(context.getStringAttribute("registeredAddress"));
			// person.setUserPassword(context.getStringAttribute("userPassword"));
			person.setDescription(context.getStringAttribute("description"));
			person.setUserType(context.getStringAttribute("userType"));
			person.setAge(context.getStringAttribute("age"));
			person.setMail(context.getStringAttributes("mail"));
			person.setMobile(context.getStringAttribute("mobile"));
			return person;
		}
	}


	
	private	final static ContextMapper<Person> USER_CONTEXT_MAPPER=new AbstractContextMapper<Person>(){

		@Override
		protected Person doMapFromContext(DirContextOperations context) {
			Person ldapPerson =	new	Person();
			ldapPerson.setCn(context.getStringAttribute("cn"));
			ldapPerson.setSn(context.getStringAttribute("sn"));
			ldapPerson.setSid(context.getStringAttribute("o"));
			ldapPerson.setAccessToken(context.getStringAttribute("registeredAddress"));
			ldapPerson.setDescription(context.getStringAttribute("description"));
			ldapPerson.setMobile(context.getStringAttribute("mobile"));
			return null;
		}};

	
	
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

}
