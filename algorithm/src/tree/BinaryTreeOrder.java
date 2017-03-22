package tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


//	�������ö����������Դ���������ʵ������ʵ�ֶ��������ȸ��������и�������������������������ȣ��������£�

@SuppressWarnings("unused")
public class BinaryTreeOrder {

	public static void preOrder(BinaryTree root) { // �ȸ��������ݹ�ʵ�֣�
		if (root != null) {
			System.out.print(root.data + "-");
			preOrder(root.left);
			preOrder(root.right);
		}
	}

//	 �ǵݹ�ʵ���������2
//	(�������ݽṹ)
   public static void iterativePreorder2(BinaryTree p) {    
       Stack<BinaryTree> stack = new Stack<BinaryTree>();    
       BinaryTree root = p;    
       while (root != null || stack.size() > 0) {    
           if (root != null) {  
        	   System.out.print(root.data + "-");   //��iterativePreorder2�Ƚ�ֻ����仰��λ�ò�һ��������ʱ�ٷ��ʡ�  
               stack.push(root);    
               root = root.left;    
           }    
           else {    
               root = stack.pop();                  
               root = root.right;    
           }    
       }    
   }
   
	public static void inOrder(BinaryTree root) { // �и��������ݹ�ʵ�֣�

		if (root != null) {
			inOrder(root.left);
			System.out.print(root.data + "--");
			inOrder(root.right);
		}
	}

//	 �ǵݹ�ʵ���������2
//	http://blog.csdn.net/clam_clam/article/details/6845399	
    public static void iterativeInorder2(BinaryTree p) {    
        Stack<BinaryTree> stack = new Stack<BinaryTree>();    
        BinaryTree root = p;    
        while (root != null || stack.size() > 0) {    
            while (root != null) {    
                stack.push(root);    
                root = root.left;    
            }    
            if (stack.size() > 0)  {    
                root = stack.pop();    
                System.out.print(root.data + "--");   //��iterativePreorder2�Ƚ�ֻ����仰��λ�ò�һ��������ʱ�ٷ��ʡ�  
                root = root.right;    
            }    
        }    
    }
    
    
	public static void postOrder(BinaryTree root) { // �������

		if (root != null) {
			postOrder(root.left);
			postOrder(root.right);
			System.out.print(root.data + "---");
		}
	}
	
//�ǵݹ�ʵ�ֺ���������������ݽṹ��
	public static void iterativePostorder2(BinaryTree root) {
		Stack<BinaryTree> stack = new Stack<BinaryTree>();
		BinaryTree r = null;
		while( root != null || stack.size() > 0){
			while( root != null){  //�ߵ������
				stack.push(root);
				root = root.left;
			}
			if(stack.size() > 0){
				root = stack.peek();  //ȡ��ջ�����
				if( root.right != null && root.right != r ){  //������������ڣ���û�б����ʹ�
					root = root.right;  //ת����
					stack.push(root);  //ѹ��ջ
					root = root.left;  //���ߵ�����
				}else{                 //���򣬵�����㲢����
					root = stack.pop();//����㵯��
					System.out.print(root.data + "---");  //����
					r = root;          //��¼������ʹ��Ľ��
					root = null;  //�������������P���
				}
			}
		}
	}
	
//���ǵݹ飩�������
//	http://blog.csdn.net/clam_clam/article/details/6845399
	 public static void iterativePostorder3(BinaryTree p) {    
	        Stack<BinaryTree> stack = new Stack<BinaryTree>();    
	        BinaryTree root = p, prev = p;    
	        while (root != null || stack.size() > 0) {    
	            while (root != null) {    
	                stack.push(root);    
	                root = root.left;    
	            }    
	            if (stack.size() > 0) {    
	            	BinaryTree temp = stack.peek().right;    
	                if (temp == null || temp == prev) {    
	                    root = stack.pop();    
	                    System.out.print(root.data + "---");   
	                    prev = root;    
	                    root = null;    
	                } else {    
	                    root = temp;    
	                }    
	            }    
	    
	        }    
	    }    
	 
	 //level order ����α�����
	// http://sishuok.com/forum/posts/list/1911.html
	public static void levelOrderStack(BinaryTree p) {
		if (p == null)
			return;
		// List<BinaryTree> queue = new LinkedList<BinaryTree>();
		Deque<BinaryTree> queue = new ArrayDeque<BinaryTree>();
		queue.add(p);
		while (!queue.isEmpty()) {
			// BinaryTree temp = queue.remove(0);
			BinaryTree temp = queue.remove();
			System.out.print(temp.data + " ");
			if (temp.left != null) {
				queue.add(temp.left);
			}
			if (temp.right != null) {
				queue.add(temp.right);
			}
		}
		System.out.println();
	} 
	    
	
	//find Lowest Common Ancestor of the two node
	public static BinaryTree findLowestCommonAncestor(BinaryTree root, BinaryTree p1, BinaryTree p2){
		int min, max;
		if(p1.data < p2.data){
			min = p1.data;
			max = p2.data;
		}
		else{
			min = p2.data;
			max = p1.data;
		}
		while(root != null){
			if(root.data >= min && root.data <= max)
				return root;
			else if(root.data < min && root.data < max)
				root = root.right;
			else
				root = root.left;
		}
		return null;
	}
	
	// ���������������(maxDepth)
	/*
	 * Given a binary tree, find its maximum depth. The maximum depth is the
	 * number of nodes along the longest path from the root node down to the
	 * farthest leaf node.
	 */
	public static int maxDepth(BinaryTree root) {
		if (root == null)
			return 0;
		int lDepth = maxDepth(root.left);
		int rDepth = maxDepth(root.right);
		return 1 + (lDepth > rDepth ? lDepth : rDepth);
	}

	// ������ö������Ƿ���ͬ(same-tree)
	/*
	 * Given two binary trees, write a function to check if they are equal or
	 * not. Two binary trees are considered equal if they are structurally
	 * identical and the nodes have the same value.
	 */
	public static boolean isSameTree(BinaryTree p, BinaryTree q) {
		boolean flag = false;
		if (p == null && q == null) {
			flag = true;
			return flag;
		}
		if (p != null && q != null) {
			if (p.data == q.data)
				return isSameTree(p.left, q.left) && 
						isSameTree(p.right,q.right);
		}
		return flag;
	}

	// ���������K��Ľڵ���
	public static int getNodeNumKthLevel(BinaryTree root, int k) {
		if (root == null | k < 1)
			return 0;

		if (k == 1)
			return 1;

		return getNodeNumKthLevel(root.left, k - 1)
				+ getNodeNumKthLevel(root.right, k - 1); // ÿ�εݹ�һ�Σ����������±���һ�㣬k-1
															// k = 1ʱ����������k��
	}

	// �������Ҷ�ӽڵ�ĸ���
	public static int LeavesNodeNum(BinaryTree root) {
		if (root == null)
			return 0;
		if (root.left == null && root.right == null)
			return 1;
		return LeavesNodeNum(root.left) + LeavesNodeNum(root.right);
	}


	// ��������������(recursion) Binary tree key search  (good)
	public static boolean searchKey(BinaryTree root, int data) {
		boolean flag = false;
		if (root == null)
			return flag;
		else {
			if (root.data == data) {
				flag = true;
				return flag;
			}
			else if (root.data > data){
				return searchKey(root.left, data);
			}
			else if (root.data < data){
				return searchKey(root.right, data);
			}
		}
		return flag;        //����Ҫ����һ��boolean�͵�ֵ�����ܲ�Ҫ,������仰��û���õ�  ��������ֵ��Ӱ�죬����return true/flase
	}
//	                                          (perfect)
	public static boolean searchKey2(BinaryTree root, int data) {
		if (root == null) {
			return false;
		} else if (root.data == data) {
			return true;
		} else if (root.data > data) {
			return searchKey(root.left, data);
		} else
			return searchKey(root.right, data);
	}
	//(non-recursion) Binary tree key search     (best)
	public static boolean searchKey3(BinaryTree root, int data){
		while(root != null){
			if(root.data == data)
				return true;
			else if(root.data > data){
				root = root.left;
			}else if(root.data < data){
				root = root.right;
			}
		}
		return false;
	}

/*   (error code and error idea)    ֻ��ɾ����4����������������ӵĽڵ� �������Ľڵ�ɾ������ǰ3���������
	public static void deleteNode(BinaryTree root, int data) {
		BinaryTree q = null;
		BinaryTree p = root;
		if (root != null) {
			if (root.data > data){
				deleteNode(root.left, data);
			}
			else if (root.data < data){
				deleteNode(root.right, data);
			}
			else {
				if (root.left == null && root.right == null)
					root = null;
				else if (root.left == null)
					root = root.right;
				else if (root.right == null)
					root = root.left;
				else {                                  
					q = root;
					p = p.left;
					while (p.right != null) {
						q = p;
						p = p.right;
					}
					root.data = p.data;
					if(q != root){
					q.right = p.left;
					}
					else{
					q.left	= p.left;
					}					
					
				}
			}
		}
	}
*/

	// ɾ��ĳ���ڵ�(correct idea)
	public static boolean delete(BinaryTree root, int key) {
		// ���ҵ���Ҫɾ���Ľڵ�
		BinaryTree p = null;
		BinaryTree current = root;
		BinaryTree parent = root;
		boolean isLeftChild = false;

		while (current.data != key)// ��Ȼ����current.iData == key ʱ��current ����Ҫ�ҵĽڵ�
		{
			parent = current; // ʹparentһֱ��current�ĸ��׽ڵ㣬��current.data == keyʱ��
								// ��������ѭ��
			if (key < current.data) {
				isLeftChild = true;
				current = current.left;
			} else {
				isLeftChild = false;
				current = current.right;
			}
			if (current == null)// �Ҳ���keyʱ����false
				return false;
		}

		// ���������ɾ���Ľڵ�
		// 1.ɾ���Ľڵ�ΪҶ�ڵ�ʱ
		if (current.left == null && current.right == null) {
			if (current == root)
				root = null;
			else if (isLeftChild) // �ж� current��parent�����ӽڵ㻹���Һ��ӽڵ�
				parent.left = null;
			else
				parent.right = null;
		}
		// ɾ���Ľڵ���һ���ӽڵ�
		else if (current.right == null)// 2.ɾ���Ľڵ�ֻ��һ�����ӽڵ�ʱ
		{
			if (current == root)// Ҫɾ���Ľڵ�Ϊ���ڵ�
				root = current.left;
			else if (isLeftChild)// Ҫɾ���Ľڵ���һ�����ӽڵ�
				parent.left = current.left;
			else
				parent.right = current.left;// Ҫɾ���Ľڵ���һ�����ӽڵ�
		} else if (current.left == null)// 3.ɾ���Ľڵ�ֻ��һ�����ӽڵ�ʱ
		{
			if (current == root)// Ҫɾ���Ľڵ�Ϊ���ڵ�
				root = current.right;
			else if (isLeftChild)// Ҫɾ���Ľڵ���һ�����ӽڵ�
				parent.left = current.right;
			else
				parent.right = current.right;// Ҫɾ���Ľڵ���һ�����ӽڵ�
		}
		// 4.ɾ���Ľڵ��������ӽڵ�
		else {
			p = current;
			parent = current;
			/** ת����������Ȼ�������ߵ�����ͷ��������ת����������Ȼ�������ߵ���ͷ�� */
			p = p.left; // ת��������
			while (p.right != null) { // ��������p������������ʱ
				parent = p;
				p = p.right;
			}
			current.data = p.data;
			if (parent != current) // �ж�parent ��current�ĸ��׽ڵ㻹��
									// p�ĸ��׽ڵ㣨�����ѭ���Ƿ�ִ���ˣ�
			{
				parent.right = p.left; // P�ڵ�current�ڵ����������ұߵĽڵ㣬���Pһ��û���Һ��ӽڵ�
			} else {
				parent.left = p.left; // �����parentʵ������current�ڵ㣨û��ִ�������ѭ����
			}
		}
		return true;
	}
	
	

	public static BinaryTree invertTree2(BinaryTree root) {
	    LinkedList<BinaryTree> queue = new LinkedList<BinaryTree>();
	 
	    if(root!=null){
	        queue.add(root);
	    }
	 
	    while(!queue.isEmpty()){
	        BinaryTree p = queue.poll();
	        if(p.left!=null)
	            queue.add(p.left);
	        if(p.right!=null)
	            queue.add(p.right);
	 
	        BinaryTree temp = p.left;
	        p.left = p.right;
	        p.right = temp;
	    }
	 
	    return root;    
	}
	
	
	public static BinaryTree invertTree1(BinaryTree root) {
	    if (root == null) {
	        return null;
	    }
	    BinaryTree right = invertTree1(root.right);
	    BinaryTree left = invertTree1(root.left);
	    root.left = right;
	    root.right = left;
	    return root;
	}
	
	
	public static void main(String[] str) {
	 // int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 ,10,14};
		int[] array = { 12, 76, 35, 22, 16, 48, 90, 45, 9, 40, 11, 30, 52 };
//		int[] array = { 15, 25, 11, 8, 30, 20, 9, 23, 21, 22 };
		BinaryTree root = new BinaryTree(array[0]); // ����������tree1 
		for (int i = 1; i < array.length; i++) {
			root.insert(root, array[i]); // ��������в�������
		}

		int[] array1 = { 12, 76, 35, 22, 16, 48, 90, 46, 9, 40 };
		BinaryTree root1 = new BinaryTree(array1[0]); // ����������tree2
		for (int i = 1; i < array1.length; i++) {
			root1.insert(root1, array1[i]); // ��������в�������
		}
			
		System.out.println(isSameTree(root, root1)); // �Ƚ����Ŷ�����

		System.out.println("�ȸ��������ݹ�ʵ�֣���");
		preOrder(root);
		System.out.println();
		
		System.out.println("�ȸ��������ǵݹ�ʵ�֣���");
		iterativePreorder2(root);
		System.out.println();
		
		System.out.println("�и��������ݹ�ʵ�֣���");
		inOrder(root);		
		System.out.println();
		
		System.out.println("�и��������ǵݹ�ʵ�֣���");
		iterativeInorder2(root);
		System.out.println();
		
		System.out.println("����������ݹ�ʵ�֣���");
		postOrder(root);
		System.out.println();
		
		System.out.println("����������ǵݹ�ʵ�֣���");
		iterativePostorder2(root);
		System.out.println();
		iterativePostorder3(root);
		System.out.println();
		
		System.out.println("��α������ݹ飩");
		levelOrderStack(root);
		
//		�ڶ�����������ɾ��������ĳ���ڵ㣬�õ��Ķ����������Ƿ��ԭ������ͬ
//		root.insert(root, 47);    //Binary tree node insertion
//		delete(root, 12);  //Binary tree node delete 
		delete(root, 48);
//		root.insert(root, 45);
		levelOrderStack(root);
/*
 * 1.����Ҷ�ӽڵ㣬 ����ͬ;  2.����Ļ�����ͬ
*/		
		System.out.println(maxDepth(root1));    // ���������������(maxDepth)
		
		System.out.println(getNodeNumKthLevel(root, 3)); // ���������K��Ľڵ���
		
		System.out.println(LeavesNodeNum(root1));   // �������Ҷ�ӽڵ�ĸ���
		
		levelOrderStack(root1);
		invertTree1(root1);
		levelOrderStack(root1);
		
//		BinaryTree node = BinaryTree findLowestCommonAncestor(root,s BinaryTree p1, BinaryTree p2);
		
//		System.out.println("�������Ҳ��ҵĹؼ��֣�");
//		Scanner sc = new Scanner(System.in);
//		while (true) {
//			int key = sc.nextInt();
//			System.out.println(searchKey3(root, key));
//		}
		
		
	}
}
