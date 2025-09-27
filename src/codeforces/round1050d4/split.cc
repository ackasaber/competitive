#include "split.h"

#include <cassert>
#include <vector>

uint64_t CountAwesomeSubarrays(std::span<uint32_t> a, uint32_t k) {
  size_t n = a.size();
  std::vector<uint32_t> freq(n);

  for (auto x: a) {
    assert(1 <= x && x <= n);
    ++freq[x-1];
  }

  for (auto &f: freq) {
    if (f % k != 0)
      return 0;
    f /= k;
  }

  // From now on, freq contains maximum frequencies of input values per
  // multiset in an "awesome" split of the input.

  // Sliding window for "maximum" awesome subarrays.
  size_t start = 0;
  // Frequency of values in the sliding window.
  std::vector<uint32_t> current(n);
  uint64_t count = 0;

  for (size_t end = 0; end < n; ++end) {
    auto x = a[end]-1;
    ++current[x];

    while (start <= end && current[x] > freq[x]) {
      --current[a[start]-1];
      ++start;
    }

    // Take into account all solutions that end on index 'end'.
    count += end - start + 1;
  }

  return count;
}
