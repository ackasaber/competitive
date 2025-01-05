#pragma once

#include <vector>
#include <memory>

struct Graph {
  explicit Graph(int vertexCount);
  void AddArc(int u, int v);
  int VertexCount() const;
  const std::vector<int>& Neighbours(int u) const;

 private:
  std::vector<std::vector<int>> outArcs_;
};

struct DFSearcher {
  explicit DFSearcher(const Graph& graph);
  bool ReachesCycle(int u);

 private:
  const Graph& graph_;
  enum class Mark { unvisited, visiting, visited };
  std::unique_ptr<Mark[]> marks_;
};
