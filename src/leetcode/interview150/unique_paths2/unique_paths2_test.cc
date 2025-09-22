#include "unique_paths2.h"

#include <gtest/gtest.h>

TEST(UniquePaths, OneObstacle) {
    std::vector<std::vector<int>> grid = {{0, 0, 0},{0, 1, 0},{0, 0, 0}};
    EXPECT_EQ(CountUniquePaths(grid), 2);
}

TEST(UniquePaths, OnePath) {
    std::vector<std::vector<int>> grid = {{0, 1}, {0, 0}};
    EXPECT_EQ(CountUniquePaths(grid), 1);
}
