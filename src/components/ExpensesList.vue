<template>
    <div>
        <!-- /**
 * 
 * {"id":22,
 * "title":"Uber",
 * "status":"Rejected",
 * "amount":25,"daysSinceRequest":4,
 * "dateHandled":1541454059475,
 * "description":"Crosstown traffic",
 * "employee_id":28,"team_id":4}
 */ -->
        <ul id="list-group">
            <li v-on:click="toggleCard(exp)" v-for="exp in filteredItems" :key="exp.id" class="list-group-item d-flex justify-content-between align-items-center">
            <div v-if="isManager">
              <button v-on:click.stop.prevent="statusToggler(exp, $event)">Approve</button>
              <button v-on:click.stop.prevent="statusToggler(exp, $event)">Reject</button>

            </div>
                {{ exp.title }} 
                <span class="badge badge-primary badge-pill">${{ exp.amount }}</span>
                <small> {{ exp.daysSinceRequest }} day{{ exp.daysSinceRequest > 1 ? 's' : '' }} ago </small>
                <div
                    v-bind:class="[ exp.active ? 'show' : 'hide' ]"
                >
                <div class="d-flex w-100 justify-content-between">
                    {{ exp.status }}
                </div>
                <p>
                    {{ exp.description }}
                </p>
                </div>
            </li>
        </ul>
        <!-- <h4>Pending list</h4> -->
        <!-- <ul id="expenses-pending">
            <li v-for="item of expenses" v-if="item.status == 'Pending'">
                {{ item.name }} - {{ item.amount }} - {{ item.status }}
            </li>
        </ul> -->
        <!-- <h3>Filtered List</h3>
        <ul>
            <li v-for="item of filteredItems" :key="item.id">
                {{ item.name }} - {{ item.amount }} - {{ item.status }}
            </li>
        </ul> -->
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
    props: ['expenses', 'statusToggler'],
    created: function() {
        console.log('created ExpensesList component!   ')
        console.log('this.expenses: ', this.expenses)

        // this.getExpenses()
    },
    data() {
        return {
            expenses: this.expenses,
            // expenses: expenseList,
            // expenses: [
            //     { name: "Uber", amount: "$200", status: "Pending", emp_id: "1", dateSubmitted: new Date('11-1-2018'), description: "traffic, cars, transportaion" },
            //     { name: "Prada", amount: "$500", status: "Approved", emp_id: "1", dateSubmitted: new Date('11-2-2018'), description: "shoes, shopping, luxury" },
            //     { name: "Grubhub", amount: "$1200", status: "Rejected", emp_id: "1", dateSubmitted: new Date('10-28-2018'), description: "food, lunch" },
            // ],
            filteredExpenses: this.expenses,
            filterText: '',
            status: '',
            search: '',
            isActive: false,
            activeStyle: {
                display: 'block',
            },
            isManager: localStorage.manager
        }
    },
    methods: {
        // commenting out to receive list of expenses via props from Manager or Employee vue
        // getExpenses() {
        //     // TODO: fetch from Servlet, right now it's just jsonplaceholder
        //     // fetch(`https://jsonplaceholder.typicode.com/albums`)
        //     ExpensesRepository.getAll()
        //     .then(expenses => {
        //         console.log("Expenses in vue:   " + JSON.stringify(expenses))
        //         this.expenses = expenses
        //         })
        //     .catch(err => err)
        // },
        toggleCard(exp) {
            console.log("clicked to toggle card")
            console.log(exp)
            exp.show = !!exp.show
            exp.active = !exp.active
            // NEW PLAN:
            // on click, add to a list
                // list[id] => element
            // set the style of all members in the list to display
            // click again, remove the element from the list

        }
    },
    computed: {
        filteredItems() {
            if (!this.status) {
                return this.expenses
            }
            return this.search.length < 1 ? 
            this.expenses.filter(exp => {
                return exp.status == this.status
            })
            :
            this.expenses.filter(exp => {
                return exp.title.toLowerCase().indexOf(this.search.toLowerCase()) > -1
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
    .show {
        display: inline;
        background-color: blue;
    }
    .hide {
        display: none
    }
</style>
