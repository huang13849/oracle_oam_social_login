<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file = "./header.jsp" %>


</head>
<body>
<div>
	<form id="oamform" name="oamform" method="post" action="http://oam.oracledemo.com:14100/oam/server/auth_cred_submit">

					<input name ="username"  value="${username}" type="hidden" />

					<input name ="password" value="${password}" type="hidden"/>
			
 					<input name="request_id" value="${param.request_id}" type="hidden"/>
					
					<input name="OAM_REQ" type="hidden" value="<%=request.getHeader("OAM_REQ") %>">
					
					<input name="social_id" value="${social_id}" type="hidden"/>
					
					<input name="social_access_token" value="${social_access_token}" type="hidden"/>
					
					<input name="social_type" value="${social_type}" type="hidden"/>			
	</form>
</div>

<script language="JavaScript" type="text/JavaScript">
var i=1;
function load_submit(){
	if(i==0){
		document.oamform.submit();
		clearTimeout(load_t);
	}
	i--;
	var load_t=setTimeout("load_submit()",1000);
}
load_submit();
</script>