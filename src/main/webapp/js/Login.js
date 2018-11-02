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
	.then(data => console.log(data));
}