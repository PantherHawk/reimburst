<template>
<div>
        <form @submit.prevent="handleSubmit(e)" class="form">
             <p v-if="errors.length">
    <b>Please correct the following error(s):</b>
    <ul>
      <li v-for="error in errors" :key="error">{{ error }}</li>
    </ul>
  </p>
            <label for="what"><input v-model="what" placeholder="what did you buy" v-on:input="showChange($event)"></label>
            <label for="where"><input v-model="where" placeholder="where did you buy it" v-on:input="showChange($event)"></label>
            <label for="amount"><input v-model="amount" placeholder="how much did you spend" v-on:input="showChange($event)"></label>
            <label for="why"><input v-model="why" placeholder="what was the reason for this expense" v-on:input="showChange($event)"></label>
            <button :disabled="errors.length > 0" type="submit">Submit</button>
        </form>
        <div v-bind:class="[submitSuccess ? successClass : 'not-yet-submitted']" role="alert">
            <h4 class="alert-heading">Well done!</h4>
            <p>Aww yeah, you successfully read this important alert message. This example text is going to run a
                bit longer so that you can see how spacing within an alert works with this kind of content.</p>
            <hr>
            <p class="mb-0">Whenever you need to, be sure to use margin utilities to keep things nice and tidy.</p>
        </div>
    </div>
</template>
<script>
import ExpensesRepository from '../util/ExpensesRepository'
export default {
    data() {
        return {
            what: '',
            where: '',
            why: '',
            amount: '',
            submitSuccess: false,
            successClass: ['submitted', 'alert', 'alert-success', 'submitted', 'not-yet-submitted'],
            errors: [],
        }
    },
    methods: {
        checkForm(e) {
            if (Number.isFinite(parseInt(this.amount)) && this.what && this.why) {
                return true
            }
            if (!Number.isFinite(this.amount)) {
                this.errors.push('Amount must be a number')
            }
            if (!this.amount) {
                this.errors.push('Amount required')
            }
            if (!this.why) {
                this.errors.push('Please provide a description')
            }
            if (!this.what) {
                this.errors.push('Name of expense required')
            }

            e.preventDefault()
            setTimeout(() => this.errors = [], 3000)
            return false;
        },
        handleSubmit(e) {
            // format body for Expense constructor on back end
            // amount
            // name
            // description
            // employee id
            // team id
            if (this.checkForm(e)) {

                const payload = {
                    amount: this.amount,
                    name: this.what,
                    description: this.why,
                }
                console.log(JSON.stringify(payload))
                // send data to the server
                ExpensesRepository.addExpense(payload)
                .then(result => {
                    console.log("result of adding expense  " + result);
                    this.submitSuccess = true;
                    console.log("submit success?", this.submitSuccess)
                    setTimeout(() => this.submitSuccess = !this.submitSuccess, 5000)
                });
            }
        },
        showChange($event) {
            console.log('change   ----- ', $event)
            this.errors = []
        }
    }
}
</script>
<style>
.form {
    border: 1px solid black;
}

.not-yet-submitted {
      display: none;
}
.submitted {
    -webkit-animation: bounceInUp 3s;
    animation: bounceInUp 3s;
}
</style>

