<template>
    <div>
        <nav>
            <ul>
                <li><router-link to="/expenses">Expenses</router-link></li>
                <li><router-link to="/profile">Profile</router-link></li>
                <li><router-link to="/edit">Edit Profile</router-link></li>
                <li>
                    <router-link to="/new-expense">New Expense</router-link>
                </li>

            </ul>
        </nav>
        <h2>Employee Homepage</h2>
        <p>Made it to employee page!</p>
        <router-view></router-view>
    </div>
</template>