<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/ajax.js"></script>
<title>super-robot</title>

<style type="text/css">
.testDiv {
	bottom: 36px;
	height: calc(100vh - 120px);
	resize: none;
	overFlow-y: scroll;
}

.keyword {
	color: red;
}
</style>
</head>
<body>
	<div class="main">
		<div class="content">
			<div class="dialog">
				<div class="history">
					<div class='testDiv'  id="history">
					
					</div>
				</div>
				<div class="question">
					<input id="question" type="text" style="height: 25px;" size="30px">
					<button style="height: 30px; width: 50px;" onclick="question()">提交</button>
				</div>
				<br> <br>
				<div>
					<form action="robot/upload" id="form1" name="form1"
						encType="multipart/form-data" method="post" target="hidden_frame">
						<input type="file" id="file" name="file" style="width: 450">
						<INPUT type="submit" value="上传文件"><span id="msg"></span>
						<iframe name='hidden_frame' id="hidden_frame"
							style='display: none'> </iframe>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

