#pragma once

struct TreeNode {
  int val;
  TreeNode *left;
  TreeNode *right ;
};

int MaxSumPath(TreeNode* root);
