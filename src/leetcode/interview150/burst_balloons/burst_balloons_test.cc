#include "burst_balloons.h"

#include <gtest/gtest.h>

TEST(BurstBalloonsTest, DescriptionTest) {
    std::vector<BalloonPosition> balloons = {{10, 16}, {2, 8}, {1, 6}, {7, 12}};
    EXPECT_EQ(FindMinArrowShots(balloons), 2);
}

TEST(BurstBalloonsTest, NonIntersectingTest) {
    std::vector<BalloonPosition> balloons = {{1, 2}, {3, 4}, {5, 6}, {7, 8}};
    EXPECT_EQ(FindMinArrowShots(balloons), 4);
}

TEST(BurstBalloonsTest, TouchingTest) {
    std::vector<BalloonPosition> balloons = {{1, 2}, {2, 3}, {3, 4}, {4, 5}};
    EXPECT_EQ(FindMinArrowShots(balloons), 2);
}