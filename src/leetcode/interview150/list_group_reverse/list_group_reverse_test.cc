#include "list_group_reverse.h"

#include <gtest/gtest.h>

TEST(ListGroupReverse, Test1) {
    constexpr size_t n = 5;
    ListNode nodes[n];

    for (size_t i = 0; i+1 < n; i++) {
        nodes[i].val = (i+1);
        nodes[i].next = &nodes[i+1];
    }

    nodes[n-1].val = n;
    nodes[n-1].next = nullptr;

    ListNode* reversed = ReverseGroups(&nodes[0], 2);
    EXPECT_EQ(reversed, &nodes[1]);
    EXPECT_EQ(nodes[0].next, &nodes[3]);
    EXPECT_EQ(nodes[1].next, &nodes[0]);
    EXPECT_EQ(nodes[2].next, &nodes[4]);
    EXPECT_EQ(nodes[3].next, &nodes[2]);
    EXPECT_EQ(nodes[4].next, nullptr);
}

TEST(ListGroupReverse, Test2) {
    constexpr size_t n = 5;
    ListNode nodes[n];

    for (size_t i = 0; i+1 < n; i++) {
        nodes[i].val = (i+1);
        nodes[i].next = &nodes[i+1];
    }

    nodes[n-1].val = n;
    nodes[n-1].next = nullptr;

    ListNode* reversed = ReverseGroups(&nodes[0], 3);
    EXPECT_EQ(reversed, &nodes[2]);
    EXPECT_EQ(nodes[2].next, &nodes[1]);
    EXPECT_EQ(nodes[1].next, &nodes[0]);
    EXPECT_EQ(nodes[0].next, &nodes[3]);
    EXPECT_EQ(nodes[3].next, &nodes[4]);
    EXPECT_EQ(nodes[4].next, nullptr);
}
