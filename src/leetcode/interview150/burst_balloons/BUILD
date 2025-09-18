cc_library(
  name = "burst_balloons",
  srcs = ["burst_balloons.cc"],
  hdrs = ["burst_balloons.h"],
)


cc_test(
  name = "burst_balloons_test",
  size = "small",
  srcs = ["burst_balloons_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":burst_balloons",
  ],
)
