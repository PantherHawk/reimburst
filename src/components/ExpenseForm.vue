<template>
    <form @submit.prevent="handleSubmit" class="form">
        <label for="what"><input v-model="what" placeholder="what did you buy"></label>
        <label for="where"><input v-model="where" placeholder="where did you buy it"></label>
        <label for="why"><input v-model="amount" placeholder="how much did you spend"></label>
        <button type="submit">Submit</button>
    </form>
</template>
<script>
export default {
    data() {
        return {
            what: '',
            where: '',
            why: '',
            amount: ''
        }
    },
    methods: {
        handleSubmit() {
            const body = {
                what: this.what,
                where: this.where,
                why: this.why,
                amount: this.amount,
            }
            console.log(JSON.stringify(body))
            // send data to the server
        }
    }
}
</script>
<style>
.form {
    border: 1px solid black;
}
</style>

