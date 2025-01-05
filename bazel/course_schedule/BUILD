cc_library(
  name = "course_schedule",
  srcs = ["course_schedule.cc", "graph.cc"],
  hdrs = ["course_schedule.h", "graph.h"],
)

cc_test(
  name = "course_schedule_test",
  size = "small",
  srcs = ["course_schedule_test.cc"],
  deps = [
    "@googletest//:gtest_main",
    ":course_schedule",
  ],
)
