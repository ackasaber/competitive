#include <memory>
#include <algorithm>

#include "trapping_rainwater.h"

using absl::Span;

int trap_rainwater(Span<const int> heights) {
  int n = heights.size();
  int max_left = 0;
  int max_right = 0;
  int volume = 0;
  int left = 0;
  int right = n - 1;
  // Invariant: heights[0..left-1] and heights[right+1..n-1] are accounted.

  while (left <= right) {
    if (max_left > max_right) {
      // heights[right+1] cannot be a global maximum
      max_right = std::max(max_right, heights[right]);
      volume += max_right - heights[right];
      right--;
    } else {
      // heights[left] cannot be a single global maximum
      max_left = std::max(max_left, heights[left]);
      volume += max_left - heights[left];
      left++;
    }
  }

  return volume;
}