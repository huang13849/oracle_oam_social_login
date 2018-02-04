
package com.social.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.social.util.AppConfig;
import com.social.util.HttpClientUtils;

import net.sf.json.JSONObject;
import weibo4j.Oauth;

@Controller
@RequestMapping("/sso")
public class SsoController extends BaseController{
	private static Logger log = Logger.getLogger(SsoController.class);

	
	@RequestMapping("/login")
	public String login() {
		return "sso/login";
	}
	
	@RequestMapping("/alipayAuthN")
	public String alipayAuthN(HttpSession session) throws Exception {
		String social_id = new String();
		String display_name=new String();
		String access_token = new String();
		String auth_code = getRequest().getParameter("auth_code");			
		AlipayClient alipayClient = new DefaultAlipayClient (AppConfig.getProperty("alipay_request_access_token_url"),AppConfig.getProperty("alipay_client_id"),AppConfig.getProperty("alipay_app_private"),"json","utf-8",AppConfig.getProperty("alipay_public_key"),"RSA2" );
		AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
		request.setCode(auth_code);
		request.setGrantType("authorization_code");

		if(auth_code!=null && !auth_code.trim().isEmpty()) {

			try {
				AlipaySystemOauthTokenResponse oauthTokenResponse= alipayClient.execute(request);
				social_id = oauthTokenResponse.getAlipayUserId();
				access_token=oauthTokenResponse.getAccessToken();
				String social_source = oauthTokenResponse.getMsg();
				System.out.println("access_token #### " + access_token);
				System.out.println("Refresh Token #### " + oauthTokenResponse.getRefreshToken());				

				AlipayUserInfoShareRequest requestUser = new AlipayUserInfoShareRequest();

				AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(requestUser, oauthTokenResponse.getAccessToken());
				System.out.println(userinfoShareResponse.getBody());
				System.out.println("alipay UserId:" + userinfoShareResponse.getUserId());//用户支付宝ID
				System.out.println("alipay UserType:" + userinfoShareResponse.getUserType() );//用户类型
				System.out.println("alipay UserStatus:" + userinfoShareResponse.getUserStatus() );//用户账户动态
				System.out.println("Email:" + userinfoShareResponse.getEmail() );//用户Email地址
				System.out.println("IsCertified:" + userinfoShareResponse.getIsCertified() );//用户是否进行身份认证
				System.out.println("IsStudentCertified:" + userinfoShareResponse.getIsStudentCertified() );//用户
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Alipay API request error :" + e);
			}
	             
		}		
		 session.setAttribute("display_name",display_name);  
		 session.setAttribute("social_id",social_id);  
		 session.setAttribute("access_token",access_token);  
		 session.setAttribute("social_type","alipay");  

   		return "sso/socialAuthN";
		
	}
	

	@RequestMapping("/wechatAuthN")
	public String weChatAuthN(HttpSession session) throws Exception {
		String auth_code = getRequest().getParameter("code");
		String display_name=new String();
		String access_token=new String();
		String social_id=new String();
		if(auth_code!=null && !auth_code.trim().isEmpty()) {
				try {
					String oauthUrl = MessageFormat.format(AppConfig.getProperty("wechat_request_oauth_url"), AppConfig.getProperty("wechat_client_id"), AppConfig.getProperty("wechat_client_sercret"), auth_code);
					String response = HttpClientUtils.sendRequest(oauthUrl);
					JSONObject result=  JSONObject.fromObject(response);
					String openId = result.getString("openid");
					access_token=result.getString("access_token");
					social_id= openId;
					access_token=result.getString("access_token");
					
					String getUserInfoUrl = MessageFormat.format(AppConfig.getProperty("wechat_request_userInfo_url"), access_token, openId);
					String userInfoResponse = HttpClientUtils.sendRequest(getUserInfoUrl);
					JSONObject userInfoesult= JSONObject.fromObject(JSONObject.fromObject(response));
					System.out.println(" Wechat userInfoesult ============ " + userInfoesult.toString());
					display_name=userInfoesult.getString("nickname");
					session.setAttribute("headimgurl", userInfoesult.getString("headimgurl"));
				} catch (Exception e) {
					log.error("Wechat API request error :" + e);
				}		    
			}
		 	session.setAttribute("display_name",display_name);  
			session.setAttribute("social_id",social_id);  
			session.setAttribute("access_token",access_token);  
			session.setAttribute("social_type","wechat"); 
	
		return "sso/socialAuthN";

	}
	
	@RequestMapping("/weiboAuthN")
	public String weiboAuthN(HttpSession session) throws Exception {
		String display_name=new String();
		String access_token=new String();
		String social_id=new String();
		String auth_code = getRequest().getParameter("code");
		String uid =new String();
		
		if(auth_code!=null && !auth_code.trim().isEmpty()) {
			try {
				String oauthUrl = MessageFormat.format(AppConfig.getProperty("weibo_request_oauth_url"), AppConfig.getProperty("weibo_client_id"), AppConfig.getProperty("weibo_client_sercret"),AppConfig.getProperty("weibo_redirect_uri"),auth_code);
				String oauthResponse = HttpClientUtils.sendRequest(oauthUrl,"POST");
				JSONObject oauthResult=  JSONObject.fromObject(oauthResponse);
				access_token = oauthResult.getString("access_token");
				uid = oauthResult.getString("uid");

				String userInfoUrl = MessageFormat.format(AppConfig.getProperty("weibo_request_userInfo_url"), access_token, uid);
				String userInfoResponse = HttpClientUtils.sendRequest(userInfoUrl);
				JSONObject result=  JSONObject.fromObject(userInfoResponse);
				log.info("WeiBo userInfo ========== " + result);
			} catch (Exception e) {
				log.error("Webo API request error :" + e);
			}
		}
		session.setAttribute("display_name",display_name);  
		session.setAttribute("social_id",uid);  
		session.setAttribute("access_token",access_token);  
		session.setAttribute("social_type","weibo"); 
		
		return "sso/socialAuthN";
	}
	
	@RequestMapping("/qqAuthN")
	public String qqAuthN(HttpSession session) throws Exception {

		String auth_code = getRequest().getParameter("code");
		String access_token=new String();
		String social_id=new String();
		String display_name=new String();
		if(auth_code!=null && !auth_code.trim().isEmpty()) {
				try {
					String oauthUrl = MessageFormat.format(AppConfig.getProperty("qq_request_oauth_url"), AppConfig.getProperty("qq_client_id"), AppConfig.getProperty("qq_client_sercret"), auth_code,AppConfig.getProperty("qq_redirect_uri"));
					String oauthResponse = HttpClientUtils.sendRequest(oauthUrl);
					access_token = patternParse("accessToken", oauthResponse);
				
					String getOpenIdUrl = MessageFormat.format(AppConfig.getProperty("qq_request_openId"), access_token);
					String openIdResponse = HttpClientUtils.sendRequest(getOpenIdUrl);
				
					String openId = patternParse("openId", openIdResponse);
					social_id = openId;
					
					String getUserInfoUrl = MessageFormat.format(AppConfig.getProperty("qq_request_userInfo"), access_token, AppConfig.getProperty("qq_client_id"),openId);	
					String userInfoResponse = HttpClientUtils.sendRequest(getUserInfoUrl);
					JSONObject result=  JSONObject.fromObject(userInfoResponse);
					display_name = result.getString("nickname");
					
					System.out.println(" qq userInfoesult ============ " + userInfoResponse.toString());
					
				} catch (Exception e) {
					log.error("Wechat API request error :" + e);
				}		    
		} 
			session.setAttribute("display_name",display_name);  
			session.setAttribute("social_id",social_id);  
			session.setAttribute("access_token",access_token);  
			session.setAttribute("social_type","qq"); 
	
		
		return "sso/socialAuthN";

	}

	private String patternParse(String type, String response) throws Exception {
		System.out.println(response);
		String returnValue="";
		Pattern p = Pattern.compile("[A-Za-z0-9_]+");
		Matcher m = p.matcher(response);
		String[] authToken_pattern = new String[7];
		int count = 0;
		while(m.find()) {
			authToken_pattern[count] = m.group();
			System.out.println(count + " "+ authToken_pattern[count]);
			count++;
		}
		if(count>0) {
			if(type.equals("accessToken")){
				returnValue= authToken_pattern[1];										
			}else if(type.equals("openId")) {
				returnValue= authToken_pattern[4];
			}
		}
		
		return returnValue;
		
	}
	
	@RequestMapping("/alipay")
	public void alipay() throws IOException {
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().sendRedirect(MessageFormat.format(AppConfig.getProperty("alipay_request_login_url"),AppConfig.getProperty("alipay_client_id"),AppConfig.getProperty("alipay_request_scope"),AppConfig.getProperty("alipay_redirect_uri"),AppConfig.getProperty("init"),getRequest().getParameter("request_id")));

	}
	
	@RequestMapping("/weibo")
	public void weibo() throws IOException {
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().sendRedirect(MessageFormat.format(AppConfig.getProperty("weibo_request_login_url"),AppConfig.getProperty("weibo_client_id"),AppConfig.getProperty("weibo_redirect_uri") ,AppConfig.getProperty("weibo_scope"),getRequest().getParameter("request_id")));

	}
	
	@RequestMapping("/wechat")
	public void wechat() throws IOException {
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().sendRedirect(MessageFormat.format(AppConfig.getProperty("wechat_request_login_url"),AppConfig.getProperty("wechat_client_id"), AppConfig.getProperty("wechat_redirect_uri") ,AppConfig.getProperty("wechat_request_scope"),getRequest().getParameter("request_id")));

	}
	
	@RequestMapping("/qq")
	public void qq() throws IOException {
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().sendRedirect(MessageFormat.format(AppConfig.getProperty("qq_request_login_url"), AppConfig.getProperty("qq_client_id"), AppConfig.getProperty("qq_redirect_uri"),getRequest().getParameter("request_id")));

		}
}
