// const API = require('../../util/API');

const ExpensesRepository = {
    uri: {
        forEveryone: `/expenses`,
        forUser: `expenses/user/:`,
    },
    _normalizeData(expenses) {
        console.log('expenses: ', expenses)
        
        return expenses.map(expense => {
            const { id, name, status, amount, dateHandled, dateSubmitted, description, employeeId, teamId } = expense;
            return {
                id: id,
                title: name,
                status: status == null ? 'Pending' : (status == 'APPROVED' ? 'Approved' : 'Rejected'),
                amount: amount,
                daysSinceRequest: diffDays(new Date(), new Date(dateSubmitted)),
                dateHandled: dateHandled,
                description: description,
                employee_id: employeeId,
                team_id: teamId,
            }
        })
    },
    // add uri later
    getForUser(user) {
        return API.get(`/api/expenses/`);
    },
    // add uri later
    getAll() {
        return API.get(this.uri.forEveryone)
        .then(this._normalizeData);
    },

    approveExpense(decision, id) {
        return API.post(`/api/${decision}/expenses`, {"id": id})
        .then(this._normalizeData);
    }


}

const diffDays = (past, now) => {
    const oneDay = 24*60*60*1000;
    return Math.round(Math.abs(now.getTime() - past.getTime())/(oneDay));
}

// module.exports = ExpensesRepository;