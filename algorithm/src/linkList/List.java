package linkList;

public class List {
	public static void main(String[] args) {
		Link l = new Link();
		l.addNode("A");
		l.addNode("B");
		l.addNode("C");
		l.addNode("D");
		System.out.println("ԭ����");
		l.print();
		String searchNode = "B";
		System.out.println("���ҽڵ�:" + searchNode);
		String result = l.searchNode(searchNode) ? "�ҵ�!" : "û�ҵ�!";
		System.out.println("���ҽ����" + result);
		System.out.println("ɾ���ڵ㣺" + searchNode);
		l.deleteNode(searchNode);
		System.out.println("ɾ���ڵ�������");
		l.print();
	} 
}


class Link { // ������
	class Node { // ����ÿһ���ڵ㣬�˴�Ϊ�˷���ֱ�Ӷ�����ڲ���
		private String data; // �ڵ������
		private Node next; // ������һ���ڵ�

		public Node(String data) { // ͨ�����췽�����ýڵ�����
			this.data = data;
		}

		public void add(Node node) { // ���ӽڵ�
			if (this.next == null) { // �����һ���ڵ�Ϊ�գ�����½ڵ���뵽next��λ����
				this.next = node;
			} else { // �����һ���ڵ㲻Ϊ�գ��������next
				this.next.add(node);
			}
		}

		public void print() { // ��ӡ�ڵ�
			if (this.next != null) {
				System.out.print(this.data + "-->");
				this.next.print();
			} else {
				System.out.print(this.data + "\n");
			}
		}

		public boolean search(String data) { // �ڲ������ڵ�ķ���
			if (this.data.equals(data)) {
				return true;
			}
			if (this.next != null) {
				return this.next.search(data);
			} else {
				return false;
			}
		}

		public void delete(Node previous, String data) { // �ڲ�ɾ���ڵ�ķ���
			if (this.data.equals(data)) {
				previous.next = this.next;
			} else {
				if (this.next != null) {
					this.next.delete(this, data);
				}
			}
		}
	}

	private Node root; // ����ͷ�ڵ�

	public void addNode(String data) { // ����������ӽڵ�
		Node newNode = new Node(data); // Ҫ����Ľڵ�
		if (this.root == null) { // û��ͷ�ڵ㣬��Ҫ����Ľڵ�Ϊͷ�ڵ�
			this.root = newNode;
		} else { // �����ͷ�ڵ㣬����ýڵ���ķ����Զ�����
			this.root.add(newNode);
		}
	}

	public void print() { // չʾ�б�ķ���
		if (root != null) { // ��������ڽڵ��ʱ�����չʾ
			this.root.print();
		}
	}

	public boolean searchNode(String data) { // ��������Ѱ��ָ�����ݵĽڵ�
		return root.search(data); // �����ڲ������ڵ�ķ���
	}

	public void deleteNode(String data) { // ��������ɾ��ָ�����ݵĽڵ�
		if (root.data.equals(data)) { // �����ͷ�ڵ�
			if (root.next != null) {
				root = root.next;
			} else {
				root = null;
			}
		} else {
			root.next.delete(this.root, data);
		}
	}
}

