import java.util.ArrayList;
import java.util.List;

public interface UserGroupInterface {

    public String getID();

    public List<UserInterface> getMembers();

    public List<UserGroupInterface> getSubGroup();

    public void addMember(UserInterface acc);

    public void addGroup(UserGroupInterface group);



    public String toString();
    public void accept(VisitorInterface visitor);
}
