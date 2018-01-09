package com.social.controller;

import java.io.IOException;

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

@Controller
@RequestMapping("/sso")
public class SsoController extends BaseController{
	private static Logger log = Logger.getLogger(SsoController.class);

	@RequestMapping("/login")
	public String login() {
		return "sso/login";
	}
	
	@RequestMapping("/alipayAuthN")
	public String loginAuthZ() throws Exception {
		String APP_ID = getRequest().getParameter("app_id");
		String scope = getRequest().getParameter("scope");
		String state = getRequest().getParameter("state");
		String auth_code = getRequest().getParameter("auth_code");
		String APP_PRIVATE_KEY = AppConfig.getProperty("alipay_app_private");
		String ALIPAY_PUBLIC_KEY = AppConfig.getProperty("alipay_public_key");
		System.out.println(auth_code);

		if(APP_ID!=null && APP_ID.length()>1 && APP_ID.equals(AppConfig.getProperty("alipay_client_id"))){
			 
			AlipayClient alipayClient = new DefaultAlipayClient (AppConfig.getProperty("alipay_request_access_token_url"),AppConfig.getProperty("alipay_client_id"),AppConfig.getProperty("alipay_app_private"),"json","utf-8",AppConfig.getProperty("alipay_public_key"),"RSA2" );
			AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
			request.setCode(auth_code);
			request.setGrantType("authorization_code");
			try {	
				AlipaySystemOauthTokenResponse oauthTokenResponse= alipayClient.execute(request);
				System.out.println("Alipay userID #### "+ oauthTokenResponse.getAlipayUserId());
				System.out.println("Access Token #### "+ oauthTokenResponse.getAccessToken());
				System.out.println("User Id #### " + oauthTokenResponse.getUserId());
				System.out.println("Refresh Token #### " + oauthTokenResponse.getRefreshToken());
				
				AlipayUserInfoShareRequest requestUser = new AlipayUserInfoShareRequest();

		        AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(requestUser, oauthTokenResponse.getAccessToken());
		        System.out.println(userinfoShareResponse.getBody());
		        System.out.println("UserId:" + userinfoShareResponse.getUserId());//用户支付宝ID
		        System.out.println("UserType:" + userinfoShareResponse.getUserType() );//用户类型
		        System.out.println("UserStatus:" + userinfoShareResponse.getUserStatus() );//用户账户动态
		        System.out.println("Email:" + userinfoShareResponse.getEmail() );//用户Email地址
		        System.out.println("IsCertified:" + userinfoShareResponse.getIsCertified() );//用户是否进行身份认证
		        System.out.println("IsStudentCertified:" + userinfoShareResponse.getIsStudentCertified() );//用户
		        
		        
			} catch (Exception e) {
					e.printStackTrace();
			}	
		}else {
				log.info("Alipay Authentication Failed");
		}
		
		//TODO AUTOMATIC CHECK LDAP
		if(true==true) {
			return "sso/alipayAuthN";
		}else {
			return "social/home";
		}
			
	}
	
	@RequestMapping("/alipay")
	public void alipay() throws IOException {
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().sendRedirect(AppConfig.getProperty("alipay_request_login_url")+"?app_id="+AppConfig.getProperty("alipay_client_id")+"&scope="+AppConfig.getProperty("alipay_request_scope")+"&redirect_uri="+ AppConfig.getProperty("alipay_redirect_uri")+"&state="+AppConfig.getProperty("init"));

	}
	
	
}
