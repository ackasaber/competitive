#include "lru_cache.h"

#include <gtest/gtest.h>

TEST(LruCache, BasicTest) {
  LruCache cache(10, 2, 5);
  cache.Put(1, 2);
  cache.Put(2, 10);
  EXPECT_EQ(cache.Get(1), 2);
  EXPECT_EQ(cache.Get(2), 10);
}

TEST(LruCache, BasicEvictionTest) {
  LruCache cache(2, 2, 5);
  cache.Put(1, 2);
  cache.Put(2, 10);
  cache.Put(3, 3);
  EXPECT_EQ(cache.Get(1), -1);
  EXPECT_EQ(cache.Get(2), 10);
  EXPECT_EQ(cache.Get(3), 3);
}

TEST(LruCache, EvictWithGetTest) {
  LruCache cache(2, 2, 5);
  cache.Put(1, 2);
  cache.Put(2, 10);
  (void) cache.Get(1);
  cache.Put(3, 3);
  EXPECT_EQ(cache.Get(2), -1);
  EXPECT_EQ(cache.Get(1), 2);
  EXPECT_EQ(cache.Get(3), 3);
}

TEST(LruCache, DescriptionTest) {
  LruCache cache(2, 34, 18);
  cache.Put(1, 1);
  cache.Put(2, 2);
  EXPECT_EQ(cache.Get(1), 1);
  cache.Put(3, 3);
  EXPECT_EQ(cache.Get(2), -1);
  cache.Put(4, 4);
  EXPECT_EQ(cache.Get(1), -1);
  EXPECT_EQ(cache.Get(3), 3);
  EXPECT_EQ(cache.Get(4), 4);
}

TEST(LruCache, FailedTest1) {
  LruCache cache(1, 34, 18);
  cache.Put(2, 1);
  EXPECT_EQ(cache.Get(2), 1);
  cache.Put(3, 2);
  EXPECT_EQ(cache.Get(2), -1);
  EXPECT_EQ(cache.Get(3), 2);
}
