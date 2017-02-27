<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="renderer" content="webkit">
  <title></title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="${ctx}/dist/css/vendor.css" type="text/css" />
  <link rel="stylesheet" href="${ctx}/dist/css/bundle.css" type="text/css" />
  <script type="text/javascript" src="${ctx}/dist/js/vendor.min.js"></script>
</head>
<body>
  <div class="container" style="margin-top: 100px">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 col-sm-6 col-sm-offset-3 form-box">
            <!-- <h3>登陆</h3> -->
            <div class="panel panel-default login-panel">
                <div class="panel-body">
                    <h3 style="text-align: center">登陆</h3>
                    <hr>
                    <form class="form-horizontal" action="#" method="post">
                        <div class="form-group">
                            <label for="name" class="col-md-2 col-sm-2 control-label">账号</label>
                            <div class="col-md-10 col-sm-10">
                                <input type="text" class="form-control" name="userName" placeholder="账号" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-md-2 col-sm-2 control-label">密码</label>
                            <div class="col-md-10 col-sm-10">
                                <input type="password" class="form-control" name="passwd" placeholder="密码" required>
                            </div>
                        </div>
                        <div class="form-group">
		    				 <c:if test="${jcaptcha}">
		    				    <label for="password" class="col-md-2 col-sm-2 control-label">验证码</label>
								<input id="jcaptchaCode" name="jcaptchaCode"  required type="text" class="form-control x164 in" placeholder="验证码" nullmsg="请输入验证码!"/>
								<img class="m" id="captcha_img" src="${ctx }/jcaptcha.jpg" alt="点击更换" title="点击更换"/>
							</c:if>
		    			</div>
		    			<div class="form-group">
                            <label for="role" class="col-md-2 col-sm-2 control-label"></label>
                            <div class="col-md-10 col-sm-10">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" id="remember"> 记住我
                                    </label>
                                </div>
                            </div>
                        </div>
				        <div id="errorMsg" class="form-group">
				      		<div class="row">
						        <div id="msg" class="alert alert-danger sr-only" role="alert">
								  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
								  <span class="sr-only">Error:</span>
								  ${msg }
				 				   <button type="button" class="close" data-dismiss="alert" 
								      aria-hidden="true">
								      &times;
								   </button>
							    </div>
						    </div>
					    </div>
                        <input type="submit" class="btn btn-primary pull-right" value="登陆"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>