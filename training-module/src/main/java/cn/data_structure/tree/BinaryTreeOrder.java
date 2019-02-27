package cn.data_structure.tree;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;


//	当建立好二叉树类后可以创建二叉树实例，并实现二叉树的先根遍历，中根遍历，后根遍历，树的最大深度，代码如下：

@SuppressWarnings("unused")
public class BinaryTreeOrder {

	public static void preOrder(BinaryTree root) { // 先根遍历（递归实现）
		if (root != null) {
			System.out.print(root.data + "-");
			preOrder(root.left);
			preOrder(root.right);
		}
	}

	//	 非递归实现先序遍历2
//	(王道数据结构)
	public static void iterativePreorder2(BinaryTree p) {
		Stack<BinaryTree> stack = new Stack<BinaryTree>();
		BinaryTree root = p;
		while (root != null || stack.size() > 0) {
			if (root != null) {
				System.out.print(root.data + "-");   //与iterativePreorder2比较只有这句话的位置不一样，弹出时再访问。
				stack.push(root);
				root = root.left;
			}
			else {
				root = stack.pop();
				root = root.right;
			}
		}
	}

	public static void inOrder(BinaryTree root) { // 中根遍历（递归实现）

		if (root != null) {
			inOrder(root.left);
			System.out.print(root.data + "--");
			inOrder(root.right);
		}
	}

	//	 非递归实现中序遍历2
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
				System.out.print(root.data + "--");   //与iterativePreorder2比较只有这句话的位置不一样，弹出时再访问。
				root = root.right;
			}
		}
	}


	public static void postOrder(BinaryTree root) { // 后根遍历

		if (root != null) {
			postOrder(root.left);
			postOrder(root.right);
			System.out.print(root.data + "---");
		}
	}

	//非递归实现后序遍历（王道数据结构）
	public static void iterativePostorder2(BinaryTree root) {
		Stack<BinaryTree> stack = new Stack<BinaryTree>();
		BinaryTree r = null;
		while( root != null || stack.size() > 0){
			while( root != null){  //走到最左边
				stack.push(root);
				root = root.left;
			}
			if(stack.size() > 0){
				root = stack.peek();  //取出栈顶结点
				if( root.right != null && root.right != r ){  //如果右子树存在，且没有被访问过
					root = root.right;  //转向右
					stack.push(root);  //压入栈
					root = root.left;  //再走到最左
				}else{                 //否则，弹出结点并访问
					root = stack.pop();//将结点弹出
					System.out.print(root.data + "---");  //访问
					r = root;          //记录最近访问过的结点
					root = null;  //结点访问完后，重置P结点
				}
			}
		}
	}

	//（非递归）后序遍历
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

	//level order （层次遍历）
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

	// 求二叉树的最大深度(maxDepth)
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

	// 检测两棵二叉树是否相同(same-tree)
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

	// 求二叉树第K层的节点数
	public static int getNodeNumKthLevel(BinaryTree root, int k) {
		if (root == null | k < 1)
			return 0;

		if (k == 1)
			return 1;

		return getNodeNumKthLevel(root.left, k - 1)
				+ getNodeNumKthLevel(root.right, k - 1); // 每次递归一次，二叉树向下遍历一层，k-1
		// k = 1时，遍历到第k层
	}

	// 求二叉树叶子节点的个数
	public static int LeavesNodeNum(BinaryTree root) {
		if (root == null)
			return 0;
		if (root.left == null && root.right == null)
			return 1;
		return LeavesNodeNum(root.left) + LeavesNodeNum(root.right);
	}


	// 二叉排序树查找(recursion) Binary tree key search  (good)
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
		return flag;        //必须要返回一个boolean型的值，不能不要,但是这句话并没有用到  所以他的值不影响，可以return true/flase
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

/*   (error code and error idea)    只能删掉第4种情况下有两个孩子的节点 ，其他的节点删不掉，前3中情况错误
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

	// 删除某个节点(correct idea)
	public static boolean delete(BinaryTree root, int key) {
		// 先找到需要删除的节点
		BinaryTree p = null;
		BinaryTree current = root;
		BinaryTree parent = root;
		boolean isLeftChild = false;

		while (current.data != key)// 显然，当current.iData == key 时，current 就是要找的节点
		{
			parent = current; // 使parent一直是current的父亲节点，当current.data == key时候
			// ，不进入循环
			if (key < current.data) {
				isLeftChild = true;
				current = current.left;
			} else {
				isLeftChild = false;
				current = current.right;
			}
			if (current == null)// 找不到key时返回false
				return false;
		}

		// 分情况考虑删除的节点
		// 1.删除的节点为叶节点时
		if (current.left == null && current.right == null) {
			if (current == root)
				root = null;
			else if (isLeftChild) // 判断 current是parent的左孩子节点还是右孩子节点
				parent.left = null;
			else
				parent.right = null;
		}
		// 删除的节点有一个子节点
		else if (current.right == null)// 2.删除的节点只有一个左子节点时
		{
			if (current == root)// 要删除的节点为根节点
				root = current.left;
			else if (isLeftChild)// 要删除的节点是一个左子节点
				parent.left = current.left;
			else
				parent.right = current.left;// 要删除的节点是一个右子节点
		} else if (current.left == null)// 3.删除的节点只有一个右子节点时
		{
			if (current == root)// 要删除的节点为根节点
				root = current.right;
			else if (isLeftChild)// 要删除的节点是一个左子节点
				parent.left = current.right;
			else
				parent.right = current.right;// 要删除的节点是一个右子节点
		}
		// 4.删除的节点有两个子节点
		else {
			p = current;
			parent = current;
			/** 转向左子树，然后向右走到“尽头”（或者转向右子树，然后向左走到尽头） */
			p = p.left; // 转向左子树
			while (p.right != null) { // 当左子树p的右子树不空时
				parent = p;
				p = p.right;
			}
			current.data = p.data;
			if (parent != current) // 判断parent 是current的父亲节点还是
			// p的父亲节点（上面的循环是否执行了）
			{
				parent.right = p.left; // P节点current节点左子树最右边的节点，因此P一定没有右孩子节点
			} else {
				parent.left = p.left; // 这里的parent实际上是current节点（没有执行上面的循环）
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
		BinaryTree root = new BinaryTree(array[0]); // 创建二叉树tree1
		for (int i = 1; i < array.length; i++) {
			root.insert(root, array[i]); // 向二叉树中插入数据
		}

		int[] array1 = { 12, 76, 35, 22, 16, 48, 90, 46, 9, 40 };
		BinaryTree root1 = new BinaryTree(array1[0]); // 创建二叉树tree2
		for (int i = 1; i < array1.length; i++) {
			root1.insert(root1, array1[i]); // 向二叉树中插入数据
		}

		System.out.println(isSameTree(root, root1)); // 比较两颗二叉树

		System.out.println("先根遍历（递归实现）：");
		preOrder(root);
		System.out.println();

		System.out.println("先根遍历（非递归实现）：");
		iterativePreorder2(root);
		System.out.println();

		System.out.println("中根遍历（递归实现）：");
		inOrder(root);
		System.out.println();

		System.out.println("中根遍历（非递归实现）：");
		iterativeInorder2(root);
		System.out.println();

		System.out.println("后根遍历（递归实现）：");
		postOrder(root);
		System.out.println();

		System.out.println("后根遍历（非递归实现）：");
		iterativePostorder2(root);
		System.out.println();
		iterativePostorder3(root);
		System.out.println();

		System.out.println("层次遍历（递归）");
		levelOrderStack(root);

//		在二叉排序树中删除并插入某个节点，得到的二叉排序树是否和原来的相同
//		root.insert(root, 47);    //Binary tree node insertion
//		delete(root, 12);  //Binary tree node delete
		delete(root, 48);
//		root.insert(root, 45);
		levelOrderStack(root);
		/*
		 * 1.若是叶子节点， 则相同;  2.否则的话不相同
		 */
		System.out.println(maxDepth(root1));    // 求二叉树的最大深度(maxDepth)

		System.out.println(getNodeNumKthLevel(root, 3)); // 求二叉树第K层的节点数

		System.out.println(LeavesNodeNum(root1));   // 求二叉树叶子节点的个数

		levelOrderStack(root1);
		invertTree1(root1);
		levelOrderStack(root1);

//		BinaryTree node = BinaryTree findLowestCommonAncestor(root,s BinaryTree p1, BinaryTree p2);

//		System.out.println("请输入找查找的关键字：");
//		Scanner sc = new Scanner(System.in);
//		while (true) {
//			int key = sc.nextInt();
//			System.out.println(searchKey3(root, key));
//		}


	}
}
