#include "course_schedule.h"
#include "graph.h"

bool CanFinish(int courseCount, const std::vector<Prerequisite>& prerequisites) {
  Graph graph(courseCount);

  for (const auto& p : prerequisites)
    graph.AddArc(p.course, p.prereq);

  DFSearcher searcher(graph);

  for (int u = 0; u < courseCount; u++) {
    if (searcher.ReachesCycle(u))
      return false;
  }

  return true;
}
