#include <memory>

#include "course_graph.h"
#include "course_schedule.h"

struct TopoSorter {
  explicit TopoSorter(const Graph& graph);
  bool IncludeCourse(int u);
  const std::vector<int>& Sorted() const { return sorted_; }

 private:
  const Graph& graph_;
  enum class Mark { unvisited, visiting, visited };
  std::unique_ptr<Mark[]> marks_;
  std::vector<int> sorted_;
};

std::vector<int> Schedule(int courseCount,
                          const std::vector<Prerequisite>& prerequisites) {
  CourseGraph graph(courseCount, prerequisites);
  TopoSorter sorter(graph);
  for (int u = 0; u < courseCount; u++) {
    if (!sorter.IncludeCourse(u)) {
      return {};
    }
  }
  return sorter.Sorted();
}

TopoSorter::TopoSorter(const Graph& graph)
    : graph_(graph), marks_(std::make_unique<Mark[]>(graph.VertexCount())) {
  sorted_.reserve(graph.VertexCount());
}

bool TopoSorter::IncludeCourse(int u) {
  if (marks_[u] == Mark::visited) {
    return true;
  }

  marks_[u] = Mark::visiting;

  for (int v : graph_.Neighbours(u)) {
    if (marks_[v] == Mark::visiting ||
        marks_[v] == Mark::unvisited && !IncludeCourse(v)) {
      return false;
    }
  }

  // All requirements have been added, it's time to include the current course.
  sorted_.push_back(u);
  marks_[u] = Mark::visited;
  return true;
}