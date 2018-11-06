<template>
    <div>
        <button v-on:click="showAll">All</button>
        <ul id="employees">
            <li v-bind:key="employee.id" v-for="employee of employees">
                <div v-bind:id="employee.id">
                    <input type="radio" v-bind:id="employee.id" v-bind:value="employee.id" v-model="employeeSelected"><label for="employee.id">{{ employee.name }}</label>
                </div>
            </li>
        </ul>
        <ul id="expenses">
            <li v-bind:key="item.id" v-for="item of filteredItems">
                {{ item.name }} - {{ item.amount }} - {{ item.status }}
            </li>
        </ul>
    </div>
</template>
<script>
export default {
    data() {
        return {
            employees: [
                { name: 'Batman', id: 1 },
                { name: 'Robin', id: 2 },
            ],
            expenses: [
                { name: "Uber", amount: "$200", status: "Pending", emp_id: "1", dateSubmitted: new Date('11-1-2018') },
                { name: "Prada", amount: "$500", status: "Approved", emp_id: "2", dateSubmitted: new Date('11-2-2018') },
                { name: "Grubhub", amount: "$1200", status: "Rejected", emp_id: "2", dateSubmitted: new Date('10-28-2018') },
            ],
            employeeSelected: null
        }
    },
    computed: {
        filteredItems() {
            console.log('employeeSelected  ', this.employeeSelected)
            return this.expenses.filter(exp => {
                return exp.emp_id == this.employeeSelected;
            })
        },
        employeeSelect() {
            // console.log("employee id    " + event.target.id || null)
            this.employeeSelected = event
        },
        
    },
    methods: {
        showAll() {
            console.log()
            this.filteredItems = this.expenses
            return this.filteredItems
        }
    }
}
</script>
