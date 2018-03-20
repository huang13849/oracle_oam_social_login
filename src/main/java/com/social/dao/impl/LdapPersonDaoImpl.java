package com.social.dao.impl;

import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
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
	private static final String LDAP_ATTR_SOCIAL_TYPE = "o";
	private static final String LDAP_ATTR_ACCESS_TOKEN = "registeredAddress";
	private static final String LDAP_ATTR_SURNAME = "sn";
	private static final String LDAP_ATTR_COMMON_NAME = "cn";
	private static final String LDAP_ATTR_USERNAME = "uid";
	private static final String LDAP_ATTR_SOCIAL_ID = "st";
	private static final String USER_PASSWORD = "userPassword";

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
					return attrs.get(LDAP_ATTR_USERNAME).get();
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
		return buildDn(person.getUsername());
		}

	private DistinguishedName buildDn(String cn) {
		DistinguishedName dn = new DistinguishedName();
		dn.add(LDAP_ATTR_USERNAME, cn);
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
	
	public List findBySocialId(String socialId) {

			AndFilter andFilter = new AndFilter();
			EqualsFilter equalFilter1 = new EqualsFilter(LDAP_ATTR_SOCIAL_ID, socialId);
			EqualsFilter equalFilter2 = new EqualsFilter("objectClass", "person");
			andFilter.and(equalFilter1).and(equalFilter2);			
			List resultList = ldapTemplate.search(DistinguishedName.EMPTY_PATH,andFilter.encode(), getContextMapper());
			
		return resultList;
	}

	/**
     * 添加所有属性 密码修改单独维护
     * 
     * @param person
     * @param context
     */
	private void mapToContext(Person person, DirContextAdapter context,boolean isCreate) {
		if (isCreate) {
			context.setAttributeValues("objectclass", new String[] { "top","organizationalUnit" });
			context.setAttributeValues("objectclass", new String[] { "organizationalPerson","inetOrgPerson" });
			context.setAttributeValue(LDAP_ATTR_USERNAME, person.getUsername());
			context.setAttributeValue(LDAP_ATTR_COMMON_NAME, person.getFirstname() + " " + person.getSurname());
			context.setAttributeValue(LDAP_ATTR_SURNAME, person.getSurname());
			context.setAttributeValue(LDAP_ATTR_SOCIAL_ID, person.getSocialId());
			context.setAttributeValue(USER_PASSWORD, person.getUserPassword());
			context.setAttributeValues("mail", person.getMail());
			context.setAttributeValue("mobile", person.getMobile());
			context.setAttributeValue("sex", person.getSex());
			context.setAttributeValue("age", person.getAge());
		}
		context.setAttributeValue(LDAP_ATTR_ACCESS_TOKEN, person.getSocialAccessToken());
		context.setAttributeValue(LDAP_ATTR_SOCIAL_TYPE, person.getSocialType());
	}

	/**
     * 查询不返回密码信息
     */
	private static class PersonContextMapper implements ContextMapper {


		public Object mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			Person person = new Person();
			// context.getAttributes() 通过属性赋值 TODO 减少代码
			person.setUsername(context.getStringAttribute(LDAP_ATTR_USERNAME));
			person.setSurname(context.getStringAttribute(LDAP_ATTR_SURNAME));
			person.setFirstname(context.getStringAttribute(LDAP_ATTR_COMMON_NAME));
			person.setSocialId(context.getStringAttribute(LDAP_ATTR_SOCIAL_ID));
			person.setSocialAccessToken(context.getStringAttribute(LDAP_ATTR_ACCESS_TOKEN));
			person.setSocialType(context.getStringAttribute(LDAP_ATTR_SOCIAL_TYPE));
//			person.setAge(context.getStringAttribute("age"));
			person.setMail(context.getStringAttributes("mail"));
			person.setMobile(context.getStringAttribute("mobile"));
			return person;
		}
	}


	
/*	private	final static ContextMapper<Person> USER_CONTEXT_MAPPER=new AbstractContextMapper<Person>(){

		@Override
		protected Person doMapFromContext(DirContextOperations context) {
			Person ldapPerson =	new	Person();
			ldapPerson.setUsername(context.getStringAttribute(LDAP_ATTR_USERNAME));
			ldapPerson.setSurname(context.getStringAttribute(LDAP_ATTR_SURNAME));
			ldapPerson.setSocialId(context.getStringAttribute(LDAP_ATTR_SOCIAL_ID));
			ldapPerson.setSocialAccessToken(context.getStringAttribute(LDAP_ATTR_ACCESS_TOKEN));
			ldapPerson.setDescription(context.getStringAttribute("description"));
			ldapPerson.setMobile(context.getStringAttribute("mobile"));
			return null;
		}};*/

	
	
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

}
