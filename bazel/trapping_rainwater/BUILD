cc_library(
  name = "trapping_rainwater",
  srcs = ["trapping_rainwater.cc"],
  hdrs = ["trapping_rainwater.h"],
  deps = ["@abseil-cpp//absl/types:span"],
)

cc_test(
  name = "trapping_rainwater_test",
  size = "small",
  srcs = ["trapping_rainwater_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":trapping_rainwater",
  ],
)
