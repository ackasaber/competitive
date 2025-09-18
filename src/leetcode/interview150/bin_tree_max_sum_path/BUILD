cc_library(
  name = "max_sum_path",
  srcs = ["max_sum_path.cc"],
  hdrs = ["max_sum_path.h"],
)


cc_test(
  name = "max_sum_path_test",
  size = "small",
  srcs = ["max_sum_path_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":max_sum_path",
  ],
)
