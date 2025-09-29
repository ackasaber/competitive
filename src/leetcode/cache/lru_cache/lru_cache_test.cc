#include <gtest/gtest.h>

#include "lru_cache_lowlevel.h"
#include "lru_cache_highlevel.h"

template<typename C>
struct LruCacheTest: testing::Test {
  C cacheBuilder;

  auto MakeCache(size_t capacity) {
    return this->cacheBuilder.MakeCache(capacity);
  }
};

struct LowLevelCacheBuilder {
  static LruCacheLowLevel MakeCache(size_t capacity) {
    return LruCacheLowLevel(capacity, 2, 5);
  }
};

struct HighLevelCacheBuilder {
  static LruCacheHighLevel MakeCache(size_t capacity) {
    return LruCacheHighLevel(capacity);
  }
};

using LruCacheTypes = testing::Types<LowLevelCacheBuilder, HighLevelCacheBuilder>;
TYPED_TEST_SUITE(LruCacheTest, LruCacheTypes);

TYPED_TEST(LruCacheTest, BasicTest) {
  auto cache = this->MakeCache(10);
  cache.Put(1, 2);
  cache.Put(2, 10);
  EXPECT_EQ(cache.Get(1), 2);
  EXPECT_EQ(cache.Get(2), 10);
}

TYPED_TEST(LruCacheTest, BasicEvictionTest) {
  auto cache = this->MakeCache(2);
  cache.Put(1, 2);
  cache.Put(2, 10);
  cache.Put(3, 3);
  EXPECT_EQ(cache.Get(1), -1);
  EXPECT_EQ(cache.Get(2), 10);
  EXPECT_EQ(cache.Get(3), 3);
}

TYPED_TEST(LruCacheTest, EvictWithGetTest) {
  auto cache = this->MakeCache(2);
  cache.Put(1, 2);
  cache.Put(2, 10);
  (void) cache.Get(1);
  cache.Put(3, 3);
  EXPECT_EQ(cache.Get(2), -1);
  EXPECT_EQ(cache.Get(1), 2);
  EXPECT_EQ(cache.Get(3), 3);
}

TYPED_TEST(LruCacheTest, DescriptionTest) {
  auto cache = this->MakeCache(2);
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

TYPED_TEST(LruCacheTest, FailedTest1) {
  auto cache = this->MakeCache(1);
  cache.Put(2, 1);
  EXPECT_EQ(cache.Get(2), 1);
  cache.Put(3, 2);
  EXPECT_EQ(cache.Get(2), -1);
  EXPECT_EQ(cache.Get(3), 2);
}
