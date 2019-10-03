public class SplayTreeNode {
    public int Data;
    public SplayTreeNode LeftChild;
    public SplayTreeNode RightChild;
    public SplayTreeNode Parent;

    SplayTreeNode(int Data)
    {
        this.Data = Data;
        LeftChild = RightChild = Parent = null;
    }
}
