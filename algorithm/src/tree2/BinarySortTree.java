package tree2;

//import java.util.Random;
import java.util.Stack;
 
/**
 * 二叉排序树（又称二叉查找树）
 * （1）可以是一颗空树
 * （2）若左子树不空，则左子树上所有的结点的值均小于她的根节点的值
 * （3）若右子树不空，则右子树上所有的结点的值均大于她的根节点的值
 * （4）左、右子树也分别为二叉排序树
 * 
 * 
 * 性能分析：
 * 查找性能：
 *      含有n个结点的二叉排序树的平均查找长度和树的形态有关，
 *      （最坏情况）当先后插入的关键字有序时，构成的二叉排序树蜕变为单枝树。查找性能为O(n)
 *      （最好情况）二叉排序树的形态和折半查找的判定树相同，其平均查找长度和log2(n)成正比
 * 
 * 
 * 插入、删除性能：
 *      插入、删除操作间复杂度都O(log(n))级的，
 *      即经过O(log(n))时间搜索到了需插入删除节点位置和删除节点的位置
 *      经O(1)级的时间直接插入和删除
 *      与顺序表相比，比序顺序表插入删除O(n)(查找时间O(log(n))移动节点时间O(n))要快
 *      与无序顺序表插入时间O(1)，删除时间O(n)相比，因为是有序的，所查找速度要快很多
 * 
 * 
 * 
 * 作者：小菜鸟
 * 创建时间：2014-08-17
 * http://www.2cto.com/kf/201408/326591.html
 */
 
public class BinarySortTree {
 
    private Node root = null;
 
     
    /**查找二叉排序树中是否有key值*/
    public boolean searchBST(int key){
        Node current = root;
        while(current != null){
            if(key == current.getValue())
                return true;
            else if(key < current.getValue())
                current = current.getLeft();
            else
                current = current.getRight();
        }
        return false;
    }
     
     
    /**向二叉排序树中插入结点*/
    public void insertBST(int key){
        Node p = root;
        /**记录查找结点的前一个结点*/
        Node prev = null;
        /**一直查找下去，直到到达满足条件的结点位置*/
        while(p != null){
            prev = p;
            if(key < p.getValue())
                p = p.getLeft();
            else if(key > p.getValue())
                p = p.getRight();
            else
                return;
        }
        /**prve是要安放结点的父节点，根据结点值得大小，放在相应的位置*/
        if(root == null)
            root = new Node(key);
        else if(key < prev.getValue())
            prev.setLeft(new Node(key));
        else prev.setRight(new Node(key));
    }
     
     
     
    /**
     * 删除二叉排序树中的结点
     * 分为三种情况：（删除结点为*p ，其父结点为*f）
     * （1）要删除的*p结点是叶子结点，只需要修改它的双亲结点的指针为空
     * （2）若*p只有左子树或者只有右子树，直接让左子树/右子树代替*p
     * （3）若*p既有左子树，又有右子树
     *      用p左子树中最大的那个值（即最右端S）代替P，删除s，重接其左子树
     * */
    public void deleteBST(int key){
        deleteBST(root, key);
    }
    private boolean deleteBST(Node node, int key) {
        if(node == null) return false;
        else{
            if(key == node.getValue()){
                return delete(node);
            }
            else if(key < node.getValue()){
                return deleteBST(node.getLeft(), key);
            }
            else{
                return deleteBST(node.getRight(), key);
            }
        }
    }
 
    private boolean delete(Node node) {
        Node temp = null;
        /**右子树空，只需要重接它的左子树
         * 如果是叶子结点，在这里也把叶子结点删除了
         * */
        if(node.getRight() == null){
            temp = node;
            node = node.getLeft();
        }
        /**左子树空， 重接它的右子树*/
        else if(node.getLeft() == null){
            temp = node;
            node = node.getRight();
        }
        /**左右子树均不为空*/
        else{
            temp = node;
            Node s = node;
            /**转向左子树，然后向右走到“尽头”（或者转向右子树，然后向左走到尽头）*/
            s = s.getLeft();
            while(s.getRight() != null){
                temp = s;
                s = s.getRight();
            }
            node.setValue(s.getValue());
            if(temp != node){
                temp.setRight(s.getLeft());
            }
            else{
                temp.setLeft(s.getLeft());
            }
        }
        return true;
    }
 
     
    /**中序非递归遍历二叉树
     * 获得有序序列
     * */
    public void nrInOrderTraverse(){
        Stack<Node> stack = new Stack<Node>();
        Node node = root;
        while(node != null || !stack.isEmpty()){
            while(node != null){
                stack.push(node);
                node = node.getLeft();
            }
            node = stack.pop();
            System.out.print(node.getValue()+ " ");
            node = node.getRight();
        }
    }
     
    public static void main(String[] args){
        BinarySortTree bst = new BinarySortTree();
        /**构建的二叉树没有相同元素*/
        int[] num = {4,7,2,1,10,6,9,3,8,11,2, 0, -2};
        for(int i = 0; i < num.length; i++){
            bst.insertBST(num[i]);
        }
        bst.nrInOrderTraverse();
        System.out.println();
        System.out.println(bst.searchBST(-2));
        bst.deleteBST(1);
        bst.nrInOrderTraverse();
    }
}    
     
    /**二叉树的结点定义*/
     class Node{
        private int value;
        private Node left;
        private Node right;
         
        public Node(){
        }
        public Node(Node left, Node right, int value){
            this.left = left;
            this.right = right;
            this.value = value;
        }
        public Node(int value){
            this(null, null, value);
        }
         
        public Node getLeft(){
            return this.left;
        }
        public void setLeft(Node left){
            this.left = left;
        }
        public Node getRight(){
            return this.right;
        }
        public void setRight(Node right){
            this.right = right;
        }
        public int getValue(){
            return this.value;
        }
        public void setValue(int value){
            this.value = value;
        }
    }
    
