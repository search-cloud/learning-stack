# Definition for singly-linked list.

class ListNode(object):
    def __init__(self, x):
        self.val = x
        self.next = None

class Solution(object):
    def swapPairs(self, head):
        """
        :type head: ListNode
        :rtype: ListNode
        """
        pre, pre.next = self, head
        while pre.next and pre.next.next :
            p1 = pre.next
            p2 = p1.next
            pre.next, p2.next, p1.next = p2, p1, p2.next
            pre = p1

        return self.next
