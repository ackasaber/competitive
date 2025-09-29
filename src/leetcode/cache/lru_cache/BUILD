# LeetCode
# Problem 146. LRU Cache
# https://leetcode.com/problems/lru-cache/description/

cc_library(
    name = "lru_cache",
    srcs = ["lru_cache_lowlevel.cc"],
    hdrs = ["lru_cache_lowlevel.h"],
)

cc_test(
    name = "lru_cache_test",
    size = "small",
    srcs = ["lru_cache_test.cc"],
    deps = [
        ":lru_cache",
        "@googletest//:gtest_main",
    ],
)
