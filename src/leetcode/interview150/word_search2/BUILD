cc_library(
  name = "word_search2",
  srcs = ["word_search2.cc"],
  hdrs = ["word_search2.h"],
)


cc_test(
  name = "word_search2_test",
  size = "small",
  srcs = ["word_search2_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":word_search2",
  ],
)
