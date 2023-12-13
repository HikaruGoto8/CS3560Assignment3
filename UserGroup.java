import java.util.*;
public class UserGroup implements UserGroupInterface {
    private String id;
    private List<UserInterface> members;
    private List<UserGroupInterface> subGroup;

    public UserGroup(String id)
    {
        this.id = id;
        this.members = new ArrayList<>();
        this.subGroup = new ArrayList<>();
    }

    @Override

    public String getID()
    {
        return id;
    }

    public List<UserInterface> getMembers()
    {
        return members;
    }

    public List<UserGroupInterface> getSubGroup()
    {
        return this.subGroup;
    }

    public void addMember(UserInterface acc)
    {
        members.add(acc);
    }

    public void addGroup(UserGroupInterface group)
    {
        subGroup.add(group);
    }

    public String toString()
    {
        return id;
    }
    public void accept(VisitorInterface visitor)
    {
        visitor.visit(this);
        for(UserInterface user : members)
        {
                user.accept(visitor);
        }
        for(UserGroupInterface group : subGroup)
        {
            group.accept(visitor);
        }
    }
}
