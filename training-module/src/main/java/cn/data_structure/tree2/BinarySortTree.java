package cn.data_structure.tree2;

//import java.util.Random;
import java.util.Stack;
 
/**
 * �������������ֳƶ����������
 * ��1��������һ�ſ���
 * ��2�������������գ��������������еĽ���ֵ��С�����ĸ��ڵ��ֵ
 * ��3�������������գ��������������еĽ���ֵ���������ĸ��ڵ��ֵ
 * ��4����������Ҳ�ֱ�Ϊ����������
 * 
 * 
 * ���ܷ�����
 * �������ܣ�
 *      ����n�����Ķ�����������ƽ�����ҳ��Ⱥ�������̬�йأ�
 *      �����������Ⱥ����Ĺؼ�������ʱ�����ɵĶ����������ɱ�Ϊ��֦������������ΪO(n)
 *      ������������������������̬���۰���ҵ��ж�����ͬ����ƽ�����ҳ��Ⱥ�log2(n)������
 * 
 * 
 * ���롢ɾ�����ܣ�
 *      ���롢ɾ�������临�Ӷȶ�O(log(n))���ģ�
 *      ������O(log(n))ʱ���������������ɾ���ڵ�λ�ú�ɾ���ڵ��λ��
 *      ��O(1)����ʱ��ֱ�Ӳ����ɾ��
 *      ��˳�����ȣ�����˳������ɾ��O(n)(����ʱ��O(log(n))�ƶ��ڵ�ʱ��O(n))Ҫ��
 *      ������˳������ʱ��O(1)��ɾ��ʱ��O(n)��ȣ���Ϊ������ģ��������ٶ�Ҫ��ܶ�
 * 
 * 
 * 
 * ���ߣ�С����
 * ����ʱ�䣺2014-08-17
 * http://www.2cto.com/kf/201408/326591.html
 */
 
public class BinarySortTree {
 
    private Node root = null;
 
     
    /**���Ҷ������������Ƿ���keyֵ*/
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
     
     
    /**������������в�����*/
    public void insertBST(int key){
        Node p = root;
        /**��¼���ҽ���ǰһ�����*/
        Node prev = null;
        /**һֱ������ȥ��ֱ���������������Ľ��λ��*/
        while(p != null){
            prev = p;
            if(key < p.getValue())
                p = p.getLeft();
            else if(key > p.getValue())
                p = p.getRight();
            else
                return;
        }
        /**prve��Ҫ���Ž��ĸ��ڵ㣬���ݽ��ֵ�ô�С��������Ӧ��λ��*/
        if(root == null)
            root = new Node(key);
        else if(key < prev.getValue())
            prev.setLeft(new Node(key));
        else prev.setRight(new Node(key));
    }
     
     
     
    /**
     * ɾ�������������еĽ��
     * ��Ϊ�����������ɾ�����Ϊ*p ���丸���Ϊ*f��
     * ��1��Ҫɾ����*p�����Ҷ�ӽ�㣬ֻ��Ҫ�޸�����˫�׽���ָ��Ϊ��
     * ��2����*pֻ������������ֻ����������ֱ����������/����������*p
     * ��3����*p����������������������
     *      ��p�������������Ǹ�ֵ�������Ҷ�S������P��ɾ��s���ؽ���������
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
        /**�������գ�ֻ��Ҫ�ؽ�����������
         * �����Ҷ�ӽ�㣬������Ҳ��Ҷ�ӽ��ɾ����
         * */
        if(node.getRight() == null){
            temp = node;
            node = node.getLeft();
        }
        /**�������գ� �ؽ�����������*/
        else if(node.getLeft() == null){
            temp = node;
            node = node.getRight();
        }
        /**������������Ϊ��*/
        else{
            temp = node;
            Node s = node;
            /**ת����������Ȼ�������ߵ�����ͷ��������ת����������Ȼ�������ߵ���ͷ��*/
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
 
     
    /**����ǵݹ����������
     * �����������
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
        /**�����Ķ�����û����ͬԪ��*/
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
     
    /**�������Ľ�㶨��*/
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
    
