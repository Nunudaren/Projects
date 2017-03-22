package tree;


public class BinaryTree {

	int data; // ���ڵ�����
	BinaryTree root;
	BinaryTree left; // ������
	BinaryTree right; // ������
	
	public BinaryTree(int data) // ʵ������������
	{
		this.data = data;
		left = null;
		right = null;
	}
	
	//Binary tree insertion	
	public void insert(BinaryTree root, int data) { // ��������в����ӽڵ�
		if (data == root.data) {
			return;
		} else if (data > root.data) // ���������ҽڵ㶼�ȸ��ڵ��
		{
			if (root.right == null) {
				root.right = new BinaryTree(data);
			} else {
				this.insert(root.right, data);
			}
		} else { // ����������ڵ㶼�ȸ��ڵ�С
			if (root.left == null) {
				root.left = new BinaryTree(data);
			} else {
				this.insert(root.left, data);
			}
		}
	}
}
