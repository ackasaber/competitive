#include "range_and.h"

#include <gtest/gtest.h>

TEST(RangeAnd, DescriptionTest) {
  EXPECT_EQ(RangeAnd(5, 7), 4);
  EXPECT_EQ(RangeAnd(0, 0), 0);
  EXPECT_EQ(RangeAnd(1, 2147483647), 0);
}

TEST(RangeAnd, MyTests) {
  EXPECT_EQ(RangeAnd(123, 123), 123);
  //   1111011
  // & 1111100
  EXPECT_EQ(RangeAnd(123, 124), 120);
  
}