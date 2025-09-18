#pragma once

#include <vector>

struct BalloonPosition
{
    int Left;
    int Right;
};

int FindMinArrowShots(std::vector<BalloonPosition>& balloons);
