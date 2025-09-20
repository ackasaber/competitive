#include "list_group_reverse.h"

namespace {
ListNode* insertAfter(ListNode* after, ListNode* head, ListNode* node) {
    if (after == nullptr) {
        node->next = head;
        head = node;
    } else {
        node->next = after->next;
        after->next = node;
    }
    return head;
}
}

ListNode* ReverseGroups(ListNode* head, int k) {
    ListNode* head0 = head;
    ListNode* newList = nullptr;
    ListNode* insertionPoint = nullptr;

    while (head != nullptr) {
        int i = 0;
        ListNode* node = head;
        ListNode* first = head;

        while (i < k && node != nullptr) {
            ListNode* nextNode = node->next;
            newList = insertAfter(insertionPoint, newList, node);
            node = nextNode;
            i++;
        }

        head = node;

        if (i < k) {
            ListNode* node = insertionPoint->next;
            // Othwerwise it gets looped.
            insertionPoint->next = nullptr;

            while (node != nullptr) {
                ListNode* nextNode = node->next;
                newList = insertAfter(insertionPoint, newList, node);
                node = nextNode;
            }
        }

        insertionPoint = first;
    }

    return newList;
}
