import javax.swing.JOptionPane;

public class RBTree {
    Node root;
    int node_count;

    public RBTree() {
        node_count = 0;
    }
    
    public void leftRotationinsert(Node node) {
        Node temp= node.right;// make a temporary node hold the right node .
        node.right= temp.left;// make the left child of the right node equals the left child of the right node---> the node that will go up .
        if(temp.left != null)
            temp.left.parent=node;// announce the left child with his new father .
        
        temp.parent=node.parent;//replace the parent of the left node with that of the right node .
        
        if(node.parent==null)//node is the root .
            this.root=temp;
        
        else if (node.parent.left==node)//check the location of the node (according to the father) to replace the other node in the same place as a left or right child of the parent .
                node.parent.left=temp;
        
        else node.parent.right=temp;
        
        temp.left=node;
        node.parent=temp;
        
        
    }
    
    public void rightRotaioninsert(Node node) {
        Node temp= node.left;
        node.left=temp.right;
        if(temp.right!=null)
            temp.right.parent=node;
        
        temp.parent=node.parent;
        if(node.parent==null)
            this.root=temp;
        
        else if (node.parent.right==node)
            node.parent.right=temp;
        else node.parent.left= temp;
        
        temp.right=node;
        node.parent=temp;
        
        
        
    }
    private void fixInsert(Node node) {
        
        Node uncle=null ;//referes to uncle .
    
        while ( node.parent.color==true) {//two consecutive red nodes .cases
            
            if (node.parent==node.parent.parent.right) {//the parent is the right child of the grandparent .
                uncle=node.parent.parent.left;
                if  (uncle != null && uncle.color==true){//case 1 : both parent and uncle are red recoloring and loop from grand parent .
                    node.parent.color=uncle.color=false; //black
                    node.parent.parent.color=true;//red .also the inserted node is already red grandparent  .
                    node=node.parent.parent;// start again from the grandparent node 
                }//end of if for case 1 .
                else {// uncle is black , parent is red .
                    if(node==node.parent.left)//the parent is right child for grand and the node is left child for the parent so :
                    {
                        //right left case :1)right rotate parent to convert it to right right case .
                        node=node.parent;
                        rightRotaioninsert(node);
                    }
                    //2)then left rotate grand parent and swap their colors . [right right case ]
                    node.parent.color=false;
                    node.parent.parent.color=true ;
                    leftRotationinsert(node.parent.parent);
                    
                }
            }else {// the parent is the left child of the grand parent .
                uncle=node.parent.parent.right;
                if (uncle != null && uncle.color==true) {
                    node.parent.color=uncle.color=false;
                    node.parent.parent.color=true;
                    node=node.parent.parent;
                }
                else {
                    if (node==node.parent.right) { // left right case ;
                    node=node.parent;   
                    leftRotationinsert(node);
                    }
                    
                    // left left case ;
                    node.parent.color=false;
                    node.parent.parent.color=true;
                    rightRotaioninsert(node.parent.parent);
                }
                
            }
            if(node==root)
                break;
        }
        this.root.color=false;
    }

    public void insertNode(int key) {
        Node newNode= new Node(key);// saves the key and make the color red.
        newNode.parent=null;
        newNode.left=newNode.right=null;
        
        Node tempParent=null;
        Node current= this.root;
        while(current!=null) {
            tempParent=current;
            if(newNode.key <current.key)
                current=current.left;
            else current=current.right;
        }
        
        newNode.parent=tempParent;
        if(tempParent==null){ //there is no nodes yet .
            this.root=newNode;
            newNode.color=false;//black root .
            node_count++;
            return ;
        }
        else if (newNode.key < tempParent.key)
            tempParent.left=newNode;
        else tempParent.right=newNode;
        
        //this means the root is the only node that exist so the added node will be red and thats it .
        if(newNode.parent.parent==null) {
            node_count++;
            return;
        }
        
        fixInsert(newNode);
        node_count++;
    }
    
    private void printHelper(Node root, String indent, boolean last) {
        // print the tree structure on the screen
        if (root != null) {
           System.out.print(indent);
           if (last) {
              System.out.print("R----");
              indent += "     ";
           } else {
              System.out.print("L----");
              indent += "|    ";
           }
            
           String sColor = root.color == true?"RED":"BLACK";
           System.out.println(root.key + "(" + sColor + ")");
           printHelper(root.left, indent, false);
           printHelper(root.right, indent, true);
        }
    }
    
    public void prettyPrint() {
        printHelper(this.root, "", true);
    }
    public void clear() {
        root = null;
        node_count = 0;
    }
    Node getSuccessor(Node node) {
        node = node.getRight();
        while(node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }
    
    Node getNode(int key) {
        return searchNode(root, key);
    }
    
    public Node searchNode(Node node, int key) {
        if(node == null) 
            return null;
        System.out.println(node.getKey());
        if(node.getKey() < key) {
            return searchNode(node.getRight(), key);
        } else if(node.getKey() > key) {
            return searchNode(node.getLeft(), key);
        } 
        
        return node;
    }

    public void deleteKey(int key) {
        this.root = deleteKey(root, key);
    }
    
    public void leftRotation(Node node) {
        Node temp= node.getRight();// make a temporary node hold the right node .
        node.setRight(temp.getLeft());// make the left child of the right node equals the left child of the right node---> the node that will go up .
        if(temp.getRight() != null)
            temp.getRight().setParent(node);// announce the left child with his new father .
        
        temp.setParent(node.getParent());//replace the parent of the left node with that of the right node .
        
        if(node.getParent()==null)//node is the root .
            this.root=temp;
        else if (node.getParent().getLeft() == node)//check the location of the node (according to the father) to replace the other node in the same place as a left or right child of the parent .
                node.getParent().setLeft(temp);
        
        else node.getParent().setRight(temp);
        
        node.setParent(temp);
        temp.setLeft(node);
        
    }
    
    public void rightRotation(Node node) {
        Node temp= node.getLeft();
        node.setLeft(temp.getRight());
        if(temp.getLeft()!=null)
            temp.getLeft().setParent(node);
        
        temp.setParent(node.getParent());
        if(node.getParent()==null)
            this.root=temp;
        
        else if (node.getParent().getLeft() == node)
            node.getParent().setLeft(temp);
        else node.getParent().setRight(temp);
        temp.setRight(node);
        node.setParent(temp);
        
    }
    
    public Node deleteKey(Node node, int key) {
        if(node == null) {
            System.out.println("The key is not found");
            return null;
        }
        if(node.getKey() < key) {
            Node x = deleteKey(node.getRight(), key);
            node.setRight(x);
            if(x != null)
                x.setParent(node);
        } else if(node.getKey() > key) {
            Node x = deleteKey(node.getLeft(), key);
            node.setLeft(x);
            if(x != null)
                x.setParent(node);
        } else {
            if(node.getLeft() == null && node.getRight() == null) {
                adjustDelete(node);
                --node_count;
                return null;
            } else if(node.getLeft() == null) {
                adjustDelete(node);
                --node_count;
                return node.getRight();
            } else if(node.getRight() == null) {
                adjustDelete(node);
                --node_count;
                return node.getLeft();
            }
            // swapping
            Node suc = getSuccessor(node);
            int nodeKey = node.getKey();
            node.setKey(suc.getKey());
            suc.setKey(nodeKey);
            // continue traversing
            Node x = deleteKey(node.getRight(), key);
            node.setRight(x);
            if(x != null)
                x.setParent(node);
        }
        return node;
    }

    private void adjustDelete(Node node) {
        if(node == null || node.isRed() || node.getParent() == null) 
            return ;
        if(node.getRight() != null && node.getRight().isRed()) 
            node.getRight().makeBlack();
        else if(node.getLeft() != null && node.getLeft().isRed())
            node.getLeft().makeBlack();
        else {
            boolean isDoubleBlack = true;
            Node temp = node;
            while(isDoubleBlack && temp.getParent() != null) {
                if(temp == temp.getParent().getLeft()) {
                    if(temp.getParent().getRight().isRed()) {
                        temp.getParent().getRight().makeBlack();
                        temp.getParent().makeRed();
                        this.rightRotation(temp.getParent());
                    } else {
                        if((temp.getParent().getRight().getLeft() == null 
                                || !temp.getParent().getRight().getLeft().isRed() ) 
                       && (temp.getParent().getRight().getRight() == null || 
                       !temp.getParent().getRight().getRight().isRed() )) {
                            
                            temp.getParent().getRight().makeRed();
                            if(temp.getParent().isRed()) {
                                temp.getParent().makeBlack();
                                isDoubleBlack = false;
                            } else {
                                temp.getParent().makeBlack();
                            }
                            temp = temp.getParent();
                        } else {
                            if(!temp.getParent().getRight().getRight().isRed()) {
                                
                                temp.getParent().getRight().getLeft().makeBlack();
                                temp.getParent().getRight().makeRed();
                                rightRotation(temp.getParent().getRight());
                                
                            }
                            if(temp.getParent().isRed())
                                temp.getParent().getRight().makeRed();
                            else 
                                temp.getParent().getRight().makeBlack();
                            temp.getParent().makeBlack();
                            temp.getParent().getRight().getRight().makeBlack();
                            this.leftRotation(temp.getParent());
                            break;
                        }
                    }
                } else {
                    if(temp.getParent().getLeft().isRed()) {
                        temp.getParent().getLeft().makeBlack();
                        temp.getParent().makeRed();
                        this.rightRotation(temp.getParent());
                    } else {
                        if((temp.getParent().getRight().getLeft() == null ||
                            !temp.getParent().getRight().getLeft().isRed() ) 
                       && (temp.getParent().getRight().getRight() == null
                           || !temp.getParent().getRight().getRight().isRed())) {
                            
                            temp.getParent().getLeft().makeRed();
                            if(temp.getParent().isRed()) {
                                temp.getParent().makeBlack();
                                isDoubleBlack = false;
                            } else {
                                temp.getParent().makeBlack();
                            }
                            temp = temp.getParent();
                        } else {
                            if(!temp.getParent().getLeft().getLeft().isRed()) {
                                
                                temp.getParent().getLeft().getRight().makeBlack();
                                temp.getParent().getLeft().makeRed();
                                rightRotation(temp.getParent().getLeft());
                                
                            }
                            if(temp.getParent().isRed())
                                temp.getParent().getLeft().makeRed();
                            else 
                                temp.getParent().getLeft().makeBlack();
                            temp.getParent().makeBlack();
                            temp.getParent().getRight().getLeft().makeBlack();
                            this.leftRotation(temp.getParent());
                            break;
                        }
                    }
                }
            }
            this.root.makeBlack();
        }
        
    }
}
