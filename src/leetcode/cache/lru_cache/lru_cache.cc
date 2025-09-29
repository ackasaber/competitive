#include "lru_cache.h"

#include <random>
#include <iostream>

LruCache::LruCache(int capacity, int a, int b)
  : capacity_(capacity)
  , size_(0)
  , a_(a)
  , b_(b)
  , table_(std::make_unique<Entry*[]>(capacity))
  , lastUsed_(nullptr)
  , leastRecentlyUsed_(nullptr)
{
}

LruCache::LruCache(int capacity)
  : LruCache(capacity, 0, 0)
{
  std::random_device device;
  std::mt19937 rng(device());
  std::uniform_int_distribution<int> aDist(1, prime-1);
  a_ = aDist(rng);
  std::uniform_int_distribution<int> bDist(0, prime-1);
  b_ = bDist(rng);
}

LruCache::~LruCache() {
  Entry* entry = lastUsed_;

  while (entry != nullptr) {
    Entry* toDelete = entry;
    entry = entry->usedAfter;
    delete toDelete;
  }
}

void LruCache::Put(int key, int value) {
  int hash = Hash(key);
  std::cout << "PUT " << key << " (hash " << hash << ") " << value << std::endl;
  Entry* entry = Find(table_[hash], key);

  if (entry == nullptr) {
    if (size_ == capacity_)
      EvictLru();
    else
      ++size_;

    // Create a new entry and insert it into the bucket.
    entry = new Entry;
    entry->key = key;
    entry->value = value;
    table_[hash] = InsertIntoBucket(table_[hash], entry);
    InsertIntoLru(entry);
  } else {
    entry->value = value;
    Use(entry);
  }
}

int LruCache::Get(int key) {
  int hash = Hash(key);
  std::cout << "GET " << key << " (hash " << hash << ")" << std::endl;
  Entry* entry = Find(table_[hash], key);

  if (entry == nullptr)
    return -1;

  Use(entry);
  return entry->value;
}

LruCache::Entry* LruCache::Find(Entry* bucket, int key) {
  Entry* entry = bucket;

  while (entry != nullptr && entry->key != key) {
    std::cout << "Searching in entry with key = " << entry->key << std::endl;
    entry = entry->bucketNext;
  }

  return entry;
}

int LruCache::Hash(int key) const {
  int hash = (a_ * key + b_) % prime;

  if (hash < 0)
    hash += prime;

  return hash % capacity_;
}

LruCache::Entry* LruCache::InsertIntoBucket(Entry* bucket, Entry* entry) {
  entry->bucketNext = bucket;

  if (bucket != nullptr)
    bucket->bucketPrev = entry;

  entry->bucketPrev = nullptr;
  return entry;
}

void LruCache::InsertIntoLru(Entry* entry) {
  entry->usedAfter = lastUsed_;

  if (lastUsed_ != nullptr)
    lastUsed_->usedBefore = entry;
  else
    leastRecentlyUsed_ = entry;

  entry->usedBefore = nullptr;
  lastUsed_ = entry;
}

void LruCache::Use(Entry* entry) {
  std::cout << "USE " << entry->key << std::endl;

  if (entry == lastUsed_)
    return;

  // Make the entry last used.
  Entry* next = entry->usedAfter;
  Entry* prev = entry->usedBefore;

  if (next != nullptr)
    next->usedBefore = prev;
  else
    leastRecentlyUsed_ = prev;

  prev->usedAfter = next;
  entry->usedAfter = lastUsed_;
  lastUsed_->usedBefore = entry;
  lastUsed_ = entry;
  entry->usedBefore = nullptr;
}

void LruCache::EvictLru() {
  std::cout << "EVICT" << std::endl;
  Entry* toDelete = leastRecentlyUsed_;

  if (toDelete == nullptr)
    return;

  std::cout << "Evicting " << toDelete->key << std::endl;
  RemoveFromBucket(toDelete);
  RemoveLru();
  delete toDelete;
}

void LruCache::RemoveFromBucket(Entry* entry) {
  Entry* next = entry->bucketNext;
  Entry* prev = entry->bucketPrev;

  if (next != nullptr)
    next->bucketPrev = prev;

  if (prev != nullptr)
    prev->bucketNext = next;
  else {
    int hash = Hash(entry->key);
    table_[hash] = next;
  }
}

void LruCache::RemoveLru() {
  Entry* lru = leastRecentlyUsed_;
  Entry* newLru = lru->usedBefore;
  leastRecentlyUsed_ = newLru;

  if (newLru != nullptr)
    newLru->usedAfter = nullptr;
  else
    lastUsed_ = nullptr;
}
