#pragma once

#include <vector>

struct Interval {
  int Start;
  int End;
};

void InsertInterval(std::vector<Interval>& intervals, const Interval& x);
void InsertInterval2(std::vector<Interval>& intervals, const Interval& x);
