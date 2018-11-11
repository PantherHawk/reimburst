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
import EditProfileForm from './components/EditProfileForm'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import auth from './auth'
import API from './util/API'

Vue.use(BootstrapVue)
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

function requireManagerAuth(to, from, next) {
  if (!auth.manager() || !auth.loggedIn()) {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
  } else {
    next()
  }
}

function requireEmployeeAuth(to, from , next) {
  if (auth.manager() || !auth.loggedIn()) {
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
    { path: '/manager', component: Manager, beforeEnter: requireManagerAuth },
    { path: '/employee', component: Employee, beforeEnter: requireEmployeeAuth, children: [
      { path: '/profile', component: Profile },
      { path: '/edit', component: EditProfileForm },
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
