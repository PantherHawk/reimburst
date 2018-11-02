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
		loginEmployee({username, password}) {
			return API.post(`/login`, { username, password });
		}

}