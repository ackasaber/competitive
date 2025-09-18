cc_library(
  name = "insert_interval",
  srcs = ["insert_interval.cc"],
  hdrs = ["insert_interval.h"],
)


cc_test(
  name = "insert_interval_test",
  size = "small",
  srcs = ["insert_interval_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":insert_interval",
  ],
)
