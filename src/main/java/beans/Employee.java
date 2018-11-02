package beans;

public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private int hasManager;
	private String email;
	private int team_id;
	private String info;
	
	public Employee() {
		super();
	}
	public Employee(String username, String password) {
		super();
		this.username	= username;
		this.password	= password;
	}
	public Employee(int id, String firstName, String lastName, String username, int hasManager,
			String email, int team_id, String info) {
		super();
		this.id			= id;
		this.firstName	= firstName;
		this.lastName	= lastName;
		this.username	= username;
		this.hasManager	= hasManager;
		this.email		= email;
		this.info = info;
		this.team_id = team_id;
	}
	public Employee(String firstName, String lastName, String username, String pass, int hasManager,
			String email, int team_id, String info) {
		super();
		this.firstName	= firstName;
		this.lastName	= lastName;
		this.username	= username;
		this.password = pass;
		this.hasManager	= hasManager;
		this.email		= email;
		this.info = info;
		this.team_id = team_id;
	}
	public int getTeam_id() {
		return team_id;
	}
	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getHasManager() {
		return hasManager;
	}
	public void setHasManager(int hasManager) {
		this.hasManager = hasManager;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result	= prime * result + ((password == null) ? 0 : password.hashCode());
		result	= prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		
		return "id: " + this.id
				+ "email: " + this.email 
				+ "username: " + this.username
				+ "first name: " + this.firstName
				+ "last name: " + this.lastName 
				+ "has a manager: " + (this.hasManager > 0)
				+ "info: " + this.info;
	}
	
	
}
