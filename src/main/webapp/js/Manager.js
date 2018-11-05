const empDic = {};

window.onload = function() {

  document.addEventListener('onclick', function (event) {

    // If the clicked element doesn't have the right selector, bail
    if (!event.target.matches('.click-me')) return;
  
    // Don't follow the link
    event.preventDefault();
  
    // Log the clicked element in the console
    console.log(event.target);
  
  }, false);
    console.log("manager page loaded.")
    // grab the container in which to render the list of employees for the team
    const employeesContainer = document.querySelector("#employee-list");
    // fetch the list of all employees from DB
    const employees = EmployeeRepository.fetchAll()
        .then(data => {
            return data;
        })
        .then(employees => {
          if (Array.isArray(employees)) {
            // iterate over employee list 
            for (let employee of employees) {
              const empEl = document.createElement("li");
              const badge = document.createElement("span");
              empEl.setAttribute("class", "list-group-item d-flex justify-content-between align-items-center click-me");
              badge.setAttribute("class", "badge badge-primary badge-pill")
              
              // fill element with the employee data using HTML5 data attribute
              for (let key in employee) {
                empEl.setAttribute(`data-${key}`, ""+employee[key]);
              }
              // get the name
              const fullName = employee.firstName + " " + employee.lastName;
              empDic[employee.id] = fullName;
              empEl.appendChild(document.createTextNode(fullName));
              // render the employee
              employeesContainer.appendChild(empEl).addEventListener("onclick", clickActivate.bind(this));
            }
          }
        });

  const expensesContainer = document.querySelector("#expenses-container");
  const expenses = ExpensesRepository.getAll()
        .then(data => {
          // console.log("expenses list got to Manager code................." + JSON.stringify(data))
          return data;
        })
        .then(exps => {
          console.log("exps   " + exps)
          if (Array.isArray(exps)) {
            for (let exp of exps) {
              console.log('expense    ' + exp)
              const li = document.createElement("a");
              li.addEventListener("onclick", clickActivate);
              const card = document.createElement("div");
              const heading = document.createElement("h5");
              const date = document.createElement("small");
              const info = document.createElement("p");
              const employee = document.createElement("h6");
              const moreInfo = document.createElement("small");

              li.setAttribute("class", "list-group-item list-group-item-action flex-column align-items-start");
              card.setAttribute("class", "d-flex w-100 justify-content-between");
              heading.setAttribute("class", "mb-1");
              info.setAttribute("clas", "mb-1");
//            put data in the card for internal use
              for (let key in exp) {
                li.setAttribute(`data-${key}`, ""+exp[key]);
              }

              heading.innerText = exp.title;
              employee.innerText = empDic[exp.employee_id];
              info.innerText = exp.description;
              date.innerText = `${exp.daysSinceRequest} day${exp.daysSinceRequest > 1 ? 's' : ''} ago`;
              moreInfo.innerText = `${exp.status}`;
              card.append(heading, date, employee);
              li.append(card, info, moreInfo);
              expensesContainer.appendChild(li);
            }
          }
        });
}

function clickActivate(e) {
  console.log(e)
  console.log("clicked to activate")
  const prevClicked = document.querySelector(".active");
  prevClicked.removeAttribute("class", "active");
  this.setAttribute("class", "active");
}