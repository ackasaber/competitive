#pragma once

#include <list>
#include <unordered_map>

class LfuCache {
public:
  LfuCache(int capacity);
  int Get(int key);
  void Put(int key, int value);

private:
  struct Entry {
    int key;
    int value;
    int useCount;
  };

  size_t capacity_;
  // List of entries, ordered from the last used to least recently used.
  using LruList = std::list<Entry>;
  // Hash table from numbers to the list of keys with the corresponding use count.
  std::unordered_map<size_t, LruList> byUseCount_;
  // Hash table from a key to the corresponding entry in the entry lists.
  std::unordered_map<int, LruList::iterator> table_;
  size_t minUseCount_;

  void Use(LruList::iterator entry);
};
