import java.util.Scanner;

public class SplayTreeTopDown {
    SplayTreeNode Current;
    SplayTreeNode GrandParent;
    SplayTreeNode Parent;
    SplayTreeNode Root;


    SplayTreeTopDown()
    {
        Current = GrandParent = Parent = Root = null;
    }

/**
 * Left Rotate is called as Zag
 */
    void LeftRotate(SplayTreeNode Current, SplayTreeNode Parent)
            {
            if(Parent != Root) {
            if (Parent.Parent.LeftChild == Parent) {
            Parent.Parent.LeftChild = Current;
            } else if(Parent.Parent.RightChild == Parent){
            Parent.Parent.RightChild = Current;
            }
            Current.Parent = Parent.Parent;
            }
            else
            {
            Current.Parent = null;
            this.Root = Current;
            }
            Parent.Parent = Current;
            if(Current.LeftChild != null) {
            Current.LeftChild.Parent = Parent;
            }
            Parent.RightChild = Current.LeftChild;
            Current.LeftChild = Parent;
            }

            /**
             * Right Rotate is called as Zig
             */
            void RightRotate(SplayTreeNode Current, SplayTreeNode Parent)
            {
            if(Parent != Root) {
            if(Parent.Parent.LeftChild != null && Parent.Parent.LeftChild == Parent){
            Parent.Parent.LeftChild = Current;
            }
            else if(Parent.Parent.RightChild == Parent)
            {
            Parent.Parent.RightChild = Current;
            }
            Current.Parent = Parent.Parent;
            }
            else
            {
            Current.Parent = null;
            this.Root = Current;
            }
            Parent.Parent = Current;
            if(Current.RightChild != null){
            Current.RightChild.Parent = Parent;
            }
            Parent.LeftChild = Current.RightChild;
            Current.RightChild = Parent;
            }


            void RotateAppropriatly(){
            /**
             * Right Rotate once (Zig)
             */
            if(Parent.LeftChild == Current && GrandParent == null){
            RightRotate(Current, Parent);
            }
            /**
             * Left Rotate once (Zag)
             */
            else if(Parent.RightChild == Current && GrandParent == null){
            LeftRotate(Current, Parent);
            }
            /**
             * Zig Zig (Left Left Case)
             */
            else if(Parent.LeftChild == Current && GrandParent.LeftChild == Parent){
            RightRotate(Parent, GrandParent);
            RightRotate(Current, Parent);
            }
            /**
             * Zig Zag (Left Right Case)
             */
            else if(Parent.LeftChild == Current && GrandParent.RightChild == Parent){
            RightRotate(Current, Parent);
            LeftRotate(Current, GrandParent);
            }
            /**
             * Zag Zag (Right Right Case)
             */
            else if(Parent.RightChild == Current && GrandParent.RightChild == Parent){
            LeftRotate(Parent, GrandParent);
            LeftRotate(Current, Parent);
            }
            /**
             * Zag Zig (Right Left Case)
             */
            else if(Parent.RightChild == Current && GrandParent.LeftChild == Parent){
            LeftRotate(Current, Parent);
            RightRotate(Current, GrandParent);
            }
            else if(Current == this.Root){
            }
            }

            /**
             * Used to assign who is the parent and who is the grandparent to vars
             */
            void History(SplayTreeNode Root)
            {
            this.Current = Root;
            if (Root.Parent != null) {
            this.Parent = Root.Parent;
            if (Root.Parent.Parent != null) {
            this.GrandParent = Root.Parent.Parent;
            }
            else{
            this.GrandParent = null;
            }
            } else {
            this.Parent = null;
            }
            }


            /**
             * if the element is not present it will return false along with it will splay the parent of the element
             */
            boolean SearchAndSplay(SplayTreeNode Root, int Data) {
            if (Root == null) {
            return false;
            } else if(Root.Data == Data){
            History(Root);
            }else if (Root.Data > Data) {
            if (Root.LeftChild != null) {
            SearchAndSplay(Root.LeftChild, Data);
            } else {
            History(Root);
            }
            } else if (Root.Data < Data) {
            if (Root.RightChild != null) {
            SearchAndSplay(Root.RightChild, Data);
            } else {
            History(Root);
            }
            }
            if (Parent == Root) {
            RotateAppropriatly();
            if(Root.Parent != null) {
            History(Root.Parent);
            }
            }
            if(Current == this.Root)
            {
            return Current.Data == Data;
            }
            return false;
            }

    /**
     * Top Down Approach
     */
    boolean Insert(SplayTreeNode Root, int Data){
        if(Root  == null) {
            this.Root = new SplayTreeNode(Data);
            return true;
        }
        else if(SearchAndSplay(Root, Data)) {
            return false;
        }
        else {
            SplayTreeNode Temp = new SplayTreeNode(Data);
            Root = this.Root;
            if (Data > Root.Data){
                Temp.LeftChild = Root;
                Temp.RightChild = Root.RightChild;
                if(Root.RightChild != null) {
                    Root.RightChild.Parent = Temp;
                    Root.RightChild = null;
                }
            }
            else {
                Temp.RightChild = Root;
                Temp.LeftChild = Root.LeftChild;
                if(Root.LeftChild != null) {
                    Root.LeftChild.Parent = Temp;
                    Root.LeftChild = null;
                }
            }
            Root.Parent = Temp;
            this.Root = Temp;
            return true;
        }
    }


    SplayTreeNode RightMostNode(SplayTreeNode Node) {
        while(Node.RightChild != null){
            Node = Node.RightChild;
        }
        return Node;
    }

    /**
     * Top Down Approach
     */
    boolean Delete(SplayTreeNode Root, int Data){
        if(!SearchAndSplay(Root, Data)) {
            return false;
        }
        else {
            Root = this.Root;
            if(Root.LeftChild != null && Root.RightChild != null)
            {
                Root.LeftChild.Parent = Root.RightChild.Parent = null;
                this.Root = Root.LeftChild;
                SearchAndSplay(Root.LeftChild,RightMostNode(Root).Data);
                this.Root.RightChild = Root.RightChild;
                Root.RightChild.Parent = this.Root;
            }else if(Root.LeftChild == null && Root.RightChild != null){
                Root.RightChild.Parent = null;
                this.Root = Root.RightChild;
            }
            else if(Root.LeftChild != null && Root.RightChild == null){
                Root.LeftChild.Parent = null;
                this.Root = Root.LeftChild;
            }
            else{
                this.Root = null;
            }
            return true;
        }
    }



    void InOrder(SplayTreeNode Root) {
        if(Root.LeftChild != null) {
            InOrder(Root.LeftChild);
        }
        System.out.print(Root.Data + " ");
        if(Root.RightChild != null) {
            InOrder(Root.RightChild);
        }
    }



    public static void main(String [] Args)
    {
        SplayTreeTopDown Obj = new SplayTreeTopDown();
        Scanner Scan = new Scanner(System.in);
        String Option;
        int Data;
        while(true)
        {
            System.out.print("Select one of the option given below\n1. Search\n2. Insert\n3. Delete" +
                    "\n4. Print[Inorder]\n6. Destroy\n7. Exit\n------>>>");
            Option = Scan.next().toUpperCase();
            if(Option.equals("exit")) {
                break;
            }
            switch (Option)
            {
                case "SEARCH":
                    System.out.print("Enter the data to be Searched : ");
                    Data = Scan.nextInt();
                    if(Obj.SearchAndSplay(Obj.Root, Data)) {
                        System.out.print(Data+" is Present!");
                    }
                    else{
                        System.out.print(Data+" is not Present!");
                    }
                    break;
                case "INSERT":
                    System.out.print("Enter the data to be Inserted : ");
                    Data = Scan.nextInt();
                    if(Obj.Insert(Obj.Root, Data)) {
                        System.out.print(Data+" has been Inserted successfully");
                    }
                    else{
                        System.out.print(Data+" is already Present!");
                    }
                    break;
                case "DELETE":
                    System.out.print("Enter the data to be Deleted : ");
                    Data = Scan.nextInt();
                    if(Obj.Delete(Obj.Root, Data)) {
                        System.out.print(Data+" has been Deleted successfully!");
                    }
                    else{
                        System.out.print(Data+" is not Present to be deleted!");
                    }
                    break;
                case "PRINT":
                    if(Obj.Root == null) {
                        System.out.print("No Elements present in the tree to print");
                    }
                    else {
                        Obj.InOrder(Obj.Root);
                    }
                    break;
                case "DESTROY":
                    Obj = new SplayTreeTopDown();
                    System.out.print("Successfully Deleted the Tree");
                    break;
                default:
                    System.out.print("Invalid input! Try Again");
            }
            System.out.println("\n-------------------------------------------------------------------------------------");
        }
    }
}
