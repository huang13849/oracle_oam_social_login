<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${request.contextPath}/social_login" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>授权登录</title>
<meta name="title" content="网页集成(微博，QQ，微信)登录">
<meta name="keywords" content="网页集成(微博，QQ，微信)登录">
<meta name="description" content="">
<meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<c:set var="csshome" value="${ctx}/css" />
<c:set var="jshome" value="${ctx}/js" />
<c:set var="imghome" value="${ctx}/images" />

<link href="${csshome}/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">
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
	<div class="row" style="margin:10px 0 50px;">
		
		<div class="col-xs-12 col-sm-5 col-md-5">	
			<img class="img-circle" src="${imghome}/alipay.jpg" />
			<div class="list-group">
				<div class="list-group-item active">
	              <h4 class="list-group-item-heading">您的社交号为 ${param.source}</h4>
	              <p class="list-group-item-text">${param.app_id} </p>
           		 </div>
			</div>
			<div class="list-group">
				<div class="list-group-item" >
	              <h4 class="list-group-item-heading">请在右边选择关联或注册 </h4>
	              <p class="list-group-item-text"></p>
            	</div>
			</div>
		</div>
		<div class="col-xs-12 col-sm-1 col-md-1"></div>
		<div class="col-xs-12 col-sm-5 col-md-5">
			 
			<form role="form-horizontal" role="form" id="regform" name="regform" action="/oam/server/auth_cred_submit" method="post">
			<!-- action="${ctx}/social/home  -->
				<div class="page-header">
                    <h2 class="text-center text-warning">
                       		 Oracle 访问管理平台
                    </h2>
				</div>
				<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
					    <ul class="nav nav-tabs" id="myTabs" role="tablist">
					      <li class="active" role="presentation"><a id="home-tab" role="tab" aria-expanded="true" aria-controls="creat-acct" href="#creat-acct" data-toggle="tab">创建OAM账号</a></li>
					      <li role="presentation"><a id="profile-tab" role="tab" aria-expanded="false" aria-controls="link-acct" href="#link-acct" data-toggle="tab">绑定OAM账号</a></li>
					    </ul>
					    <div class="tab-content" id="myTabContent">
					      <div class="tab-pane fade active in" id="creat-acct" role="tabpanel" aria-labelledby="home-tab">
       				        	
									<div class="form-group has-feedback">
										<label for="username">用户名</label>
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
											<input class="form-control" id="username" type="text" maxlength="20" placeholder="请输入用户名">
										</div>
										
										<span class="tips" style="color:red;display: none;"></span>
										<span class=" glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
										<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
									</div>
									
									<div class="form-group has-feedback">
										<label for="password">密码</label>
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
											<input class="form-control" id="password" type="password" maxlength="20" placeholder="请输入密码">
										</div>
										
										<span class="tips" style="color:red;display: none;"></span>
										<span class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
										<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
									</div>
									
									<div class="form-group has-feedback">
										<label for="passwordConfirm">确认密码</label>
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
											<input class="form-control" id="passwordConfirm" type="password" maxlength="20" placeholder="请再次输入密码">
										</div>
										<span class="tips" style="color:red;display: none;"></span>
										<span class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
										<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
									</div>
									
									<div class="row">
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
									
									<div class="form-group has-feedback">
										<label for="phoneNum">手机号码</label>
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-phone"></span></span>
											<input class="form-control" id="phoneNum" type="text" maxlength="11" placeholder="请输入手机号码">
										</div>
										<span class="tips" style="color:red;display: none;"></span>
										<span class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
										<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
									</div>
									
									<div class="row">
										<div class="col-xs-7">
											<div class="form-group has-feedback">
												<label for="idcode-btn">校验码</label>
											<div class="input-group">
												<span class="input-group-addon"><span class="glyphicon glyphicon-qrcode"></span></span>
												<input class="form-control" id="idcode-btn" type="text" maxlength="6" placeholder="请输入校验码">
											</div>
											<span class="tips" style="color:red;display: none;"></span>
											<span class="glyphicon glyphicon-remove form-control-feedback" style="display: none;"></span>
											<span class="glyphicon glyphicon-ok form-control-feedback" style="display: none;"></span>
											</div>
										</div>
										<div class="col-xs-5 text-center" style="padding-top: 26px">
											<button class="btn btn-primary" id="loadingButton" type="button" autocomplete="off">获取短信校验码</button>
										</div>
									</div>
	
					      </div>
					      <div class="tab-pane fade" id="link-acct" role="tabpanel" aria-labelledby="profile-tab">
         						<div class="form-group has-feedback">
										<label for="username"></label>
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
											<input class="form-control"  name="username" id="username-link" type="text" maxlength="20" placeholder="请输入用户名">
										</div>
										
								
								</div>
								<div class="form-group has-feedback">
										<label for="password"></label>
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
											<input class="form-control" name="password" id="password-link" type="password" maxlength="20" placeholder="请输入密码">
										</div>
								
								</div>
								<input name="request_id" value="${param.reqId}" type="hidden">
								
					      </div>
				
					    </div>
					</div>
	    
					<hr/>
				
					<div class="form-group">
							<input class="form-control btn btn-primary" id="submit" type="submit" value="确&nbsp;&nbsp;定&nbsp;&nbsp;">
					</div>
									
					<div class="form-group">
							<input class="form-control btn btn-danger" id="reset" type="reset" value="重&nbsp;&nbsp;置&nbsp;&nbsp;">
					</div>
				
			</form>
			

		</div>
		
	</div>
  </div>
  
	<script type="text/javascript" src="${jshome}/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${jshome}/bootstrap.min.js"></script>
	<script type="text/javascript" src="${jshome}/bootstrap.js"></script>
	<script type="text/javascript" src="${jshome}/loginAuthN.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
				
		
	});

	</script>
</body>
</html>