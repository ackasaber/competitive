#include "insert_interval.h"

namespace
{
size_t FindStart(const std::vector<Interval>& intervals, size_t left, size_t right, int x)
{
    // intervals[0..left-1].Start <= x, intervals[right..n-1].Start > x
    while (left < right)
    {
        size_t middle = left + (right - left)/2;
        if (intervals[middle].Start <= x)
            left = middle+1;
        else
            right = middle;
    }

    return left;
}

size_t FindEnd(const std::vector<Interval>& intervals, size_t left, size_t right, int x)
{
    // intervals[0..left-1].End < x, intervals[right..n-1].End >= x
    while (left < right)
    {
        size_t middle = left + (right - left)/2;
        if (intervals[middle].End < x)
            left = middle+1;
        else
            right = middle;
    }

    return left;
}
}

void InsertInterval2(std::vector<Interval>& intervals, const Interval& x)
{
    std::vector<Interval> result;
    auto it = intervals.cbegin();

    while (it != intervals.cend() && it->End < x.Start) {
        result.push_back(*it);
        it++;
    }

    int insertStart = x.Start;
    int insertEnd = x.End;

    if (it != intervals.cend()) {
        if (it->Start < x.Start) {
            insertStart = it->Start;
        }

        while (it != intervals.cend() && it->Start <= x.End) {
            it++;
        }

        if (it != intervals.cbegin() && (it-1)->Start <= x.End && (it-1)->End > insertEnd) {
            insertEnd = (it-1)->End;
        }
    }

    result.push_back({insertStart, insertEnd});

    while (it != intervals.cend()) {
        result.push_back(*it);
        it++;
    }

    intervals = result;
}

void InsertInterval(std::vector<Interval>& intervals, const Interval& x)
{
    size_t n = intervals.size();
    size_t i = FindEnd(intervals, 0, n, x.Start);

    if (i == n || x.End < intervals[i].Start) {
        intervals.insert(intervals.cbegin() + i, x);
        return;
    }

    // !(intervals[i].End < x.Start || x.End < intervals[i].Start)
    int insertStart = std::min(intervals[i].Start, x.Start);
    int insertEnd = x.End;
    size_t j = FindStart(intervals, i, n, x.End);

    if (j != 0 && intervals[j-1].Start <= x.End && intervals[j-1].End > x.End) {
        insertEnd = intervals[j-1].End;
    }

    intervals[i].Start = insertStart;
    intervals[i].End = insertEnd;
    intervals.erase(intervals.cbegin() + (i+1), intervals.cbegin() + j);
}