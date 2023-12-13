

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class User implements UserInterface {
    private String id;
    private List<UserInterface> followers;
    private List<UserInterface> following;
    private List<String> feed;
    private long creationTime;
    private long lastUpdateTime;
    DefaultListModel<String> feedListModel;

    private int totalMessages;

    public User(String id)
    {
        this.id = id;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.feed = new ArrayList<>();
        this.feedListModel = new DefaultListModel<>();
        this.totalMessages = 0;
        this.creationTime = System.currentTimeMillis();
        this.lastUpdateTime = this.creationTime;
    }

    public String getID()
    {
        return id;
    }

    public List<UserInterface> getFollowers()
    {
        return followers;
    }

    public List<UserInterface> getFollowing()
    {
        return following;
    }
    public long getTimeCreated()
    {
        return creationTime;
    }

    public long getLastUpdated()
    {
        return lastUpdateTime;
    }

    public List<String> getFeed()
    {
        return feed;
    }
    public int getTotalMessages()
    {
        return totalMessages;
    }

    public void follow(UserInterface acc)
    {
        following.add(acc);
    }

    public void postMessage(String mess)
    {
        lastUpdateTime = System.currentTimeMillis();
        feed.add(mess);
        feedListModel.addElement(lastUpdateTime + " - " + getID() + " : " + mess);
        totalMessages++;
    }

    public  DefaultListModel<String> getFeedListModel()
    {
        return feedListModel;
    }


    public UserInterface getUser(String id)
    {
        if(id.equals(this.id))
        {
            return this;
        }
        return null;
    }

    public void accept(VisitorInterface visitor)
    {
        visitor.visit(this);
    }
}
