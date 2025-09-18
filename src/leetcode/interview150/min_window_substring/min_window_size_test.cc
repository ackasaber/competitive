#include "min_window_size.h"

#include <gtest/gtest.h>

TEST(MinWindowSize, DescriptionTest) {
  EXPECT_EQ(MinContainingSubstring("ADOBECODEBANC", "ABC"), "BANC");
  EXPECT_EQ(MinContainingSubstring("a", "a"), "a");
  EXPECT_EQ(MinContainingSubstring("a", "aa"), "");
}
