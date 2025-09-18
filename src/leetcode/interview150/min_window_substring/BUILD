cc_library(
  name = "min_window_size",
  srcs = ["min_window_size.cc"],
  hdrs = ["min_window_size.h"]
)


cc_test(
  name = "min_window_size_test",
  size = "small",
  srcs = ["min_window_size_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":min_window_size",
  ],
)
