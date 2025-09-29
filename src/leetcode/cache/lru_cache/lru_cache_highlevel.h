#pragma once

#pragma once

#include <list>
#include <unordered_map>

class LruCacheHighLevel {
public:
  explicit LruCacheHighLevel(size_t capacity);
  int Get(int key); // -1 if not found
  void Put(int key, int value);

  LruCacheHighLevel(const LruCacheHighLevel&) = delete;
  LruCacheHighLevel& operator=(const LruCacheHighLevel&) = delete;

private:
  struct Entry {
    int key;
    int value;
  };

  size_t capacity_;
  // Entries are ordered in the usage order,
  // starting from the most recently used keys.
  using UsageList = std::list<Entry>;
  UsageList entries_;
  std::unordered_map<int, UsageList::iterator> table_;
  void Evict();
};
