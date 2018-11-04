window.onload = function() {
    console.log("manager page loaded.")
    // grab the container in which to render the list of employees for the team
    const employeesContainer = document.querySelector("#employee-list");
    // fetch the list of all employees from DB
    const employees = EmployeeRepository.fetchAll()
        .then(employees => {
            console.log("employee list got to Manager code................" + employees)
        })
    // iterate over employee list 
        // render the employee
}