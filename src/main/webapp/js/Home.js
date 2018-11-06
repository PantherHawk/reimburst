// const Expenses = require('./ExpensesRepository');
window.onload = function () {
    // add event listeners
    const pendingBtn = document.querySelector(".show-pending");
    document.querySelector("#new-expense").addEventListener("submit", e => submitExpense(e))
    // pendingBtn.addEventListener("click", showPending);
    // renderExpenses();

    // for searching for an expense, ICEBOX
    const expenseDictionary = new Map;

    const expensesList = document.querySelector(".expenses");

    // fetch the expenses
    const data = ExpensesRepository.getAll();
    data.then(renderExpenses);
    console.log('data; ', data)

    // render the expenses by putting each in a list tag and appending it to expensesList
    function renderExpenses(bigFatArray) {
        bigFatArray.forEach(expense => {
            const approvedOrNot = expense.approved ? "Approved" : "Pending";
            const group = document.createElement("a");
            group.setAttribute("id", `id-${expense.id}`);
            // group.setAttribute("class", approvedOrNot)
            group.setAttribute("class", `list-group-item list-group-item-action flex-column align-items-start expense ${approvedOrNot}`);
            const card = document.createElement("div");
            card.setAttribute("class", "d-flex w-100 justify-content-between");
            const heading = document.createElement("h5");
            heading.setAttribute("class", "mb-1");
            heading.innerText = expense.name;
            const status = document.createElement("small");
            status.innerText = approvedOrNot;


            card.appendChild(status);
            card.appendChild(heading);
            group.appendChild(card);

            expensesList.appendChild(group);
        });
    }

    // when you click the pending button, 
    // add event listener to onclick of 'Pending'


    // only display pending 
    
//    fill profile section with sessionStorage info;
    const user = JSON.parse(sessionStorage["user"]);
    const { firstName, lastName, id, email, info, username } = user;
    const name = document.querySelector("#name");
    name.innerText = `${firstName}  ${lastName}`;
//    jQuery(name).fitText(0.38)
    const userName = document.querySelector("#username");
    userName.innerText = `${username}`;
//    $(userName).fitText(0.38)
    const email_address = document.querySelector("#email");
    email_address.innerText = email;
    const about = document.querySelector("#about");
    about.innerText = info;
//    $(about).fitText(0.38)
//    TODO: upload avatar...
    const avatar = document.querySelector("#avatar");

}

function showAll() {
    const expenses = document.querySelectorAll(".expense");
    expenses.forEach(expense => {
        expense.setAttribute("style", "display: show");
    });
}
function showPending() {
    console.log('clicked pending!')
    const expenses = document.querySelectorAll(".expense");
    expenses.forEach(expense => {
        console.log('running in foreach')
        if (!expense.classList.contains("Pending")) {
            expense.setAttribute("style", "display: none;");
        }
    });
}

function showApproved() {
    console.log('clicked pending!')
    const expenses = document.querySelectorAll(".expense");
    expenses.forEach(expense => {
        console.log('running in foreach')
        if (!expense.classList.contains("Approved")) {
            expense.setAttribute("style", "display: none;");
        }
    });
}

function submitExpense(event) {
    event.preventDefault();
    const expenseForWhat = document.querySelector(".new-expense-title").value;
    const howMuch = document.querySelector(".how-much").value;

    console.log(`Expense   [ ${expenseForWhat}  ${howMuch} ]`);

    const toAnimate = document.querySelector(".alert-success");
    toAnimate.classList.remove("not-yet-submitted");

    let fields = document.querySelectorAll('input[type="text"]');
    fields.forEach(field => field.value = '');

    setTimeout(() => toAnimate.setAttribute("class", "not-yet-submitted"), 5000);

}

function logout() {
	console.log("clicked logout ");
	
	EmployeeRepository.logout()
	.then(data => {
		window.location.replace("/");
	})
}