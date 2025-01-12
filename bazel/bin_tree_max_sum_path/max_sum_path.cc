#include "max_sum_path.h"

#include <algorithm>

using std::max;

int max(int x, int y, int z) { return max(x, max(y, z)); }

struct MaxSumComputer {
  int maxSum;
  int MaxSumBranch(TreeNode* root);
};

int MaxSumPath(TreeNode* root) {
  MaxSumComputer comp{root->val};
  comp.MaxSumBranch(root);
  return comp.maxSum;
}

int MaxSumComputer::MaxSumBranch(TreeNode* root) {
  if (!root) {
    return 0;
  }

  int maxLeftBranchSum = max(0, MaxSumBranch(root->left));
  int maxRightBranchSum = max(0, MaxSumBranch(root->right));
  int maxBranchLoad = max(maxLeftBranchSum, maxRightBranchSum, maxLeftBranchSum + maxRightBranchSum);
  maxSum = max(maxSum, root->val + maxBranchLoad);
  return max(maxLeftBranchSum, maxRightBranchSum) + root->val;
}
