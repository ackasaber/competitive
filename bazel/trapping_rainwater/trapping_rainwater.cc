#include "trapping_rainwater.h"

using absl::Span;

int max_in(Span<const int> a, int initial);
int min_of(int a, int b);

int trap_rainwater(Span<const int> heights) {
  int volume = 0;
  int n = heights.size();

  for (int i = 0; i < n; i++) {
    int h1 = max_in(heights.first(i), 0);
    int h2 = max_in(heights.last(n - i - 1), 0);
    int level = min_of(h1, h2);
    int h = heights[i];

    if (level > h) volume += level - h;
  }

  return volume;
}

int max_in(Span<const int> a, int initial) {
  int max = initial;

  for (int x : a) {
    if (x > max) max = x;
  }

  return max;
}

int min_of(int a, int b) { return a < b ? a : b; }