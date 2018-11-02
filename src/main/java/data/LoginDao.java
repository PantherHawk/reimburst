package data;

import beans.Login;

public interface LoginDao {
	Login login(String username, String password);

}
