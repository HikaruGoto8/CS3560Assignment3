import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageApp {
    private final UserGroupInterface root;
    private final DefaultMutableTreeNode rootNode;
    private final DefaultTreeModel treeModel;
    private final List<ObserverInterface> obs;

    private static MessageApp app;


    public MessageApp()
    {
        this.root = new UserGroup("Root");
        this.rootNode = new DefaultMutableTreeNode(root);
        this.treeModel = new DefaultTreeModel(rootNode);
        this.obs = new ArrayList<>();
        obs.add(new TreeObserver(treeModel));
    }

    public static MessageApp getInstance()
    {
        if(app == null)
        {
            app = new MessageApp();
        }
        return app;
    }
    public void updateAddObs(ObserverInterface obser)
    {
        obs.add(obser);
    }

    public void notifyObservers()
    {
        for(ObserverInterface observer : obs)
        {
            observer.update();
        }
    }

    public void addUser(UserInterface acc, UserGroupInterface group, DefaultMutableTreeNode parent)
    {
        group.addMember(acc);
        DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(acc.getID());
        parent.add(userNode);
        notifyObservers();
    }

    public void addGroup(UserGroupInterface group, UserGroupInterface parentG, DefaultMutableTreeNode parentN)
    {
        parentG.addGroup(group);
        DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group.getID());
        parentN.add(groupNode);
        notifyObservers();
    }

    public void getID(Set<String> groupID, Set<String> userID, UserGroupInterface parentG)
    {
        groupID.add(parentG.getID());
        for(UserInterface user : parentG.getMembers())
        {
            userID.add(user.getID());
        }
        for(UserGroupInterface group : root.getSubGroup())
        {
            groupID.add(group.getID());
        }
    }

    public void adminCP()
    {
        // main frame
        JFrame frame = new JFrame("Admin Control Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // all components in Admin Control Panel
        JButton addUserButton = new JButton("Add User");

        JButton checkIDButton = new JButton("ID Check");

        JButton lastUpdatedUserButton = new JButton("Last Updated User");

        JTextField userIDTF = new JTextField(15);

        JButton addGroupButton = new JButton("Add Group");

        JTextField groupIDTF = new JTextField(15);

        JButton userViewButton = new JButton("Open User View");

        JTextField viewUserIDTF = new JTextField(15);

        JButton showTotalUsersButton = new JButton("Total Users");

        JButton showTotalGroupsButton = new JButton("Total Groups");

        JButton showTotalMessagesButton = new JButton("Total Messages");

        JButton showPositiveMessagesButton = new JButton("Positive Messages");

        // tree of groups and users
        JTree userTree = new JTree(treeModel);
        userTree.setBackground(Color.lightGray);
        JScrollPane treeScrollPane = new JScrollPane(userTree);



        // Configure layout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.gray);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        panel.add(showTotalUsersButton, constraints);
        constraints.gridx++;

        panel.add(showTotalGroupsButton, constraints);

        constraints.gridx = 0;
        constraints.gridy++;

        panel.add(showTotalMessagesButton, constraints);
        constraints.gridx++;

        panel.add(showPositiveMessagesButton, constraints);
        constraints.gridy++;

        constraints.gridx = 0;
        panel.add(userIDTF, constraints);
        constraints.gridx++;

        panel.add(addUserButton, constraints);

        constraints.gridx = 0;
        constraints.gridy++;



        panel.add(groupIDTF, constraints);
        constraints.gridx++;

        panel.add(addGroupButton, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridy++;

        panel.add(viewUserIDTF, constraints);
        constraints.gridx++;

        panel.add(userViewButton, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridy++;

        panel.add(checkIDButton, constraints);
        constraints.gridx++;
        panel.add(lastUpdatedUserButton, constraints);

        constraints.gridx = 0;
        constraints.gridy++;

        constraints.gridwidth = 3;

        constraints.fill = GridBagConstraints.BOTH;

        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        panel.add(treeScrollPane, constraints);

        addUserButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                String userId = userIDTF.getText().trim();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) userTree.getSelectionPath().getLastPathComponent();

                if (!userId.isEmpty()) {

                    UserInterface newUser = new User(userId);

                    addUser(newUser, root, selectedNode);

                    userIDTF.setText("");

                }

            }

        });


        addGroupButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                String groupId = groupIDTF.getText().trim();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) userTree.getSelectionPath().getLastPathComponent();

                if (!groupId.isEmpty()) {

                    UserGroupInterface newGroup = new UserGroup(groupId);

                    addGroup(newGroup, root, selectedNode);

                    groupIDTF.setText("");

                }

            }

        });

        userViewButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {


                String userID = viewUserIDTF.getText().trim();

                if (!userID.isEmpty()) {
                    displayUserView(findUser(userID, root));

                    groupIDTF.setText("");

                }

            }

        });

        showTotalUsersButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {
                VisitorInterface visitor = new UserVisitor();
                root.accept(visitor);

                JOptionPane.showMessageDialog(frame, "Total Users: " + visitor.getTotal());

            }

        });


        showTotalGroupsButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {
                VisitorInterface visitor = new GroupVisitor();
                root.accept(visitor);

                JOptionPane.showMessageDialog(frame, "Total Groups: " + visitor.getTotal());

            }

        });


        showTotalMessagesButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                VisitorInterface visitor = new TotalMessagesVisitor();
                root.accept(visitor);

                JOptionPane.showMessageDialog(frame, "Total Messages: " + visitor.getTotal());

            }

        });


        showPositiveMessagesButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                VisitorInterface visitorP = new PositiveMessVisitor();
                root.accept(visitorP);
                VisitorInterface visitorT = new TotalMessagesVisitor();
                root.accept(visitorT);

                double percentage = (double) visitorP.getTotal() / visitorT.getTotal() * 100;

                JOptionPane.showMessageDialog(frame, "Positive Messages: " + String.format("%.2f", percentage) + "%");

            }

        });

        checkIDButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {
                boolean hasSpace = false;
                Set<String> groupID = new HashSet<String>();
                Set<String> userID = new HashSet<String>();

                VisitorInterface visitorG = new GroupVisitor();
                root.accept(visitorG);

                VisitorInterface visitorU = new UserVisitor();
                root.accept(visitorU);

                getID(groupID, userID, root);

                for(String id : groupID)
                {
                    if(id.contains(" "))
                        hasSpace = true;
                }
                for(String id : userID)
                {
                    if(id.contains(" "))
                        hasSpace = true;
                }

                if(hasSpace || groupID.size() < visitorG.getTotal() || userID.size() < visitorU.getTotal())
                {
                    JOptionPane.showMessageDialog(frame, "Invalid IDs");
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Valid IDs");
                }

            }

        });

        lastUpdatedUserButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                String userID = root.getMembers().get(0).getID();
                long lastUpdated = root.getMembers().get(0).getLastUpdated();

                for(UserInterface user : root.getMembers())
                {
                    if(user.getLastUpdated() > lastUpdated)
                    {
                        lastUpdated = user.getLastUpdated();
                        userID = user.getID();
                    }
                }


                JOptionPane.showMessageDialog(frame, "Last Updated User: " + userID);

            }

        });

        frame.add(panel);

        frame.pack();

        frame.setVisible(true);
    }


    public void displayUserView(UserInterface acc) {

        JFrame frame = new JFrame( "User Created: " + acc.getTimeCreated() + ", User View: " + acc.getID());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


// Create UI components

        DefaultListModel<String> followingListModel = new DefaultListModel<>();
        for(UserInterface account : acc.getFollowing())
        {
            followingListModel.addElement(account.getID());
        }

        JList<String> followingList = new JList<>(followingListModel);

        JScrollPane followingScrollPane = new JScrollPane(followingList);


        DefaultListModel<String> feedListModel = acc.getFeedListModel();


        JList<String> feedList = new JList<>(feedListModel);

        JScrollPane newsFeedScrollPane = new JScrollPane(feedList);


        JTextField followUserIDTF = new JTextField(15);

        JButton followUserButton = new JButton("Follow");

        JTextField messageTF = new JTextField(15);

        JButton postMessageButton = new JButton("Post Tweet");


// Configure layout

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;

        constraints.gridy = 0;

        constraints.anchor = GridBagConstraints.WEST;

        constraints.insets = new Insets(5, 5, 5, 5);


        panel.add(new JLabel("News Feed: "), constraints);

        constraints.gridx++;

        constraints.gridheight = 3;

        constraints.fill = GridBagConstraints.BOTH;

        constraints.weightx = 0.5;

        constraints.weighty = 0.5;

        panel.add(newsFeedScrollPane, constraints);


        constraints.gridx = 2;

        constraints.gridy = 0;

        constraints.gridheight = 1;

        constraints.fill = GridBagConstraints.NONE;

        constraints.weightx = 0;

        constraints.weighty = 0;

        panel.add(new JLabel("Follow User: "), constraints);

        constraints.gridx++;

        panel.add(followUserIDTF, constraints);

        constraints.gridx++;

        panel.add(followUserButton, constraints);


        constraints.gridx = 2;

        constraints.gridy = 1;

        /**/
        panel.add(new JLabel("Following: "), constraints);

        constraints.gridx++;

        constraints.gridwidth = 2;

        constraints.fill = GridBagConstraints.BOTH;

        /**/
        panel.add(followingScrollPane, constraints);


        constraints.gridx = 2;

        constraints.gridy = 2;

        constraints.gridwidth = 1;

        constraints.fill = GridBagConstraints.NONE;

        panel.add(new JLabel("Tweet: "), constraints);

        constraints.gridx++;

        panel.add(messageTF, constraints);

        constraints.gridx++;

        panel.add(postMessageButton, constraints);


// Add event listeners

        followUserButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                String userId = followUserIDTF.getText().trim();

                if (!userId.isEmpty())
                {

                    UserGroupInterface group = root;

                    UserInterface followUser = findUser(userId, group);

                    if (followUser != null && !followUser.equals(acc))
                    {

                        acc.follow(followUser);
                        followingListModel.addElement(followUser.getID());
                        ObserverInterface followerObs = new FollowerObserver(acc, followUser);
                        followerObs.update();


                        notifyObservers();

                        followUserIDTF.setText("");

                    }

                }

            }

        });


        postMessageButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                String mess = messageTF.getText().trim();

                if (!mess.isEmpty())
                {
                    acc.postMessage(mess);
                    ObserverInterface followerObs = new FollowerObserver(acc, mess);
                    followerObs.update();


                    messageTF.setText("");

                }

            }

        });


        frame.add(panel);

        frame.pack();

        frame.setVisible(true);

    }


    private UserInterface findUser(String userID, UserGroupInterface group) {

        for (UserInterface user : group.getMembers()) {

            if (user.getID().equals(userID)) {

                return user;

            }

        }

        for (UserGroupInterface subGroup : group.getSubGroup()) {

            UserInterface user = findUser(userID, subGroup);

            if (user != null) {

                return user;

            }

        }
        return null;
    }

}
