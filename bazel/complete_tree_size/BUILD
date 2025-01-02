cc_library(
  name = "complete_tree_size",
  srcs = ["complete_tree_size.cc"],
  hdrs = ["complete_tree_size.h"],
)

cc_test(
  name = "complete_tree_size_test",
  size = "small",
  srcs = ["complete_tree_size_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":complete_tree_size",
  ],
)
