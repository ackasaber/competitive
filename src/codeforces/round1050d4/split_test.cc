#include "split.h"

#include <gtest/gtest.h>

TEST(Split, NoSolutions) {
  std::vector<uint32_t> a = {1, 1, 1};
  EXPECT_EQ(CountAwesomeSubarrays(a, 2), 0);
}

TEST(Split, BasicTests) {
  std::vector<uint32_t> a = {1, 2, 1, 2};
  EXPECT_EQ(CountAwesomeSubarrays(a, 2), 7);
  std::vector<uint32_t> b = {3, 3, 3, 3, 2, 2, 2, 2};
  EXPECT_EQ(CountAwesomeSubarrays(b, 2), 18);
  std::vector<uint32_t> c = {1, 1, 1, 1, 1, 1};
  EXPECT_EQ(CountAwesomeSubarrays(c, 3), 11);
}
