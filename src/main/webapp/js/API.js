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
        return _response.ok ? _response : Promise.reject(_response.statusText);
    },

    _handleContentType(_response) {
        const contentType = _response.headers.get('content-type');
        const redirected = _response.redirected;
        console.log('content type:    ', contentType)
        console.log("response text: " + _response.text())
        if (contentType && contentType.includes('application/json')) {
            return _response.json();
        } else if (redirected) {
        	return _response;
        }
        return Promise.reject('Oops! Aint no JSON!')
    },

    get(_endpoint) {
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
            headers: { 'Contnet-type': 'application/json' },
            body: JSON.stringify(body)
            })
            .then(this._handleError)
            .then(this._handleContentType)
            .catch(error => { throw new Error(error) })
    }
}