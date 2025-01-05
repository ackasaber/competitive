#include "course_schedule.h"

#include <gtest/gtest.h>

TEST(CourseSchedule, DescriptionTest) {
  EXPECT_TRUE(CanFinish(2, {{1, 0}}));
  EXPECT_FALSE(CanFinish(2, {{1, 0}, {0, 1}}));
}

TEST(CourseSchedule, MyTests) {
  EXPECT_TRUE(CanFinish(2000, {}));
  EXPECT_TRUE(CanFinish(3, {{0, 1}, {1, 2}, {0, 2}}));
  EXPECT_FALSE(CanFinish(3, {{2, 0}, {1, 0}, {0, 2}}));
  EXPECT_FALSE(CanFinish(1, {{0, 0}}));
  EXPECT_TRUE(CanFinish(5, {{0, 1}, {4, 2}, {2, 0}}));
}