#include "max_freq_stack.h"

#include <gtest/gtest.h>

TEST(MaxFreqStack, InterleavingElements) {
  FreqStack stack;
  stack.Push(1);
  stack.Push(2);
  stack.Push(1);
  stack.Push(2);
  stack.Push(1);
  stack.Push(2);
  EXPECT_EQ(stack.Pop(), 2);
  EXPECT_EQ(stack.Pop(), 1);
  EXPECT_EQ(stack.Pop(), 2);
  EXPECT_EQ(stack.Pop(), 1);
  EXPECT_EQ(stack.Pop(), 2);
  EXPECT_EQ(stack.Pop(), 1);
}

TEST(MaxFreqStack, DescriptionTest) {
  FreqStack stack;
  stack.Push(5);
  stack.Push(7);
  stack.Push(5);
  stack.Push(7);
  stack.Push(4);
  stack.Push(5);
  EXPECT_EQ(stack.Pop(), 5);
  EXPECT_EQ(stack.Pop(), 7);
  EXPECT_EQ(stack.Pop(), 5);
  EXPECT_EQ(stack.Pop(), 4);
  EXPECT_EQ(stack.Pop(), 7);
  EXPECT_EQ(stack.Pop(), 5);
}
