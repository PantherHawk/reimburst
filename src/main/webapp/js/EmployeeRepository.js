const EmployeeRepository = {
		uri: `/employee/`,
		
		_normalizeData(employees) {
			console.log('employee data: ', employees);
			
			return employees.map(employee => {
				const { 
					firstName, 
					lastName, 
					username,
					hasManager,
					email,
					info,
					team_id
					} 
				= employee;
				return {
					firstName: firstName,
					lastName: lastName,
					username: username,
					hasManager: hasManager && hasManager > 0 ? 'NOT_ADMIN' : 'ADMIN',
					email: email,
					info: info,
					team_id: team_id
				}
			});
		},
		fetchAll() {
			return API.get(`/employee`)
			.then(employees => {
				console.log("employee object finally returned to fetchAll:   ", employees);
				return employees;
			})
		},
		loginEmployee({username, password}) {
			return API.post(`/login`, { username, password })
//			.then(data => data.json())
			.then(employee => {
				console.log("employee object finally returned to loginEmployee:  ", employee);
				return employee;
			});
		},
		logout() {
			return API.get(`/logout`)
			.then(logoutSuccess => {
				console.log("logged out!   ", logoutSuccess.status);
				return logoutSuccess;
			})
		}

}