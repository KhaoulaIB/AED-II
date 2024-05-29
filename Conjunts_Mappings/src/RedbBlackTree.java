/**
 * RULES OF A RED-BLACK TREE
 * ROOT IS ALWAYS BLACK
 * NEW INSERTIONS ARE ALWAYS RED
 * THE NUMBER OF BLACK NODES MUST BE THE SAME IN EVERY BRANCH OF THE TREE
 * THERE ARE NO TWO CONSECUTIVE RED NODES
 * NULL NODES ARE BLACK
 * @author  Titrit
 * BASED ON RobEdwards explication
 * @param <K>
 * @param <V>
 */
public class RedbBlackTree<K extends Comparable<K>,V>  {



    Node <K,V> root;
    int size;

    class Node<K,v>{

        K key;
        V value;
        Node <K,V> left, right, parent;
        boolean isLeftChild, black;
        public Node(K key, V value){
            this.key=key;
            this.value=value;
            left=right=parent=null;
            isLeftChild=false;//we don't know so far
            black=false;//insertions are always RED
        }

    }

    public void add(K key, V value){

        Node<K,V> node = new Node<K,V>(key,value);
        //is the tree empty?
        if (root==null){
            root=node;
            size++;
            root.black=true;//root is always BLACK
        }else{
            recursiveAdd(root,node);


        }

    }

    private void recursiveAdd(Node<K,V> parent, Node<K,V> newNode){

        if (newNode.key.compareTo(parent.key)>0){
            if (parent.right==null){
                parent.right=newNode;
                newNode.parent=parent;
                newNode.isLeftChild=false;
            }else {
                   recursiveAdd(parent.right, newNode);
            }
        }else{
            if (parent.left==null){
                parent.left=newNode;
                newNode.parent=parent;
                newNode.isLeftChild=true;
            }else {
                recursiveAdd(parent.left, newNode);
            }
        }

        //check color
        checkColor(newNode);


    }

    /**
     * Checks form node to root if there are any violations of RB-tree norms
     * @param node
     */
    private void checkColor(Node<K,V> node){

        //
        if (node==root){
            return;
        }
        if (!node.black && !node.parent.black){//two consecutive reds nods

            correctTree(node);
        }
        checkColor(node.parent);

    }

    /**
     * //black aunt-->Rotate + change color : BLACK, RED, RED
     *
     *  //red aunt--> colorFlip : RED, BLACK, BLACK
     * @param node
     */

    public void correctTree(Node<K,V>node){
        if(node.parent.isLeftChild){
            //aunt is node.parent.parent.right
            Node<K,V> aunt = node.parent.parent.right;
            if (aunt==null || node.parent.parent.right.black){
                rotate(node);
            }
            if (node.parent.parent.right!=null){
                //color flip
                node.parent.parent.right.black=true;
                node.parent.parent.black=false;
                node.parent.black=true;
            }
            return;
        }
        //aunt is grandparent.left
        Node<K,V> aunt = node.parent.parent.right;

        if (aunt==null || node.parent.parent.left.black){
            rotate(node);
        }
        if (node.parent.parent.left!=null){
            //color flip
            node.parent.parent.left.black=true;
            node.parent.parent.black=false;
            node.parent.black=true;
        }



    }

    /**
     * The same rotationd for AVL trees
     * @param node
     */
    private void rotate(Node<K,V> node){
        if (node.isLeftChild){
            if (node.parent.isLeftChild){
                rightRotate(node.parent.parent);
                node.black=false;
                node.parent.parent.black=true;
                if (node.parent.right!=null){
                    node.parent.right.black=false;
                }
                return;
            }
            RLcase(node.parent.parent);
            node.black=true;
            node.right.black=false;
            node.left.black=false;
            return;

        }
        //right child

        if (node.parent.isLeftChild){
            leftRotate(node.parent.parent);
            node.black=false;
            node.parent.parent.black=true;
            if (node.parent.right!=null){
                node.parent.right.black=false;
            }
            return;
        }
        LRcase(node.parent.parent);
        node.black=true;
        node.right.black=false;
        node.left.black=false;
        return;


    }

    private void LRcase(Node<K, V> node) {
        leftRotate(node.left);//rotate the parent node
        rightRotate(node);
    }

    private void RLcase(Node<K,V> node){
        rightRotate(node.right);
        leftRotate(node);
    }

    private void rightRotate(Node<K,V>node){
        Node <K,V> temp = node.left;
        node.left=temp.right;
        if (node.left!=null){//upadte the right child
            node.left.parent=node;
            node.left.isLeftChild=false;
        }
        if (node.parent==null){
            //we are the root node
            root = temp;
            temp.parent=null;

        }else{
            temp.parent=node.parent;
            if (node.isLeftChild){
                temp.isLeftChild=true;
                temp.parent.right=temp;
            }else{
                temp.isLeftChild=false;
                temp.parent.left=temp;

            }

            temp.right=node;
            node.isLeftChild=true;
            node.parent=temp;
        }

    }

    private void leftRotate(Node<K,V>node){

        Node <K,V> temp = node.right;
        node.right=temp.left;
        if (node.right!=null){//upadte the right child
            node.right.parent=node;
            node.right.isLeftChild=false;
        }
        if (node.parent==null){
            //we are the root node
            root = temp;
            temp.parent=null;

        }else{
            temp.parent=node.parent;
            if (node.isLeftChild){
                temp.isLeftChild=true;
                temp.parent.left=temp;
            }else{
                temp.isLeftChild=false;
                temp.parent.right=temp;

            }

            temp.left=node;
            node.isLeftChild=true;
            node.parent=temp;
        }



    }

    public int height(){

        if (root==null){
            return 0;
        }
        return height(root)-1;

    }
    private int height(Node<K,V> node){
        if (node==null){
            return 0;
        }
        int leftHeight = height(node.left)+1;//+current edge
        int rightHeight=height(node.right)+1;

        return Math.max(leftHeight, rightHeight);

    }

    public int numBalckNodes(Node<K,V> node){
        //RB-Tree
        if (node==null){
            return 1;//null nods are black ALWAYS
        }
        int rightBNcount = numBalckNodes(node.right);
        int leftBNcount=numBalckNodes(node.left);
        if (rightBNcount!=leftBNcount){
            //throw an erro OR fix the tree
            System.out.println("error in the tree. Number of black nodes is not the smae in evey branch!");
           // return;
        }
        if (node.black) {
            leftBNcount++;
        }
            return  leftBNcount;//we can use any of them
        }

    }








