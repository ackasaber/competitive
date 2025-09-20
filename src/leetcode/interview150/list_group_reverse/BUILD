cc_library(
  name = "list_group_reverse",
  srcs = ["list_group_reverse.cc"],
  hdrs = ["list_group_reverse.h"],
)


cc_test(
  name = "list_group_reverse_test",
  size = "small",
  srcs = ["list_group_reverse_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":list_group_reverse",
  ],
)
