<template>
<div>
    <form action="" @submit.prevent="handleSubmit($event)">
        <label for="edit-firstname">First Name:</label>
        <input v-model="firstName" placeholder="firstName" id="editFirstname" type="text" >
        <label for="edit-lastname">Last Name:</label>
        <input v-model="lastName" placeholder="lastName" id="editLastname" type="text">
        <label for="edit-username">Username:</label>
        <input v-model="username" placeholder="username" id="editUsername" type="text">
        <label for="edit-email">Email:</label>
        <input v-model="email" placeholder="email" id="editEmail" type="text">
        <label for="edit-info">Info:</label>
        <input v-model="info" placeholder="info" id="editInfo" type="text">
        <button type="submit" id="submitEditProfile">Save</button>
    </form>
    <a @click="$router.go(-1)">Back</a>
</div>
</template>

<script>
import EmployeeRepository from '../util/EmployeeRepository'

export default {
    created: function() {
       this.setUp()
    },
    data() {
        return {
            username: "",
            email: "",
            info: "",
            firstName: "",
            lastName: "",
        }
    },
    methods: {
        setUp() {
            const user = JSON.parse(localStorage.user)
            // ({ lastName, username, password, email, info } = user)
            console.log('setting up ...' );
             this.username = user.username
        this.email = user.email
        this.info = user.info
        this.firstName = user.firstName
        this.lastName = user.lastName
        },
        handleSubmit($event) {
            console.log('clicked submit')
            $event.preventDefault()
            const { firstName, lastName, username, password, email, info, id } = JSON.parse(localStorage.user)
            // check for user profile changes
            for (let key in localStorage.user) {
                if (
                    firstName.valueOf === this.firstName &&
                    lastName.valueOf === this.lastName &&
                    username.valueOf === this.username &&
                    email.valueOf === this.email &&
                    info.valueOf === this.info       
                ) {

                    console.log("No change made. Sending nothing to the database.")
                    // handle redirect
                    return
                }
            }
            const body = {
                id: id,
                firstName: firstName.valueOf === this.firstName ? firstName : this.firstName,
                lastName: lastName.valueOf === this.lastName ? lastName : this.lastName, 
                username: username.valueOf === this.username ? username : this.username,
                email: email.valueOf === this.email ? email : this.email,
                info: info.valueOf === this.info ? info : this.info,
            }

            // make put to /employees
            console.log('firing off put request with  ' + body)

            EmployeeRepository.editProfile(body)

        }
    }
}
</script>

<style>

</style>
