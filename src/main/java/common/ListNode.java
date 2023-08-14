package common;

/**
 * @author lzn
 * @date 2022/10/24 18:09
 * @description
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode prev;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public ListNode(int val, ListNode next, ListNode prev) {
        this.val = val;
        this.next = next;
        this.prev = prev;
    }
}
