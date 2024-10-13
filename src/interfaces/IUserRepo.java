package interfaces;
import java.lang.*;

import entity.*;

public interface IUserRepo {
    public void insertInDB(User c);
	public void deleteFromDB(String id);
	public void updateInDB(User c);
	public User searchUser(String id);
	public String[][] getAllUser();
}
