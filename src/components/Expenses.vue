<template>
    <div>
        <h2>Employee Expenses page!</h2>
        <expenses-list></expenses-list>
    </div>
</template>
<script>
import ExpensesList from './ExpensesList'
export default {
    // data() {

    // },

    components: {
            'expenses-list': ExpensesList,
        }
}
</script>
