#include "lfu_cache.h"

#include <gtest/gtest.h>

TEST(LfuCache, DescriptionTest) {
  LfuCache cache(2);
  cache.Put(1, 1);
  cache.Put(2, 2);
  EXPECT_EQ(cache.Get(1), 1);
  cache.Put(3, 3);
  EXPECT_EQ(cache.Get(2), -1);
  EXPECT_EQ(cache.Get(3), 3);
  cache.Put(4, 4);
  EXPECT_EQ(cache.Get(1), -1);
  EXPECT_EQ(cache.Get(3), 3);
  EXPECT_EQ(cache.Get(4), 4);
}

TEST(LfuCache, EmptyTest) {
  LfuCache cache(10);
  EXPECT_EQ(cache.Get(0), -1);
}

TEST(LfuCache, PutTest) {
  LfuCache cache(2);
  cache.Put(1, 1);
  EXPECT_EQ(cache.Get(1), 1);
  cache.Put(1, 2);
  EXPECT_EQ(cache.Get(1), 2);
}

TEST(LfuCache, LfuEvictionTest) {
  LfuCache cache(2);
  cache.Put(1, 1);
  cache.Put(2, 2);
  cache.Put(2, 3);
  cache.Put(2, 4);
  EXPECT_EQ(cache.Get(1), 1);
  // 1 x 2, 2 x 3
  cache.Put(3, 1);
  // 1 has to be evicted
  EXPECT_EQ(cache.Get(1), -1);
  EXPECT_EQ(cache.Get(2), 4);
  EXPECT_EQ(cache.Get(3), 1);
}
