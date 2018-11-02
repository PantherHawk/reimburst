package beans;

import java.sql.Timestamp;

public class Expense {
	private int id;
	private double amount;
	private String name;
	private String description;
	private String status;
	private Timestamp dateSubmitted;
	private Timestamp dateHandled;
	private int employeeId;
	private int teamId;
	
	
//	to put things in the db
	public Expense(double amount, String name, String description, int employeeId, int teamId) {
		super();
		this.amount			= amount;
		this.name			= name;
		this.description	= description;
		this.employeeId		= employeeId;
		this.teamId			= teamId;
	}
//	to make data objects from the db
	public Expense(int id, double amount, String name, String description, String status, Timestamp dateSubmitted,
			Timestamp dateHandled, int employeeId, int teamId) {
		super();
		this.id 			= id;
		this.amount			= amount;
		this.name			= name;
		this.description	= description;
		this.status			= status;
		this.dateSubmitted	= dateSubmitted;
		this.dateHandled	= dateHandled;
		this.employeeId		= employeeId;
		this.teamId			= teamId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getDateSubmitted() {
		return dateSubmitted;
	}
	public void setDateSubmitted(Timestamp dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}
	public Timestamp getDateHandled() {
		return dateHandled;
	}
	public void setDateHandled(Timestamp dateHandled) {
		this.dateHandled = dateHandled;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	@Override
	public String toString() {
		return "Expense [id=" + id + ", amount=" + amount + ", name=" + name + ", description=" + description
				+ ", status=" + status + ", dateSubmitted=" + dateSubmitted + ", dateHandled=" + dateHandled
				+ ", employeeId=" + employeeId + ", teamId=" + teamId + "]";
	}
	
	
}
