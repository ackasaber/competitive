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

// Testing when the solution exists.
bool CheckSchedule(int courseCount,
                   const std::vector<Prerequisite>& prerequisites);
// Testing when there's no solution.
bool CheckNoSchedule(int courseCount,
                     const std::vector<Prerequisite>& prerequisites);

TEST(CourseSchedule2, DescriptionTest) {
  EXPECT_TRUE(CheckSchedule(2, {{1, 0}}));
  EXPECT_TRUE(CheckSchedule(4, {{1, 0}, {2, 0}, {3, 1}, {3, 2}}));
  EXPECT_TRUE(CheckSchedule(1, {}));
}

TEST(CourseSchedule2, MyTests) {
  EXPECT_TRUE(CheckNoSchedule(2, {{1, 0}, {0, 1}}));
  EXPECT_TRUE(CheckNoSchedule(3, {{2, 0}, {1, 0}, {0, 2}}));
  EXPECT_TRUE(CheckNoSchedule(1, {{0, 0}}));

  EXPECT_TRUE(CheckSchedule(2000, {}));
  EXPECT_TRUE(CheckSchedule(3, {{0, 1}, {1, 2}, {0, 2}}));
  EXPECT_TRUE(CheckSchedule(5, {{0, 1}, {4, 2}, {2, 0}}));
}

bool CheckSchedule(int courseCount,
                   const std::vector<Prerequisite>& prerequisites) {
  auto schedule = Schedule(courseCount, prerequisites);

  if (schedule.size() != courseCount) {
    return false;
  }

  auto index = std::make_unique<int[]>(courseCount);
  for (int i = 0; i < courseCount; i++) {
    index[schedule[i]] = i;
  }

  for (const auto& p : prerequisites) {
    if (index[p.course] < index[p.prereq]) {
      return false;
    }
  }

  return true;
}

bool CheckNoSchedule(int courseCount,
                     const std::vector<Prerequisite>& prerequisites) {
  auto schedule = Schedule(courseCount, prerequisites);
  return (schedule.size() != courseCount);
}