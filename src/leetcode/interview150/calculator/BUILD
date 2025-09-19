cc_library(
  name = "calculator",
  srcs = ["calculator.cc"],
  hdrs = ["calculator.h"],
)


cc_test(
  name = "calculator_test",
  size = "small",
  srcs = ["calculator_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":calculator",
  ],
)
