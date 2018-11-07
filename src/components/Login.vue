<template>
    <div>

        <h2>Login</h2>
        <p v-if="$route.query.redirect">
            You need to login first.
        </p>
        <form @submit.prevent="login">
            <label><input v-model="username" placeholder="username"></label>
            <label><input v-model="password" type="password" placeholder="password"></label>
            <button type="submit">login</button>
            <p v-if="error" class="error">Bad login information</p>
        </form>

    </div>
</template>

<script>
import auth from '../auth'

export default {
    data() {
        return {
            username: 'martian_manhunter',
            password: 'password',
            error: false
        }
    },
    methods: {
        login() {
            auth.login(this.username, this.password, loggedIn => {
                // console.log('loggedin: ', loggedIn)s
                if (!loggedIn) {
                    this.error = true
                } else {
                    this.$router.replace(this.$route.query.redirect || '/')
                }
            })
        }
    }
}
</script>

<style>
    .error {
        color: red;
    }
</style>
