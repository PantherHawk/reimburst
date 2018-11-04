/**
 * API for hitting the servlet 
 */

const API = {
    /** check whether the content type is correct before processing further
     */
    url: {
    	dummy: `https://jsonplaceholder.typicode.com/todos`,
    	dev: `http://localhost:8081/EmployeeReimbursement/api`,
    	prod: ``
    		},

    _handleError(_response) {
    	console.log("handling any errors from the response...")
        return _response.ok ? _response : Promise.reject(_response.statusText);
    },

    _handleContentType(_response) {
    	console.log("Checking the content type and processing json...")
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
    	console.log('_endpoint--------->' + _endpoint)
    	console.log('WT ACTUAL FUCK!')
        _endpoint = _endpoint ? _endpoint : ''; /** for testing */
        return fetch(this.url.dev + _endpoint, {
            method: 'GET',
            headers: new Headers({
                'Accept': 'application/json'
            })
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
            body: JSON.stringify(body)
            })
            .then(this._handleError)
            .then(this._handleContentType)
            .catch(error => { throw new Error(error) })
    }
}