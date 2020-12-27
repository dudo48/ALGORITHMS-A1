public class RBTree {
    Node root;
    int node_count;

    public RBTree() {
        node_count = 0;
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
                        rightRotation(node);
                    }
                    //2)then left rotate grand parent and swap their colors . [right right case ]
                    node.parent.color=false;
                    node.parent.parent.color=true ;
                    leftRotation(node.parent.parent);
                    
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
                    leftRotation(node);
                    }
                    
                    // left left case ;
                    node.parent.color=false;
                    node.parent.parent.color=true;
                    rightRotation(node.parent.parent);
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
        if(node.getKey() < key) {
            return searchNode(node.getRight(), key);
        } else if(node.getKey() > key) {
            return searchNode(node.getLeft(), key);
        } 
        
        return node;
    }

    public void deleteKey(int key) {
        deleteKey(root, key);
    }

    public void leftRotation(Node node) {
        
        Node temp = node.getRight();// make a temporary node hold the right node .
        node.setRight(temp.getLeft());// make the left child of the right node equals the left child of the right node---> the node that will go up .
        if(node.getRight() != null)
            node.getRight().setParent(node);// announce the left child with his new father .
        
        temp.setParent(node.getParent());//replace the parent of the left node with that of the right node .
        
        if(node.getParent()==null)//node is the root .
            this.root=temp;
        else if (node.getParent().getLeft() == node)//check the location of the node (according to the father) to replace the other node in the same place as a left or right child of the parent .
                node.getParent().setLeft(temp);
        
        else node.getParent().setRight(temp);
        
        temp.setLeft(node);
        node.setParent(temp);
    }
    
    public void rightRotation(Node node) {
        Node temp = node.getLeft();
        node.setLeft(temp.getRight());
        if(node.getLeft() != null)
            node.getLeft().setParent(node);
        
        temp.setParent(node.getParent());
        if(node.getParent() == null)
            this.root = temp;
        else if (node.getParent().getLeft() == node)
            node.getParent().setLeft(temp);
        else node.getParent().setRight(temp);
        temp.setRight(node);
        node.setParent(temp);
        
    }
    public void traverse(Node node) {
        if(node == null)
            return ;
        
        traverse(node.getLeft());
        traverse(node.getRight());
    }
    
    public void deleteKey(Node node, int key) {
        if(node == null) {
            System.out.println("The key is not found");
            return;
        }
        if(node.getKey() < key) {
            deleteKey(node.getRight(), key);
        } else if(node.getKey() > key) {
            deleteKey(node.getLeft(), key);
        } else {
            if(node.getLeft() == null && node.getRight() == null) {
                adjustDelete(node);
                if(node == root) {
                    this.root = null;
                } else if(node == node.getParent().getLeft()) {
                    node.getParent().setLeft(null);
                    node.setParent(null);
                } else {
                    node.getParent().setRight(null);
                    node.setParent(null);
                }
                return ;
            } else if(node.getLeft() == null) {
                adjustDelete(node);
                if(node == root) {
                    this.root = node.getRight();
                    node.getRight().setParent(null);
                } else if(node == node.getParent().getLeft()) {
                    node.getParent().setLeft(node.getRight());
                    node.getRight().setParent(node.getParent());
                } else {
                    node.getParent().setRight(node.getRight());
                    node.getRight().setParent(node.getParent());
                }
                node.setParent(null);
                return ;
            } else if(node.getRight() == null) {
                adjustDelete(node);
                if(node == root) {
                    this.root = node.getLeft();
                    node.getLeft().setParent(null);
                } else if(node == node.getParent().getLeft()) {
                    node.getParent().setLeft(node.getLeft());
                    node.getLeft().setParent(node.getParent());
                } else {
                    node.getParent().setRight(node.getLeft());
                    node.getLeft().setParent(node.getParent());
                }
                node.setParent(null);
                return ;
            }
            // swapping
            Node suc = getSuccessor(node);
            int nodeKey = node.getKey();
            node.setKey(suc.getKey());
            suc.setKey(nodeKey);
            
            deleteKey(node.getRight(), key);
        }
    }

    private void adjustDelete(Node node) {
        
        if(node == null || node.isRed())  // if it is red, do nothing.
            return ;
        // if it has a red child, make it black.
        if(node.getRight() != null && node.getRight().isRed()) 
            node.getRight().makeBlack();
        else if(node.getLeft() != null && node.getLeft().isRed())
            node.getLeft().makeBlack();
        else {
            // if it's black and has both black children.(double-black).
            boolean isDoubleBlack = true;
            Node temp = node;
            while(isDoubleBlack && temp != root) {
                if(temp == temp.getParent().getLeft()) {
                    // if the sibling is red.
                    if(temp.getParent().getRight() != null && temp.getParent().getRight().isRed()) {
                        temp.getParent().getRight().makeBlack();
                        temp.getParent().makeRed();
                        this.leftRotation(temp.getParent());
                    } else {
                        // if it's black
                        if((temp.getParent().getRight().getLeft() == null ||!temp.getParent().getRight().getLeft().isRed()) 
                       && ( temp.getParent().getRight().getRight() == null ||!temp.getParent().getRight().getRight().isRed()))                               {
                            
                            temp.getParent().getRight().makeRed();
                            if(temp.getParent() != null && temp.getParent().isRed()) {
                                temp.getParent().makeBlack();
                                isDoubleBlack = false;
                            }
                            temp = temp.getParent();
                        } else {
                            
                            if(temp.getParent().getRight().getRight() == null || 
                                    !temp.getParent().getRight().getRight().isRed()) {
                                temp.getParent().getRight().getLeft().makeBlack();
                                temp.getParent().getRight().makeRed();
                                this.rightRotation(temp.getParent().getRight());
                            }
                            
                            if(temp.getParent() != null && temp.getParent().isRed())
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
                    if(temp.getParent().getLeft() != null && temp.getParent().getLeft().isRed()) {
                        temp.getParent().getLeft().makeBlack();
                        temp.getParent().makeRed();
                        this.rightRotation(temp.getParent());
                    } else {
                        if((temp.getParent().getLeft().getLeft() == null ||
                                !temp.getParent().getLeft().getLeft().isRed() ) 
                       && (temp.getParent().getLeft().getRight() == null
                       || !temp.getParent().getLeft().getRight().isRed())) {
                            temp.getParent().getLeft().makeRed();
                            if(temp.getParent().isRed()) {
                                temp.getParent().makeBlack();
                                isDoubleBlack = false;
                            }
                            temp = temp.getParent();
                        } else {
                            if(temp.getParent().getLeft().getLeft() == null || 
                                    !temp.getParent().getLeft().getLeft().isRed()) {
                                
                                temp.getParent().getLeft().getRight().makeBlack();
                                temp.getParent().getLeft().makeRed();
                                this.leftRotation(temp.getParent().getLeft());
                                
                            }
                            if(temp.getParent() != null && temp.getParent().isRed())
                                temp.getParent().getLeft().makeRed();
                            else 
                                temp.getParent().getLeft().makeBlack();
                            temp.getParent().makeBlack();
                            temp.getParent().getLeft().getLeft().makeBlack();
                            this.rightRotation(temp.getParent());
                            break;
                        }
                    }
                }
            }
            this.root.makeBlack();
        }
    }
}
