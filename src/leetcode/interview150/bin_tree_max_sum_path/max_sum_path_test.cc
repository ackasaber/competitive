#include "max_sum_path.h"

#include <gtest/gtest.h>

class MaxSumPathTest : public testing::Test {
  std::unique_ptr<TreeNode[]> storage_;
  static constexpr size_t capacity_ = 52;
  size_t size_;

 protected:
  MaxSumPathTest();
  TreeNode* n(int val, TreeNode* left, TreeNode* right);
  TreeNode* n(int val) { return n(val, nullptr, nullptr); }
};

TEST_F(MaxSumPathTest, DescriptionTest) {
  EXPECT_EQ(MaxSumPath(n(1, n(2), n(3))), 6);
  EXPECT_EQ(MaxSumPath(n(-10, n(9), n(20, n(15), n(7)))), 42);
}

TEST_F(MaxSumPathTest, MyTests) {
  EXPECT_EQ(MaxSumPath(n(-1)), -1);
  EXPECT_EQ(MaxSumPath(n(-1, n(-2), nullptr)), -1);
  EXPECT_EQ(MaxSumPath(n(-2, n(-1), nullptr)), -1);
  EXPECT_EQ(MaxSumPath(n(1, n(-2), n(3))), 4);
}

MaxSumPathTest::MaxSumPathTest()
    : storage_(std::make_unique<TreeNode[]>(capacity_)), size_(0) {}

TreeNode* MaxSumPathTest::n(int val, TreeNode* left, TreeNode* right) {
  if (size_ == capacity_) {
    throw std::runtime_error("no space in node arena");
  }

  TreeNode* node = &storage_[size_++];
  node->val = val;
  node->left = left;
  node->right = right;
  return node;
}
