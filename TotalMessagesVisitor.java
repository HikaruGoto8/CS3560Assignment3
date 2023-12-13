public class TotalMessagesVisitor implements VisitorInterface{
    private int totalMessages = 0;
    @Override
    public void visit(UserInterface user)
    {
        setTotalMessages(getTotal() + user.getTotalMessages());
    }

    @Override
    public void visit(UserGroupInterface group)
    {
        setTotalMessages(getTotal());
    }

    @Override
    public int getTotal()
    {
        return totalMessages;
    }

    public void setTotalMessages(int totalMessages)
    {
        this.totalMessages = totalMessages;
    }

}
