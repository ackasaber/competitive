#include "unique_paths2.h"

int CountUniquePaths(const std::vector<std::vector<int>>& obstacleGrid)
{
    size_t m = obstacleGrid.size();
    size_t n = obstacleGrid[0].size();
    std::vector<std::vector<int>> wayCount(m);

    if (obstacleGrid[0][0] == 1) {
        return 0;
    }

    wayCount[0].assign(n, 0);
    wayCount[0][0] = 1;

    for (size_t j = 1; j < n; j++) {
        wayCount[0][j] = obstacleGrid[0][j] == 1 ? 0 : wayCount[0][j-1];
    }

    for (size_t i = 1; i < m; i++) {
        wayCount[i].assign(n, 0);
        wayCount[i][0] = obstacleGrid[i][0] == 1 ? 0 : wayCount[i-1][0];

        for (size_t j = 1; j < n; j++) {
            wayCount[i][j] = obstacleGrid[i][j] == 1 ? 0 : wayCount[i-1][j] + wayCount[i][j-1];
        }
    }

    return wayCount[m-1][n-1];
}