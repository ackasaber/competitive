#include "insert_interval.h"

#include <gtest/gtest.h>
#include <iostream>

bool operator==(const Interval& u, const Interval& v)
{
  return std::tie(u.Start, u.End) == std::tie(v.Start, v.End);
}

std::ostream& operator<<(std::ostream& out, const Interval& interval)
{
  return out << "(" << interval.Start << "," << interval.End << ")";
}

TEST(InsertIntervalTest, IntersectLeft) {
  std::vector<Interval> intervals = {{1, 3}, {6, 9}};
  InsertInterval(intervals, {2, 5});
  std::vector<Interval> expected = {{1, 5}, {6, 9}};
  EXPECT_EQ(intervals, expected);
}

TEST(InsertIntervalTest, SeveralIntersections) {
  std::vector<Interval> intervals = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
  InsertInterval(intervals, {4, 8});
  std::vector<Interval> expected = {{1, 2}, {3, 10}, {12, 16}};
  EXPECT_EQ(intervals, expected);
}

TEST(InsertIntervalTest, SwallowedInterval) {
  std::vector<Interval> intervals = {{1, 3}, {6, 9}};
  InsertInterval(intervals, {6, 8});
  std::vector<Interval> expected = {{1, 3}, {6, 9}};
  EXPECT_EQ(intervals, expected);
}

TEST(InsertIntervalTest, IsolatedInterval) {
  std::vector<Interval> intervals = {{1, 3}, {6, 9}};
  InsertInterval(intervals, {4, 5});
  std::vector<Interval> expected = {{1, 3}, {4, 5}, {6, 9}};
  EXPECT_EQ(intervals, expected);
}

TEST(InsertIntervalTest, InsertToEmpty) {
  std::vector<Interval> intervals;
  InsertInterval(intervals, {1, 2});
  std::vector<Interval> expected = {{1, 2}};
  EXPECT_EQ(intervals, expected);
}

TEST(InsertIntervalTest, FullRange) {
  std::vector<Interval> intervals = {{1, 3}, {4, 5}, {6, 9}};
  InsertInterval(intervals, {1, 10});
  std::vector<Interval> expected = {{1, 10}};
  EXPECT_EQ(intervals, expected);
}