#include "complete_tree_size.h"

TreeNode* CompleteTree::Root() { return root_; }

CompleteTree::CompleteTree(int size)
    : nodes_(std::make_unique<TreeNode[]>(size)) {
  for (int i = 0; i < size; i++) {
    nodes_[i].left = 2 * i + 1 < size ? &nodes_[2 * i + 1] : nullptr;
    nodes_[i].right = 2 * i + 2 < size ? &nodes_[2 * i + 2] : nullptr;
  }

  root_ = size > 0 ? &nodes_[0] : nullptr;
}

int Height1(TreeNode* node) {
  int height = 0;

  while (node) {
    node = node->left;
    height++;
  }

  return height;
}

// O(log^2 n)

int CompleteTreeSize(TreeNode* root) {
  int h = Height1(root);
  int count = 0;

  while (root) {
    int hR = Height1(root->right);
    count += 1 << hR;

    if (hR + 1 == h) {
      // left subtree is full
      root = root->right;
    } else {
      // right subtree must be full
      root = root->left;
    }

    h--;
  }

  return count;
}