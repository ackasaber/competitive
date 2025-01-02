#pragma once

#include <memory>

struct TreeNode {
  TreeNode* left;
  TreeNode* right;
};

int CompleteTreeSize(TreeNode* root);

struct CompleteTree {
  explicit CompleteTree(int size);
  TreeNode* Root();

 private:
  std::unique_ptr<TreeNode[]> nodes_;
  TreeNode* root_;
};