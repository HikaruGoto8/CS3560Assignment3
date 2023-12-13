public class UserVisitor implements VisitorInterface{
    private int userCount = 0;
    public int getTotal()
    {
        return userCount;
    }

    public void setUserCount(int userCount)
    {
        this.userCount = userCount;
    }

    @Override
    public void visit(UserInterface user)
    {
        setUserCount(userCount + 1);
    }

    @Override
    public void visit(UserGroupInterface group)
    {
        setUserCount(userCount);
    }
}
