#include "lru_cache_highlevel.h"

LruCacheHighLevel::LruCacheHighLevel(size_t capacity)
  : capacity_(capacity)
  , table_(capacity)
{}

int LruCacheHighLevel::Get(int key) {
  auto it = table_.find(key);

  if (it == table_.end())
    return -1;

  entries_.splice(entries_.begin(), entries_, it->second);
  return it->second->value;
}

void LruCacheHighLevel::Put(int key, int value) {
  auto it = table_.find(key);

  if (it == table_.end()) {
    if (table_.size() == capacity_)
      Evict();

    entries_.push_front({key, value});
    table_.emplace(key, entries_.begin());
  } else {
    it->second->value = value;
    entries_.splice(entries_.begin(), entries_, it->second);
  }
}

void LruCacheHighLevel::Evict() {
  if (entries_.empty())
    return;

  int keyToRemove = entries_.back().key;
  entries_.pop_back();
  table_.erase(keyToRemove);
}
