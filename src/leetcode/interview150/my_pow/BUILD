cc_library(
  name = "my_pow",
  srcs = ["my_pow.cc"],
  hdrs = ["my_pow.h"],
)


cc_test(
  name = "my_pow_test",
  size = "small",
  srcs = ["my_pow_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":my_pow",
  ],
)
