export default expensesMixin = {
    created: function() {
        this.getExpenses()
    },
    methods: {
        getExpenses() {
            // TODO: fetch from Servlet, right now it's just jsonplaceholder
            fetch(`https://jsonplaceholder.typicode.com/albums`)
            .then(res => res.json())
            .then(expenses => this.expenses = expenses)
            .catch(err => console.log(err))
        }
    }
}