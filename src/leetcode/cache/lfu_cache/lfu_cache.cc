#include "lfu_cache.h"

LfuCache::LfuCache(int capacity)
  : capacity_(capacity)
  , minUseCount_(0)
{}

int LfuCache::Get(int key) {
  auto it = table_.find(key);

  if (it == table_.end())
    return -1;

  Use(it->second);
  return it->second->value;
}

void LfuCache::Put(int key, int value) {
  auto it = table_.find(key);

  if (it == table_.end()) {
    if (table_.size() == capacity_) {
      auto& targetList = byUseCount_[minUseCount_];
      auto keyToErase = targetList.back().key;
      table_.erase(keyToErase);
      targetList.pop_back();
    }

    byUseCount_[1].push_front({key, value, 1});
    table_[key] = byUseCount_[1].begin();
    // In all cases, including after inserting the first element and after eviction.
    minUseCount_ = 1;
  } else {
    it->second->value = value;
    Use(it->second);
  }
}

void LfuCache::Use(LruList::iterator entry) {
  size_t oldUseCount = entry->useCount;
  size_t newUseCount = ++entry->useCount;
  auto& oldList = byUseCount_[oldUseCount];
  auto& newList = byUseCount_[newUseCount];
  newList.splice(newList.begin(), oldList, entry);

  if (oldList.empty()) {
    byUseCount_.erase(oldUseCount);

    if (oldUseCount == minUseCount_)
      minUseCount_ = newUseCount;
  }
}
