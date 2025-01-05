#include "graph.h"

Graph::Graph(int vertexCount)
    : outArcs_(vertexCount) { }

void Graph::AddArc(int u, int v) {
  outArcs_[u].push_back(v);
}

int Graph::VertexCount() const { return outArcs_.size(); }

const std::vector<int>& Graph::Neighbours(int u) const {
  return outArcs_[u];
}

DFSearcher::DFSearcher(const Graph& graph)
    : graph_(graph)
    , marks_(std::make_unique<Mark[]>(graph.VertexCount())) {}

bool DFSearcher::ReachesCycle(int u) {
  if (marks_[u] == Mark::visited) return false;

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