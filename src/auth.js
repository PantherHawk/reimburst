import EmployeeRepository from './util/EmployeeRepository'
import API from './util/API'

export default {
    login(username, password, cb) {

        cb = arguments[arguments.length - 1]
        if (localStorage.token) {
            if (cb) cb(true)
            this.onChange(true)
            return
        }
        realLogin(username, password, (res) => {
            if (res.authenticated) {
                localStorage.token = res.token
                if (cb) cb(true)
                this.onChange(true)
            } else {
                if (cb) cb(false)
                this.onChange(false)
            }
        })
    },

    getToken() {
        return localStorage.token
    },

    logout(cb) {
        delete localStorage.token
        if (cb) cb()
        this.onChange(false)
    },

    loggedIn() {
        return !!localStorage.token
    },
    manager() {
        return !!localStorage.manager
    },

    onChange() {}
}

function pretendRequest(email, password, cb) {
    setTimeout(() => {
        if (email == 'joe@example.com' && password == 'password') {
            cb({
                authenticated: true,
                token: Math.random().toString(36).substring(7)
            })
        } else {
            cb({ authenticated: false })
        }
    }, 300)
    
}
// TODO: real request to api
function realLogin(username, password, cb) {
    const body = { username: username, password: password }
    // hit db, return promise
    // const body = JSON.stringify({ email: email, password: password })
    // fetch(`https://jsonplaceholder.typicode.com/users`, {
    //     method: "POST",
    //     headers: { 'Content-Type': 'application/json' },
    //     body: body,
    //     mode: 'cors',
    // })
    // EmployeeRepository.loginEmployee({username, password})
    fetch(`http://localhost:8081/EmployeeReimbursement/api/login`, {
        method: 'POST',
        // headers: { 'Content-type': 'application/json' },
        body: JSON.stringify(body),
        mode: 'cors' 
    })
    .then(users => {
        const result = users
        console.log('user from db: ', result)
        return result.json()
    })
    .then(json => JSON.stringify(json))
    .then(user => {
        console.log('user id', )
        if (JSON.parse(user).id > 0) {
            if (JSON.parse(user).hasManager < 1) {
                return cb({
                    authenticated: true,
                    manager: true,
                    token: Math.random().toString(36).substring(7),
                })
            } else {
                return cb ({
                    authenticated: true,
                    token: Math.random().toString(36).substring(7),
                })
            }
        } else {
            console.log('faq')
            cb({ authenticated: false })
        }
    })
}