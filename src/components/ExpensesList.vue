<template>
    <div>
        <ul id="expenses">
            <li v-for="{id, title, userId} in expenses">
                {{ id }} - {{ title }} - {{ userId }}
            </li>
        </ul>
        <!-- <h4>Pending list</h4> -->
        <!-- <ul id="expenses-pending">
            <li v-for="item of expenses" v-if="item.status == 'Pending'">
                {{ item.name }} - {{ item.amount }} - {{ item.status }}
            </li>
        </ul> -->
        <h3>Filtered List</h3>
        <ul>
            <li v-for="item of filteredItems">
                {{ item.name }} - {{ item.amount }} - {{ item.status }}
            </li>
        </ul>
        <input type="text" v-model="search">
        <input type="radio" id="Rejected" value="Rejected" v-model="status"><label for="Rejected">Rejected</label>
        <input type="radio" id="Approved" value="Approved" v-model="status"><label for="Approved">Approved</label>
        <input type="radio" id="Pending" value="Pending" v-model="status"><label for="Pending">Pending</label>
    </div>
</template>
<script>
// import expensesMixin from '../mixins/expensesMixin'
import ExpensesRepository from '../util/ExpensesRepository';

export default {
    created: function() {
        console.log('created ExpensesList component!   ')
        this.getExpenses()
    },
    data() {
        return {
            expenses: [
                { name: "Uber", amount: "$200", status: "Pending", emp_id: "1", dateSubmitted: new Date('11-1-2018'), description: "traffic, cars, transportaion" },
                { name: "Prada", amount: "$500", status: "Approved", emp_id: "1", dateSubmitted: new Date('11-2-2018'), description: "shoes, shopping, luxury" },
                { name: "Grubhub", amount: "$1200", status: "Rejected", emp_id: "1", dateSubmitted: new Date('10-28-2018'), description: "food, lunch" },
            ],
            filteredExpenses: [],
            filterText: '',
            status: '',
            search: '',
        }
    },
    methods: {
        getExpenses() {
            // TODO: fetch from Servlet, right now it's just jsonplaceholder
            // fetch(`https://jsonplaceholder.typicode.com/albums`)
            ExpensesRepository.getAll()
            .then(res => res.json())
            .then(expenses => this.expenses = expenses)
            .catch(err => err)
        }
    },
    computed: {
        filteredItems() {
            return this.search.length < 1 ? 
            this.expenses.filter(exp => {
                return exp.status == this.status
            })
            :
            this.expenses.filter(exp => {
                return exp.name.toLowerCase().indexOf(this.search.toLowerCase()) > -1
            }).concat(
                this.expenses.filter(exp => {
                    return exp.description.toLowerCase().indexOf(this.search.toLowerCase()) > - 1
                })
            )
        }
    }
}
</script>
<style>
    .active {
        display: none;
    }
</style>
