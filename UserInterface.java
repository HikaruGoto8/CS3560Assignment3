import javax.swing.*;
import java.util.List;

public interface UserInterface {
    public String getID();

    public List<UserInterface> getFollowers();

    public List<UserInterface> getFollowing();

    public List<String> getFeed();

    public void follow(UserInterface acc);

    public void postMessage(String mess);

    public DefaultListModel<String> getFeedListModel();

    public UserInterface getUser(String id);
    public long getTimeCreated();
    public long getLastUpdated();

    public void accept(VisitorInterface visitor);
    public int getTotalMessages();
}
