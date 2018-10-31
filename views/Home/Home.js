// const Expenses = require('./ExpensesRepository');
window.onload = function () {
    // add event listeners
    const pendingBtn = document.querySelector(".show-pending");
    // pendingBtn.addEventListener("click", showPending);
    console.log('pending button: ', pendingBtn)
    // renderExpenses();
    
    const expensesList = document.querySelector(".expenses");

    // fetch the expenses
    const data = ExpensesRepository.getAll();
    data.then(renderExpenses);
    console.log('data; ', data)

    // render the expenses by putting each in a list tag and appending it to expensesList
    function renderExpenses(bigFatArray)  {
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
                console.log('expenses in showPending: ------ ', expenses)
                expenses.forEach(expense => {
                    console.log('running in foreach')
                    if (!expense.classList.contains("Pending")) {
                        expense.setAttribute("style", "display: none;");
                    }
                });
            }