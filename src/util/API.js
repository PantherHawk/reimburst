
export default {
    /** check whether the content type is correct before processing further
     */
    url: {
    	dummyExpenses: `https://jsonplaceholder.typicode.com/todos`,
    	dev: `http://localhost:8081/EmployeeReimbursement/api`,
    	prod: ``
    },

    _handleError(_response) {
    	console.log("handling any errors from the response...")
    	const status = {
    		"404": "Login.html",
    		"500": "ServerError.html",
    	}
    	console.log("response ----------->   " + _response.status)
    	if (_response.status == "404") {
    		console.log("got a 404, redirecting to login.")
    		window.location.replace(status[_response.status]);
    		return;
    	} else if (_response.status == "500") {
    		console.log("got a 500, redirecting to Error page.")
    		window.location.replace(status[_response.status]);
    		return;
    	}
        return _response.ok ? _response : Promise.reject(_response.statusText);
    },

    _handleContentType(_response) {
        console.log("Checking the content type and processing json...")
        console.log("the fuck's my response obj ......", _response)
        const contentType = _response.headers.get('content-type');
        const redirected = _response.redirected;
        console.log('content type:    ', contentType)
//        console.log("response text: " + _response.text())
        if (contentType && contentType.includes('application/json')) {
        	console.log('result: ' + _response);
            return _response.json();
        } else if (contentType.includes('text/html')) {
        	console.log('redirected');
        	return _response;
        }
        return Promise.reject('Oops! Aint no JSON!')
    },

    get(_endpoint) {
    	console.log('_endpoint sent to GET--------->' + _endpoint)
        _endpoint = _endpoint ? _endpoint : ''; /** for testing */
        return fetch(this.url.dev + _endpoint, {
            method: 'GET',
            headers: new Headers({
                'Accept': 'application/json'
            }),
            mode: 'cors'
        })
        .then(this._handleError)
        .then(this._handleContentType)
        .catch(error => { throw new Error(error) })
    },
    post(_endpoint, body) {
        _endpoint = _endpoint ? _endpoint : ''; /** for testing */
        console.log("endpoint passed in as   ", _endpoint)
        return fetch(this.url.dev + _endpoint, {
            method: 'POST',
            headers: { 'Content-type': 'application/json' },
            body: JSON.stringify(body),
            mode: 'cors' 
            })
            .then(r => this._handleContentType(r))
            .then(l => this._handleError(l))
            .catch(error => { throw new Error(error) })
    }
}