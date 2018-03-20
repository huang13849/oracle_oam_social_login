package com.social.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpMessage;
import org.springframework.ldap.NamingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.social.dao.LdapPersonDao;
import com.social.domain.Person;
import com.social.domain.vo.PersonVO;

@Controller
@RequestMapping(value="/ldap")
public class LdapOUDController {
	
	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {"spring-ldap.xml"});
	
	private LdapPersonDao ldapPersonDao = (LdapPersonDao) ctx.getBean("ldapPersonDao");
	private static Logger log = Logger.getLogger(LdapOUDController.class);

	@RequestMapping(value= "/addPerson")
	@ResponseBody//此处不能省略 否则ajax无法解析返回值
	public Map<String,Object> addUser(PersonVO personVo) throws Exception {		
		Person person = mapPerson(personVo);
		 Map<String,Object> resultMap = new HashMap<String, Object>();  
		 try {
			 ldapPersonDao.create(person);
			 log.info("User "+ personVo.getUsername() +" created successfully!");
			 resultMap.put("result", "success");
		 } catch (NamingException e) {
			// TODO Auto-generated catch block
			 log.info("User creation error "+ e);
	         throw new Exception(e);
		 }
		
		 return resultMap; 
	}

	
	@RequestMapping(value= "/showUser")
	@ResponseBody
	public ModelMap showUser(@RequestParam(value="fullName",required=true) String fullName) {
		Person ldapPerson= ldapPersonDao.findByPrimaryKey(fullName);
		return new ModelMap("person",ldapPerson);
	}
	
	@RequestMapping(value= "/findBySid")
	@ResponseBody
	public ModelMap findBySid(@RequestParam(value="social_id",required=true) String social_id) {
		if(social_id==null || social_id.isEmpty() ) {
			log.info("No sid posted:" + social_id);
			return new ModelMap("person","No_Sid_Posted"); 
		}else {
			List<Person> persons = null;
			persons = ldapPersonDao.findBySocialId(social_id);
			if(persons.size()==1) {
				log.info("User found By sid :" + social_id);
				return new ModelMap("person",(Person)persons.get(0));
			}else {
				log.info("User Not found By sid :" + social_id);
				return new ModelMap("person","Not_Found");
			}
		}
	}
	
	@RequestMapping(value="/updateSocialNumber")
	@ResponseBody
	public Map<String,Object> updateSocialNumber(PersonVO personVo) {
		Map<String,Object> resultMap = new HashMap<String, Object>();  
		Person ldapPerson = null;
		try {
			ldapPerson = ldapPersonDao.findByPrimaryKey(personVo.getUsername_link());
			ldapPerson.setSocialAccessToken(personVo.getAccess_token());
			ldapPerson.setSocialType(personVo.getSocial_type());
			ldapPersonDao.update(ldapPerson);
			log.info("User associate / update successfully !" + ldapPerson.getUsername());
		} catch (Exception e) {
			log.info("Update Fail :" + e);
			resultMap.put("result", "Update Fail");
		}
		resultMap.put("result", "success");
		return resultMap; 
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
	
	@RequestMapping(value="/getAll")
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
/*		mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);*/
		//设置将MAP转换为JSON时候只转换值不等于NULL的
		mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES,false);
		//mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		//设置有属性不能映射成PO时不报错
/*		mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);*/
		return mapper;
		}

	private Person mapPerson(PersonVO personVo) {;
		Person ldapPerson = new Person();
			ldapPerson.setUsername(personVo.getUsername_link());
			ldapPerson.setFirstname(personVo.getFirstname_link());
			ldapPerson.setSurname(personVo.getSurname_link());			
			ldapPerson.setMail(new String[]{personVo.getMail_link()});			
			ldapPerson.setMobile(personVo.getPhoneNum_link());
			System.out.println("######### PW" + personVo.getPassword_link());
			ldapPerson.setUserPassword(personVo.getPassword_link());			
			ldapPerson.setSocialId(personVo.getSocial_id());
			ldapPerson.setSocialAccessToken(personVo.getAccess_token());
			ldapPerson.setSocialType(personVo.getSocial_type());
		
		return ldapPerson;
	}

	
	private Person mockPerson(String name) {;
	Person ldapPerson = new Person();
		ldapPerson.setUsername(name);
		ldapPerson.setFirstname("fn");
		ldapPerson.setSurname("sn");			
		ldapPerson.setMail(new String[]{"email"});			
		ldapPerson.setMobile("12223312");									
		ldapPerson.setUserPassword("Welcome1");			
		ldapPerson.setSocialId("sid");
		ldapPerson.setSocialAccessToken("at");
		ldapPerson.setSocialType("st");
	
	return ldapPerson;
}
}
