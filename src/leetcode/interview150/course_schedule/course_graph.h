#pragma once

#include <vector>
#include "prerequisite.h"

struct Graph {
  explicit Graph(int vertexCount) : outArcs_(vertexCount) {}
  void AddArc(int u, int v) { outArcs_[u].push_back(v); }
  int VertexCount() const { return outArcs_.size(); }
  const std::vector<int>& Neighbours(int u) const { return outArcs_[u]; }

 private:
  std::vector<std::vector<int>> outArcs_;
};

struct CourseGraph : public Graph {
  CourseGraph(int courseCount, const std::vector<Prerequisite>& prerequisites)
      : Graph(courseCount) {
    for (const auto& p : prerequisites) {
      AddArc(p.course, p.prereq);
    }
  }
};
