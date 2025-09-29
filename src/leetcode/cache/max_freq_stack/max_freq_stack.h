#pragma once

#include <stack>
#include <vector>
#include <unordered_map>

class FreqStack
{
public:
  FreqStack();
  void Push(int value);
  int Pop();

private:
  using Stack = std::stack<int, std::vector<int>>;
  // Frequency by value.
  std::unordered_map<int, size_t> freq_;
  // All elements are stored in stacks. The stack index is the element
  // frequency after that element got inserted minus 1.
  std::vector<Stack> byPushFreq_;
  size_t maxFreq_;
};
