
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file = "./header.jsp" %>
</head>
<body>
	<header class="intro-header"
		style="background-image: url('${imghome}/home_darkroom.jpg')">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 ">
					<div class="site-heading">
						<h1>social login</h1>
						
					</div>
				</div>
			</div>
		</div>
	</header>
  <div class="container register">
	<div class="row" style="margin:20px 0 50px;">
		
		<div class="col-xs-12 col-sm-5 col-md-5" id="LEFT_DIV">	
				<img id="social_img" style="margin-top:50px" class="img-circle center-block" height="310" width="400" />
			<div  data-example-id="simple-nav-stacked">
			    <ul class="nav nav-pills nav-stacked col-md-offset-3 ">
			      <li id="nav1" role="presentation" class="panel-group">
			      	<a href="#">您的社交号为<p class="text-right"> ${social_id}</p></a>
			      </li>
			      <li id="nav2" class="panel-group" role="presentation">
			      	<a href="#">欢迎！ <p class="text-right"> ${display_name} 请选择以下方式登录:</p></a>
			      </li>
			       <li class="panel-group" role="presentation">
			      	<button id="nav3" type="button" class="btn btn-block" role="button" data-toggle="collapse" data-target="#regform" aria-expanded="false" aria-controls="regform">
		  				新用户注册访问
					</button>	
			      </li>
			      <li class="panel-group" role="presentation">
			      	<button id="nav4" type="button" class="btn btn-block" onClick="javascript:top.location='/social_login/sso/autoLogin'">
		  				免登录访问
					</button>	
			      </li>
	
			    </ul> 	
			  </div>	
		</div>
		
		
		<div class="col-xs-12 col-sm-1 col-md-1"></div>
		<div class="col-xs-12 col-sm-5 col-md-6" >
			 
			<form role="form-horizontal" role="form" class="hide collapse" id="regform" name="regform" action="http://iam12c.oracledemo.com:14100/oam/server/auth_cred_submit" method="post">
			<!-- action="${ctx}/social/home  -->
				<div class="page-header">
                    <h2 class="text-center text-warning">
                       		 Oracle 访问管理平台
                    </h2>
				</div>
				<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
					 	<div class="collapse navbar-collapse alert alert-info" role="alert" id="createOrLinkTitle">
				     	   <p class="navbar-text navbar-right"> <a class="navbar-link" href="#">有疑问？</a></p>
				     	   <p class="navbar-text navbar-left" id="formtitle"> 您还没有企业账号, 请创建:</p>
				        </div>
					    
					    <div class="tab-conten" id="myTabContent">
					      <div class="tab-pane fade active in" id="creat-acct" role="tabpanel" aria-labelledby="home-tab">
       				        		<div class="form-group has-feedback">
	       				        		<label for="surname">姓</label>
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-text-color"></span></span>
											<input class="form-control" id="surname_link" type="text" maxlength="20" placeholder="姓" >
										</div>
										<span class="tips" style="color:red;display: none;"></span>
										<span class=" glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
										<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
									</div>
									<div class="form-group has-feedback">
										<label for="firstname">名</label>
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-text-background"></span></span>
											<input class="form-control" id="firstname_link" type="text" maxlength="20" placeholder="名">
										</div>	
										<span class="tips" style="color:red;display: none;"></span>
										<span class=" glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
										<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
									</div>
									<div class="form-group has-feedback">
										<label for="username">用户名</label>
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
											<input class="form-control" id="username_link" type="text" maxlength="20" placeholder="请输入用户名">
										</div>
										
										<span class="tips" style="color:red;display: none;"></span>
										<span class=" glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
										<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
									</div>
									
									<div class="form-group has-feedback">
										<label for="mail">邮箱</label>
											<div class="input-group">
												<span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
												<input class="form-control"  id="mail_link" type="text" maxlength="20" placeholder="邮箱" >
												<div class="input-group-addon">@oracledemo.com</div>
											</div>
											<span class="tips" style="color:red;display: none;"></span>
											<span class=" glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
											<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
											
									</div> 		
									<div class="form-group has-feedback">
										<label for="password">密码</label>
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
											<input class="form-control" name="password_link" id="password_link" type="password" maxlength="20" placeholder="请输入密码" >
										</div>
										
										<span class="tips" style="color:red;display: none;"></span>
										<span class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
										<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
									</div>
																		
									<div class="form-group has-feedback" id="phoneNum_link">
										<label for="phoneNum">手机号码</label>
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-phone"></span></span>
											<input class="form-control" id="phoneNum" type="text" maxlength="11" placeholder="请输入手机号码">
										</div>
										<span class="tips" style="color:red;display: none;"></span>
										<span class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
										<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
									</div>
									
									<div class="row" id="idcode_link">
										<div class="col-xs-7" >
											<div class="form-group has-feedback">
												<label for="idcode-btn">校验码</label>
											<div class="input-group">
												<span class="input-group-addon"><span class="glyphicon glyphicon-qrcode"></span></span>
												<input class="form-control" id="idcode_btn" type="text" maxlength="6" placeholder="请输入校验码">
											</div>
											<span class="tips" style="color:red;display: none;"></span>
											<span class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
											<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
											</div>
										</div>
										<div class="col-xs-5 text-center" style="padding-top: 26px">
											<button id="otp" class="btn btn-primary" id="loadingButton" type="button" autocomplete="off">获取短信校验码</button>
										</div>
									</div>
									
									<div class="form-group has-feedback hide">
										<label for="passwordConfirm">确认密码</label>
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
											<input class="form-control" id="passwordConfirm" type="password" maxlength="20" placeholder="请再次输入密码">
										</div>
										<span class="tips" style="color:red;display: none;"></span>
										<span class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
										<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
									</div>
									
									<div class="row hide" >
										<div class="col-xs-7">
											<div class="form-group has-feedback">
												<label for="idcode-btn">验证码</label>
												<div class="input-group">
													<span class="input-group-addon"><span class="glyphicon glyphicon-qrcode"></span></span>
													<input class="form-control" id="idcode-btn" type="text" maxlength="4" placeholder="请输入验证码">
												</div>
												<span class="tips" style="color:red;display: none;"></span>
												<span class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
												<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
											</div>
										</div>
										<div class="col-xs-5" style="padding-top: 30px">
											<div id="idcode" style="background: transparent;"></div>
										</div>
									</div>
					      </div>
					      					   
					</div>
	    
					<hr/>
					
     				<input name="request_id" value="${param.state}" type="hidden"/>
					<input name="password_link" id="username_link" type="hidden"/>
					
					<input name="social_id" id="social_id" value="${social_id}" type="hidden"/>
					<input name="social_access_token" id="social_access_token" value="${access_token}" type="hidden"/>
					<input name="social_type" id="social_type" value="${social_type}" type="hidden"/> 
				 															
					<button type="button" class="btn btn-block" id="associate_submit" name="associate_submit" type="associate_submit" >关 联</button>
					<button type="button" class="btn btn-block" id="create_submit"  name="create_submit" type="create_submit" >创 建</button>
			
					
			</form>
			

		</div>
		
	</div>
  </div>
  
	<script type="text/javascript" src="${jshome}/loginAuthN.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){		
		var socialid= "${social_id}";
		var socialtype=	"${social_type}";

		if(socialtype=="wechat"){ 
			wechatTheme();
		}else if(socialtype=="alipay"){
			alipayTheme();
		}else if(socialtype=="weibo"){
			weiboTheme();
		}else if(socialtype=="qq"){
			qqTheme();
		}else{
			noneTheme();
		}
 		socialIdisNotExisted();
/* 		var params = {};
 		params.social_id = socialid; 
		 $.ajax({  
			         type: "POST",  
					 dataType: "json",//预期服务器返回的数据类型
			       	 url:"../ldap/findBySid",  
			         data:params,// 序列化表单值  getUrlPara(app_id)
			         async: true,  		           
			         success: function(data) {
				      		
							$('#username').val(data.person.username);
							socialIdisNotExisted();
						 	if(data.person=='No_Sid_Posted'){
								alert("No sid posted.");
							}else if(data.person=='Not_Found'){
								socialIdisNotExisted();        		
							}else{
								socialIDisExisted(data);
							} 
						 
					 },
					  error: function (XMLHttpRequest, textStatus, errorThrown) {
		                     // 状态码
		                    console.log(XMLHttpRequest.status);
		                    // 状态
		                    console.log(XMLHttpRequest.readyState);
		                    // 错误信息   
		                    console.log(textStatus);
		                    
		        	  	 	noLDAPAlert(); 
		              },
		              complete: function(jqXHR, textStatus){
		       			  jqXHR.done(function(data, textStatus, jqXHR) {
		            			
		            	  });
		              }
			});  */
		 
	});
	
	</script>
</body>
</html>