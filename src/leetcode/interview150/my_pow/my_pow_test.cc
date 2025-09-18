#include "my_pow.h"

#include <gtest/gtest.h>

TEST(MyPow, DescriptionTest) {
  EXPECT_DOUBLE_EQ(MyPow(2., 10), 1024.);
  EXPECT_DOUBLE_EQ(MyPow(2.1, 3), 9.261);
  EXPECT_DOUBLE_EQ(MyPow(2., -2), 0.25);
}

TEST(MyPow, MyTests) {
  EXPECT_DOUBLE_EQ(MyPow(0., 1000), 0.);
  EXPECT_DOUBLE_EQ(MyPow(-10000., 1), -10000.);
  EXPECT_DOUBLE_EQ(MyPow(-1.5, -2), 0.444444444444444444);
  // They DID have a test like this.
  EXPECT_DOUBLE_EQ(MyPow(1., -2147483648), 1.);
}