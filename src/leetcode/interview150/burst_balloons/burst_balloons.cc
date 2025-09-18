#include "burst_balloons.h"
#include <algorithm>

int FindMinArrowShots(std::vector<BalloonPosition>& balloons)
{
    std::sort(balloons.begin(), balloons.end(),
        [](const BalloonPosition& u, const BalloonPosition& v) {
            return u.Right < v.Right;
        });

    int count = 0;

    if (!balloons.empty()) {
        count++;
        auto it = balloons.cbegin();
        int lastShot = it->Right;
        it++;

        while (it != balloons.cend()) {
            if (it->Left > lastShot) {
                count++;
                lastShot = it->Right;
            }
            it++;
        }
    }

    return count;
}
