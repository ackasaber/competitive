# LeetCode
# Problem 460. LFU Cache
# https://leetcode.com/problems/lfu-cache/description

cc_library(
    name = "lfu_cache",
    srcs = ["lfu_cache.cc"],
    hdrs = ["lfu_cache.h"],
)

cc_test(
    name = "lfu_cache_test",
    size = "small",
    srcs = ["lfu_cache_test.cc"],
    deps = [
        ":lfu_cache",
        "@googletest//:gtest_main",
    ],
)
