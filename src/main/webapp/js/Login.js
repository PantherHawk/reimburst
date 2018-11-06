// grab the value of input fields
// send them in a POST to Employee repo

window.onload = function () {
	console.log('page loaded.')
	const loginBtn = document.querySelector(".login");
	loginBtn.addEventListener("click", loginSubmit);
}

function loginSubmit(event) {
	event && event.preventDefault();
	const user = document.querySelector("#inputEmail").value;
	const pass = document.querySelector("#inputPassword").value;
	
	console.log(`Employee [  ${user}   ${pass}  ]`);
	
	EmployeeRepository.loginEmployee({ "username": user, "password": pass })
	.then(user => {
		sessionStorage.setItem("user", JSON.stringify(user));
//		console.log("user response: ", JSON.parse(user))
		if (user.id > 0 && user.hasManager < 1) {
//			redirect;
			console.log("manager log in success, redirecting to manager page...")
			window.location.replace("Manager.html");
		} else if (user.id > 0) {
			console.log("employee log in success, redirecting to employee page")
			window.location.replace("Home.html")
		}
	});
}