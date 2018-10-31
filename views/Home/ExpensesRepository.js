// const API = require('../../util/API');

const ExpensesRepository = {
    uri: {
        forEveryone: `/api/expenses`,
        forUser: `/api/expenses/user?id=`,
    },
    _normalizeData(expenses) {
        console.log('expenses: ', expenses)
        
        return expenses.map(expense => {
            const { id, title, completed } = expense;
            return {
                id: id,
                name: title,
                approved: completed
            }
        })
    },
    // add uri later
    getForUser(user) {
        return API.get(`/api/expenses/user?id=${user.id}`);
    },
    // add uri later
    getAll() {
        return API.get()
        .then(this._normalizeData)
    },


}

// module.exports = ExpensesRepository;