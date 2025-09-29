#pragma once

#include <memory>

class LruCacheLowLevel {
public:
  explicit LruCacheLowLevel(int capacity);
  int Get(int key); // -1 if not found
  void Put(int key, int value);

  LruCacheLowLevel(int capacity, int a, int b);
  ~LruCacheLowLevel();
  LruCacheLowLevel(const LruCacheLowLevel&) = delete;
  LruCacheLowLevel& operator=(const LruCacheLowLevel&) = delete;

private:
  struct Entry {
    int key;
    int value;
    Entry* bucketNext;
    Entry* bucketPrev;
    Entry* usedBefore;
    Entry* usedAfter;
  };

  // The first prime larger the maximum key 10000.
  static constexpr int prime = 10007;

  // Serves both as the hash table allocated size and contents size limit.
  int capacity_;
  int size_;
  // Random parameters for universal hashing.
  int a_;
  int b_;
  std::unique_ptr<Entry*[]> table_;
  // Double-linked list of all inserted entries
  Entry* lastUsed_;
  Entry* leastRecentlyUsed_;

  static Entry* Find(Entry* bucket, int key);
  static Entry* InsertIntoBucket(Entry* bucket, Entry* entry);
  void InsertIntoLru(Entry* entry);
  void RemoveFromBucket(Entry* entry);
  void RemoveLru();
  int Hash(int key) const;
  void Use(Entry* entry);
  void EvictLru();
};
