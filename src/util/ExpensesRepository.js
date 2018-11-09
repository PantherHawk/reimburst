import API from './API'

export default {
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
                status: status == null ? 'Pending' : (status === "approve" ? 'Approved' : 'Rejected'),
                amount: amount,
                daysSinceRequest: diffDays(new Date(), new Date(dateSubmitted)),
                dateHandled: new Date(dateHandled),
                description: description,
                employee_id: employeeId,
                team_id: teamId,
                active: false
            }
        })
    },
    // add uri later
    getForUser(user) {
        return API.get(`/expenses`)
        .then(this._normalizeData);
    },
    // add uri later
    getAll() {
        return API.get(`/expenses`)
        .then(this._normalizeData);
    },

    approveExpense(decision, id) {
    	decision = decision.toLowerCase();
        return API.post(`/decision/decision?decision=${decision}`, {"id": id})
        .then(success => success)
        .catch(err => console.log('Err: ', err))
    },
    addExpense(expense) {
        return API.post(`/expenses`, expense)
    }


}

const diffDays = (past, now) => {
    const oneDay = 24*60*60*1000;
    return Math.round(Math.abs(now.getTime() - past.getTime())/(oneDay));
}

// module.exports = ExpensesRepository;