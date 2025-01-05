#pragma once

#include <vector>

struct Prerequisite {
  int course;
  int prereq;
};

bool CanFinish(int courseCount, const std::vector<Prerequisite>& prerequisites);