<!DOCTYPE html>
<html>
<head>
	<title>用户添加</title>
	<link rel="stylesheet" href="${ctx}/static/css/formSelects-v4.css"
		  media="all" />
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;" >
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">用户名</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input userName"
				   lay-verify="required" name="userName" value="${(user
			.userName)!}" id="userPwd"
				   placeholder="请输入用户名">
		</div>
	</div>
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">真实姓名</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input userName"
				   lay-verify="required" name="trueName" value="${(user.trueName)!}" id="trueName"
				   placeholder="请输入真实姓名">
		</div>
	</div>
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">邮箱</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input userEmail"
				   lay-verify="email"  name="email" value="${(user.email)!}"
				   id="email"
				   placeholder="请输入邮箱">
		</div>
	</div>
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">手机号</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input userEmail"
				   lay-verify="phone"  name="phone" value="${(user.phone)!}"
				   id="phone"
				   placeholder="请输入手机号">
		</div>
	</div>
	<div class="layui-row">
		<div class="magb15 layui-col-md4 layui-col-xs12">
			<label class="layui-form-label">会员状态</label>
			<div class="layui-input-block">
				<select name="isValid" >
					<#--<option value="1" <#if user?? && user.isValid==1>selected</#if>>启用</option>
					<option value="0" <#if user?? && user.isValid==0>selected</#if>>禁用</option>-->
					<#if user?? && user.isValid==0>
						<option value="1">启用</option>
						<option value="0" selected>禁用</option>
						<#else>
							<option value="1" selected>启用</option>
							<option value="0" >禁用</option>
					</#if>
				</select>
			</div>
		</div>

		<div class="magb15 layui-col-md4 layui-col-xs12">
			<label class="layui-form-label">角色</label>
			<div class="layui-input-block">
				<select name="roleIds"  xm-select="selectId">
				</select>
			</div>
		</div>
	</div>

	<div class="layui-form-item layui-row layui-col-xs12">
		<div class="layui-input-block">
			<button class="layui-btn layui-btn-lg" lay-submit=""
					lay-filter="addUser">确认</button>
			<button  class="layui-btn layui-btn-lg layui-btn-normal">取消</button>
		</div>
	</div>
	<input type="hidden" name="id"  value="${(user.id)!}">
</form>
<script type="text/javascript" src="${ctx}/static/js/user/userUpdate.js"></script>
</body>
</html>