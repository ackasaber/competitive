cc_library(
  name = "range_and",
  srcs = ["range_and.cc"],
  hdrs = ["range_and.h"],
)


cc_test(
  name = "range_and_test",
  size = "small",
  srcs = ["range_and_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":range_and",
  ],
)
