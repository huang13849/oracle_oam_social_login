<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file = "./header.jsp" %>


</head>
<body>
<div>
	<form id="oamform" name="oamform" method="post" action="http://oam.oracledemo.com:14100/oam/server/auth_cred_submit">

					<input name ="username"  value="${username}" type="hidden" />

					<input name ="password" value="welcome1" type="hidden"/>
			
 					<input name="request_id" value="${request_id}" type="hidden"/>
					
					<input name="OAM_REQ" type="hidden" value="<%=request.getHeader("OAM_REQ") %>">
					
					<input name="social_id" value="${social_id}" type="hidden"/>
					
					<input name="social_access_token" value="${social_access_token}" type="hidden"/>
					
					<input name="social_type" value="${social_type}" type="hidden"/>			
	</form>
</div>
 
 				<div id="gridSystemModal" class="modal fade in" tabindex="-1" role="dialog" aria-labelledby="gridModalLabel" style="display: block; padding-right: 16px;">
				    <div class="modal-dialog" role="document">
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				          <h4 class="modal-title" id="gridModalLabel">开始认证</h4>
				        </div>
				        <div class="modal-body">
				          <div class="row">
				            <div id="gridModalText" class="col-md-4">请稍后</div>
				          </div>
		         
				        </div>
				        <div class="modal-footer">
				        	<div class="progress">
							  <div id="progressbar" class="progress-bar" role="progressbar" aria-valuenow="30" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
							  </div>
							</div>
				        </div>
				      </div>
				    </div>
				  </div>  
	
	
	
<script language="JavaScript" type="text/JavaScript">


var value=0;
var time =50;

function startBarIncrease(){
  	value+=1;  	
    $("#progressbar").css('width', value + "%").text(value + "%");
  	if(value>=100){
  		document.oamform.submit();
 		$("#gridModalLabel").text("认证成功！");
 		$("#gridModalText").text("请稍后,登录中。。。");
 		clearTimeout(load_t);
  	}
	
  	setTimeout("startBarIncrease()",time)	
 }

startBarIncrease();

</script>