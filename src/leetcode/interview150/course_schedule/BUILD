cc_library(
  name = "course_schedule",
  srcs = ["course_schedule.cc", "course_schedule2.cc"],
  hdrs = ["prerequisite.h", "course_schedule.h", "course_graph.h"],
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
