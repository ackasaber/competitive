#include <memory>
#include "course_schedule.h"
#include "course_graph.h"

struct CycleSearcher {
  explicit CycleSearcher(const Graph& graph);
  bool ReachesCycle(int u);

 private:
  const Graph& graph_;
  enum class Mark { unvisited, visiting, visited };
  std::unique_ptr<Mark[]> marks_;
};

bool CanFinish(int courseCount,
               const std::vector<Prerequisite>& prerequisites) {
  CourseGraph graph(courseCount, prerequisites);
  CycleSearcher searcher(graph);
  for (int u = 0; u < courseCount; u++) {
    if (searcher.ReachesCycle(u)) {
      return false;
    }
  }
  return true;
}

CycleSearcher::CycleSearcher(const Graph& graph)
    : graph_(graph),
      marks_(std::make_unique<Mark[]>(graph.VertexCount())) {}

bool CycleSearcher::ReachesCycle(int u) {
  if (marks_[u] == Mark::visited) {
    return false;
  }

  // unvisited, then
  marks_[u] = Mark::visiting;

  for (int v : graph_.Neighbours(u)) {
    if (marks_[v] == Mark::visiting ||
        marks_[v] == Mark::unvisited && ReachesCycle(v)) {
      return true;
    }
  }

  marks_[u] = Mark::visited;
  return false;
}