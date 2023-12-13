import com.sun.source.tree.Tree;

import javax.swing.tree.DefaultTreeModel;

public class TreeObserver implements ObserverInterface {
    private DefaultTreeModel treeModel;
    public TreeObserver(DefaultTreeModel treeModel)
    {
        this.treeModel = treeModel;
    }
    public void update()
    {
        treeModel.reload();
    }
}
