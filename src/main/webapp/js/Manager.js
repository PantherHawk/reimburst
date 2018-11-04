window.onload = function() {
    console.log("manager page loaded.")
    // grab the container in which to render the list of employees for the team
    const employeesContainer = document.querySelector("#employee-list");
    // fetch the list of all employees from DB
    const employees = EmployeeRepository.fetchAll()
        .then(data => {
            console.log("employee list got to Manager code................" + JSON.stringify(data));
            return data;
        })
        .then(employees => {
          console.log("employee list    " + employees )
          if (Array.isArray(employees)) {
            // iterate over employee list 
            for (let employee of employees) {
              const empEl = document.createElement("li");
              const badge = document.createElement("span");
              empEl.setAttribute("class", "list-group-item d-flex justify-content-between align-items-center");
              badge.setAttribute("class", "badge badge-primary badge-pill")
      
              // fill element with the employee data using HTML5 data attribute
              for (let key in employee) {
                empEl.setAttribute(`data-${key}`, ""+employee[key]);
              }
              // get the name
              const fullName = employee.firstName + " " + employee.lastName;
              empEl.appendChild(document.createTextNode(fullName));
              console.log("employee li to append ---------->" + empEl)
              // render the employee
              employeesContainer.appendChild(empEl);
            }
          }
        });
}