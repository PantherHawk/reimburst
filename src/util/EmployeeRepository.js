import API from './API'

export default {
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
                team_id: team_id,
                active: false,
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
        console.log("is API not found? ", API)
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
    },
    editProfile(body) {
        return API.put(`/employee`, body)
        .then(result => {
            console.log("result of update employee profile request")
            return result
        })
    }

}