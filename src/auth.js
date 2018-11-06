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
function realLogin(email, password, cb) {
    // hit db, return promise
    const body = JSON.stringify({ email: email, password: password })
    fetch(`https://jsonplaceholder.typicode.com/users`, {
        method: "POST",
        headers: { 'Content-Type': 'application/json' },
        body: body,
        mode: 'cors',
    })
    .then(users => {
        const result = users.json()
        console.log('users from dummy db: ', result)
        return result
    })
    .then(json => json)
    .then(user => {
        console.log('user object', user)
        if (user["id"] > 0) {
            cb({
                authenticated: true,
                token: Math.random().toString(36).substring(7),
            })
        } else {
            cb({ authenticated: false })
        }
    })
}