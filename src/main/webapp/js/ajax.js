
var request;

function question() {
	var element = document.getElementById("question");
	var input = element.value;
	element.value = "";
	record("me", input);
	var url = "robot/interaction?question=" + encodeURI(input) + "&ran="
			+ new Date().getTime();
	if (window.XMLHttpRequest) {
		request = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		request = new ActiveXObject("Microsoft.XMLHTTP");
	}
	request.open("GET", url, true);
	request.onreadystatechange = callback;
	request.send(null);
}

function callback() {
	if (request.readyState == 4 && request.status == 200) {
		var response = request.responseText;
		answer(response);
	}
}

function answer(response) {
	record("super-robot", response);
}

function record(user, text) {
	var line = user + "  :  " + text + "<br/>";
	var history = document.getElementById("history");
	history.innerHTML += line;
}