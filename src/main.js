import Vue from 'vue'
import App from './components/App.vue'
import VueRouter from 'vue-router'
import Login from './components/Login'
import Manager from './components/Manager'
import Employee from './components/Employee'
import About from './components/About'
import Profile from './components/Profile'
import Expenses from './components/Expenses'
import ExpenseForm from './components/ExpenseForm'
import auth from './auth'

Vue.use(VueRouter);

// require authentication
function requireAuth(to, from, next) {
  if (!auth.loggedIn()) {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
  } else {
    next()
  }
}

const router = new VueRouter({
  mode: 'history',
  base: __dirname,
  routes: [
    { path: '/login', component: Login},
    { path: '/about', component: About },
    { path: '/manager', component: Manager, beforeEnter: requireAuth },
    { path: '/employee', component: Employee, beforeEnter: requireAuth, children: [
      { path: '/profile', component: Profile },
      { path: '/expenses', component: Expenses },
      { path: '/new-expense', component: ExpenseForm }
    ] },
    { path: '/logout', 
      beforeEnter (to, from, next) {
        auth.logout()
        next('/')
      }
    }
  ]
})

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
