public class GroupVisitor  implements  VisitorInterface{
    private int groupCount = 0;
    public int getTotal()
    {
        return groupCount;
    }

    public void setGroupCount(int userCount)
    {
        this.groupCount = userCount;
    }

    @Override
    public void visit(UserInterface user)
    {
        setGroupCount(groupCount);
    }

    @Override
    public void visit(UserGroupInterface group)
    {
        setGroupCount(groupCount + 1);
    }
}
