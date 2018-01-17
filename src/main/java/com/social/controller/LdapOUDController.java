package com.social.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.social.dao.LdapPersonDao;
import com.social.domain.Person;

@Controller
@RequestMapping(value="/ldap")
public class LdapOUDController {
	
	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {"spring-ldap.xml"});
	
	private LdapPersonDao ldapPersonDao = (LdapPersonDao) ctx.getBean("ldapPersonDao");
	
	@RequestMapping(value= "/addUser")
	public String addUser() {
		Person ldapPerson = getPerson();		
		ldapPersonDao.create(ldapPerson);
		return "success";
	}

	@RequestMapping(value= "/showUser")
	@ResponseBody
	public ModelMap showUser(@RequestParam(value="fullName",required=true) String fullName) {
		Person ldapPerson= ldapPersonDao.findByPrimaryKey(fullName);
		return new ModelMap("person",ldapPerson);
	}
	
	@RequestMapping(value= "/existSid")
	@ResponseBody
	public Boolean existSid(@RequestParam(value="sid",required=true) String sid) {
		Boolean isExistedSocialId= ldapPersonDao.isUserSocialIdExist(sid);
		return isExistedSocialId;
	}
	
	@RequestMapping(value="/updatePhoneNumber", method = RequestMethod.POST)
	public String updatePhoneNumber(@RequestParam(value="fullName",required=true) String fullName) {
		Person ldapPerson=ldapPersonDao.findByPrimaryKey(fullName);
		ldapPerson.setMobile(StringUtils.join(new String[] {ldapPerson.getMobile(),"0"}));
		
		ldapPersonDao.update(ldapPerson);
		return "success";
	}
	
	@RequestMapping(value="/removePerson")
	public String removePerson(@RequestParam(value="fullName",required=true) String fullName) {
		ldapPersonDao.delete(fullName);
		return "success";
	}
	
	@RequestMapping(value="/getAll", method = RequestMethod.POST)
	@ResponseBody
	public String getAll() throws IOException{
		ObjectMapper mapper = getDefaultObjectMapper();
		List<Person> person = ldapPersonDao.findAll();
		
		return mapper.writeValueAsString(person);
	}
	
	@RequestMapping("/getAllName")
	@ResponseBody
	public String getAllName() throws IOException{
		ObjectMapper mapper = getDefaultObjectMapper();
		List<Person> person = ldapPersonDao.getAllPersonNames();
		return mapper.writeValueAsString(person);
	}
	
	private ObjectMapper getDefaultObjectMapper() {
		ObjectMapper mapper=new ObjectMapper();
		mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
		//设置将MAP转换为JSON时候只转换值不等于NULL的
		mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES,false);
		//mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		//设置有属性不能映射成PO时不报错
		mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		return mapper;
		}

	private Person getPerson() {
		Person ldapPerson= new Person();
		ldapPerson.setCn("Panda Huang");
		ldapPerson.setSn("Panda");
		ldapPerson.setUid("littlePanda");
		ldapPerson.setSid("123456789");
		ldapPerson.setAccessToken("XNKASDJAHHDASDIAPSDA");
		ldapPerson.setDescription("Panda Huang Test from SpringMVC.");
		
		return ldapPerson;
		}
	
}
