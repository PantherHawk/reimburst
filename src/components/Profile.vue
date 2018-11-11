<template>
<div>
    <h2>Employee Profile page!</h2>
  </div>
</template>

<script>
export default {
  data() {
    return {
      firstName: localStorage.user.firstName,
      lastName: localStorage.user.lastName,
      info: localStorage.user.info,
      email: localStorage.user.email
    }
  }
}
</script>

<style>

</style>
