/**
 * Definition for singly-linked list.
 */
class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
	}
}

public class AddTwoNumbers {
	public static void main(String[] args) {
		int arr1[] = { 2, 4, 5 };
		int arr2[] = { 3, 7, 6 };
		ListNode headNodel1 = new ListNode(arr1[0]);
		ListNode headNodel2 = new ListNode(arr2[0]);
		ListNode l1 = headNodel1, l2 = headNodel2;
	
		for (int i = 1; i < arr1.length; i++) {
			l1.next = new ListNode(arr1[i]);
			l1 = l1.next;
		}
		for (int j = 1; j < arr2.length; j++) {
			l2.next = new ListNode(arr2[j]);
			l2 = l2.next;
		}
		l1 = headNodel1;                              //很重要，重新从到头节点
		StringBuilder s1 = new StringBuilder();
		while(l1 != null){
			s1.append(l1.val);
			l1 = l1.next;
		}
		System.out.println(s1.reverse().toString());	

		l2 = headNodel2;
		StringBuilder s2 = new StringBuilder();
		while(l2 != null){
			s2.append(l2.val);
			l2 = l2.next;
		}
		System.out.println(s2.reverse().toString());
		
		ListNode r = addTwoNumbers(headNodel1,headNodel2);
		StringBuilder s = new StringBuilder();
		while(r != null){
			s.append(r.val);
			r = r.next;
		}
		System.out.println(s.reverse().toString());
	}

	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode dummyHead = new ListNode(0);
		ListNode p = l1, q = l2, curr = dummyHead;
		int carry = 0;
		while (p != null || q != null) {
			int x = (p != null) ? p.val : 0;
			int y = (q != null) ? q.val : 0;
			int sum = x + y + carry;
			carry = sum / 10;
			curr.next = new ListNode(sum % 10);
			curr = curr.next;
			if (p != null)
				p = p.next;
			if (q != null)
				q = q.next;
		}
		if (carry > 0) {
			curr.next = new ListNode(carry);
		}
		return dummyHead.next;
	}

}
