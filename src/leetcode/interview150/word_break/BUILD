cc_library(
  name = "word_break",
  srcs = ["word_break.cc"],
  hdrs = ["word_break.h"],
)


cc_test(
  name = "word_break_test",
  size = "small",
  srcs = ["word_break_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":word_break",
  ],
)
