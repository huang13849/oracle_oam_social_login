<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${request.contextPath}/social_login" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>网页集成(微博，QQ，微信)登录</title>
<meta name="title" content="网页集成(微博，QQ，微信)登录">
<meta name="keywords" content="网页集成(微博，QQ，微信)登录">
<meta name="description" content="">
<meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<c:set var="csshome" value="${ctx}/css" />
<c:set var="jshome" value="${ctx}/js" />
<c:set var="imghome" value="${ctx}/images" />

<link href="${csshome}/bootstrap.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<header class="intro-header"
		style="background-image: url('${imghome}/home_darkroom.jpg')">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 ">
					<div class="site-heading">
						<h1>social login</h1>
						<span class="subheading">请您使用第三方平台账号登陆：</span>
					</div>
				</div>
			</div>
		</div>
	</header>
<div class="container theme-showcase" role="main">
	<div class="jumbotron">  
		<h2>Oracle Access Management </h2>
		<p> 第三方社交登录 </p> 
	</div>
	
	<div class ="row">
			 <div class="col-sm-6">
			  		<div class="jumbotron">
					  <form class="form-signin" action="/oam/server/auth_cred_submit" method="post">
				        <label for="inputText" class="sr-only">user name</label>
				        <input type="text" name="username" class="form-control" placeholder="username" required autofocus>
				        <label for="inputPassword" class="sr-only">Password</label>
				        <input type="password" name ="password" id="inputPassword" class="form-control" placeholder="Password" required>
						<input name="request_id" value="${param.reqId}" type="hidden">
				        <div class="checkbox">
				          <label>
				            <input type="checkbox" value="remember-me"> Remember me
				          </label>
				        </div>
				        <button name="submit" value="submit" class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
				      </form>		  
				     </div>
			 </div>
		  
			<div class="col-sm-6">
				<div class="jumbotron">
					<p>
							<a href="${ctx}/social/weibo" target="_top">
								<button type="button" class="btn btn-lg btn-warning" style="width: 170px;">新浪微博登录</button>
							</a>
					</p>	
					<p>
							<a href="${ctx}/social/qq" target="_top">
								<button type="button" class="btn btn-lg btn-primary" style="width: 170px;">腾讯QQ登录</button>
							</a>
					</p>
				
					<p>
							<a href="${ctx}/social/wechat" target="_top">
								<button type="button" class="btn btn-lg btn-success" style="width: 170px;">微信登录</button>
							</a>
					</p>
					<p>
							<a href="${ctx}/sso/alipay" target="_top">
								<button type="button" class="btn btn-lg btn-info" style="width: 170px;">支付宝登录</button>
							</a>
					</p>
				</div>
			
			
					
			</div>
		</div>
</div>
	<script type="text/javascript" src="${jshome}/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${jshome}/bootstrap.min.js"></script>
	<script type="text/javascript" src="${jshome}/bootstrap.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		
	});
	</script>
</body>
</html>