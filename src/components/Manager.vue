<template>
    <div>
        <button v-on:click="showAll">All</button>
        <ul id="employees">
            <li v-bind:key="employee.id" v-for="employee of employees">
                <div v-bind:id="employee.id">
                    {{ employee.firstName + " " +employee.lastName }} -- {{ employee.info }} -- {{ employee.username }}
                    <input type="radio" v-bind:id="employee.id" v-bind:value="employee.id" v-model="employeeSelected"><label for="employee.id">{{ employee.username }}</label>
                </div>
            </li>
        </ul>
        <!-- <ul id="expenses">
            <li v-bind:key="item.id" v-for="item of filteredItems">
                <button v-on:click="approveOrReject">Approve</button>
                <button v-on:click="approveOrReject">Reject</button>
                {{ item.title }} - {{ item.id }} - {{ item.userId }}
            </li>
        </ul> -->
        <expense-list v-bind:statusToggler="approveOrReject" v-bind:expenses="filteredItems">Expense List</expense-list>
    </div>
</template>
<script>
import ExpensesRepository from '../util/ExpensesRepository'
import EmployeeRepository from '../util/EmployeeRepository';
import ExpensesList from './ExpensesList';

export default {
    components: {
        'expense-list': ExpensesList,
    },
    
    created: function() {
        this.getExpenses()
        this.getEmployees()
    },
    data() {
        return {
            employees: [
                { name: 'Batman', id: 1 },
                { name: 'Robin', id: 2 },
            ],
            allExpenses: [
                { name: "Uber", amount: "$200", status: "Pending", emp_id: "1", dateSubmitted: new Date('11-1-2018') },
                { name: "Prada", amount: "$500", status: "Approved", emp_id: "2", dateSubmitted: new Date('11-2-2018') },
                { name: "Grubhub", amount: "$1200", status: "Rejected", emp_id: "2", dateSubmitted: new Date('10-28-2018') },
            ],
            employeeSelected: null,
        }
    },
    computed: {
        filteredItems() {
            if (!this.employeeSelected) {
                return this.allExpenses
            }
            console.log('employeeSelected  ', this.employeeSelected)
            return this.allExpenses.filter(exp => {
                return exp.employee_id == this.employeeSelected;
            })
        },
        employeeSelect() {
            // console.log("employee id    " + event.target.id || null)
            this.employeeSelected = event
        },
        
    },
    methods: {
        showAll() {
            this.employeeSelected = null;

        },
        getExpenses() {
            // TODO: fetch from Servlet, right now it's just jsonplaceholder
             ExpensesRepository.getAll()
            .then(expenses => {
                console.log("Expenses in vue:   " + JSON.stringify(expenses))
                this.allExpenses = expenses
                })
            .catch(err => err)
        },
        getEmployees() {
            // TODO: fetch from Servlet, right now it's just jsonplaceholder
            EmployeeRepository.fetchAll()
            .then(result => {
                console.log('uers from db    ' + JSON.stringify(result))
                return result
            })
            .then(employees => this.employees = employees)
            .catch(err => console.log(err))
        },
        approveOrReject(expense, $event) {

            console.log("toggled status:   ", $event.target.innerText)
            console.log("which expense do we want to approve ------> " + JSON.stringify(expense))
            // TODO: animations
            expense.status = $event.target.innerText
            // TODO: send update to db
            ExpensesRepository.approveExpense(expense.status, expense.id)
            .then(result => {
                console.log('result from approve expense   ------->   ' + result)
            })
            // this.decision = $event.target.innerText;

        }
    }
}
</script>
