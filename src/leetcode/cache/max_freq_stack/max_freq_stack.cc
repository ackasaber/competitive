#include "max_freq_stack.h"

FreqStack::FreqStack()
  : maxFreq_(0)
{}

void FreqStack::Push(int value) {
  auto freq = ++freq_[value];

  if (freq-1 == byPushFreq_.size())
    byPushFreq_.resize(freq);

  byPushFreq_[freq-1].push(value);

  if (freq > maxFreq_)
    maxFreq_ = freq;
}

int FreqStack::Pop() {
  if (maxFreq_ == 0)
    throw std::runtime_error("empty stack");

  auto& stack = byPushFreq_[maxFreq_-1];
  int value = stack.top();
  stack.pop();

  if (stack.empty()) {
    --maxFreq_;
    byPushFreq_.resize(maxFreq_);
  }

  --freq_[value];
  return value;
}
