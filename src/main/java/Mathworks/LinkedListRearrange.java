package Mathworks;

import common.ListNode;

/**
 * @author lzn
 * @date 2023/08/13 21:11
 * @description Given a chained table of odd length, it is required that the odd bits be placed in the first half and the even bits in the second half
 * Requirements:
 * 1. No extra space, that means only constant space complexity allowed
 * 2. Modify linked list in place instead of swap value
 */
public class LinkedListRearrange {

    public ListNode rearrangeLinkedList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;

        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;

            even.next = odd.next;
            even = even.next;
        }

        odd.next = evenHead;

        return head;
    }

    public static void main(String[] args) {
        // Create and populate the linked list
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        LinkedListRearrange rearranger = new LinkedListRearrange();
        rearranger.rearrangeLinkedList(head);

        // Print the modified linked list
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
    }
}