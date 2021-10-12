<html>
	<head>
		<meta charset="utf-8">
		<title>展示用户信息-freemarker页面</title>
	</head>
	<body>
	    <h2>展示用户信息-freemarker页面</h2>
		<table border="1" align="center" width="50%">
			<tr>
				<th>Id</th>
				<th>name</th>
				<th>age</th>
			</tr>
			 
			 <#list userList as user>
				 <tr>
					 <td>${user.userId}</td>
					 <td>${user.userName}</td>
					 <td>${user.userAge}</td>
				 </tr>
			</#list> 
		</table>
	</body>
</html>
