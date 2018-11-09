<template>
    <div>
        <h2>Employee Expenses page!</h2>
        <expenses-list v-bind:expenses="userExpenses"></expenses-list>
    </div>
</template>
<script>
import ExpensesRepository from '../util/ExpensesRepository'
import ExpensesList from './ExpensesList'
export default {
    created: function() {
        this.getExpenses()
    },
    data() {
        return {
            userExpenses: []
        }
    },

    components: {
            'expenses-list': ExpensesList,
        },
    methods: {
        getExpenses() {
            // TODO: fetch from Servlet, right now it's just jsonplaceholder
             ExpensesRepository.getForUser({ 
                 username: localStorage.username, 
                 password: localStorage.password 
            })
            .then(expenses => {
                console.log("Expenses in vue:   " + JSON.stringify(expenses))
                this.userExpenses = expenses
                })
            .catch(err => err)
        },
    }
}
</script>
