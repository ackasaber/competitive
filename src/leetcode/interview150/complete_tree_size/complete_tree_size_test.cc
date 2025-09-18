#include "complete_tree_size.h"

#include <gtest/gtest.h>

TEST(CompleteTreeSize, DescriptionTest) {
  CompleteTree tree6(6);
  EXPECT_EQ(CompleteTreeSize(tree6.Root()), 6);

  CompleteTree tree0(0);
  EXPECT_EQ(CompleteTreeSize(tree0.Root()), 0);

  CompleteTree tree1(1);
  EXPECT_EQ(CompleteTreeSize(tree1.Root()), 1);
}

TEST(CompleteTreeSize, MyTest) {
  CompleteTree tree100(100);
  EXPECT_EQ(CompleteTreeSize(tree100.Root()), 100);

  CompleteTree tree255(255);
  EXPECT_EQ(CompleteTreeSize(tree255.Root()), 255);

  CompleteTree tree256(256);
  EXPECT_EQ(CompleteTreeSize(tree256.Root()), 256);

  CompleteTree big_tree(50000);
  EXPECT_EQ(CompleteTreeSize(big_tree.Root()), 50000);
}