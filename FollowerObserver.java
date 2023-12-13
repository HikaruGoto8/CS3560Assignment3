public class FollowerObserver implements ObserverInterface {
    private UserInterface user;
    private UserInterface following;
    private String message;

    public FollowerObserver(UserInterface user, UserInterface following)
    {
        this.user = user;
        this.message = null;
        this.following = following;
    }

    public FollowerObserver(UserInterface user, String message)
    {
        this.user = user;
        this.message = message;
        this.following = null;
    }
    public void update()
    {
        if(this.message == null)
        {
            following.getFollowers().add(user);
        }
        else {
            for(UserInterface follower : user.getFollowers())
            {
                follower.getFeedListModel().addElement("- " + user.getID() + " : " + message);
            }
        }
    }
}
