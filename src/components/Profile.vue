<template>
<div class="container">
    <h2>Employee Profile page!</h2>

    <div class="jumbotron">
      <p>{{ firstName }}</p>
      <p> {{ lastName }} </p>
      <p>{{ info }}</p>
      <p>{{ email }}</p>
      <p>{{username}}</p>
    </div>
  </div>
</template>

<script>
export default {
  created: function() {
    this.setUp();
  },
  data() {
    return {
      firstNam0: "",
      lastName: "",
      info: "",
      email: "",
      username: ""
    }
  },
  methods: {
    setUp() {
      const { firstName, lastName, info, email, username } = JSON.parse(localStorage.user)
      this.firstName = firstName
      this.lastName = lastName
      this.info = info
      this.email = email
      this.username = username
    }
  }
}
</script>

<style>

</style>
