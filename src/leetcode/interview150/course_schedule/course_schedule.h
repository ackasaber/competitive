#pragma once

#include <vector>
#include "prerequisite.h"

bool CanFinish(int courseCount, const std::vector<Prerequisite>& prerequisites);
std::vector<int> Schedule(int courseCount, const std::vector<Prerequisite>& prerequisites);