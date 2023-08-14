package Mathworks;

import common.ListNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListRearrangeTest {

    private LinkedListRearrange rearranger;

    private ListNode head;
    private ListNode result;

    @BeforeEach
    void populateData() {
        // Create and populate the linked list
        head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        result = new ListNode(1);
        result.next = new ListNode(3);
        result.next.next = new ListNode(5);
        result.next.next.next = new ListNode(2);
        result.next.next.next.next = new ListNode(4);

        rearranger = new LinkedListRearrange();
    }

    @Test
    void when_passNull_then_return_head() {
        assertEquals(head, rearranger.rearrangeLinkedList(head));
    }

    @Test
    void when_pass_practical_linkedList_then_swap_odd_and_even_bits() {
        ListNode ori = result, res = rearranger.rearrangeLinkedList(head);
        while (ori != null) {
            assertEquals(ori.val, res.val);
            ori = ori.next;
            res = res.next;
        }
        assertNull(res);
    }

}