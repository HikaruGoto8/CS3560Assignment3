public class PositiveMessVisitor implements VisitorInterface{

    private int totalPositive = 0;

    public void setTotalPositive(int totalPositive)
    {
        this.totalPositive = totalPositive;
    }

    @Override
    public void visit(UserInterface user)
    {
        for(String mess : user.getFeed())
        {
            if(isPositiveMessage(mess))
            {
                setTotalPositive(getTotal() + 1);
            }
        }
    }

    @Override
    public void visit(UserGroupInterface group)
    {
        setTotalPositive(getTotal());
    }

    @Override
    public int getTotal()
    {
        return totalPositive;
    }

    private boolean isPositiveMessage(String mess)
    {
        String[] posWords = {"good", "great", "excellent"};
        for(String word : posWords)
        {
            if(mess.toLowerCase().contains(word))
            {
                return true;
            }
        }
        return false;
    }
}
