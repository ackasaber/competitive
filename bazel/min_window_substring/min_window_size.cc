#include <array>
#include "min_window_size.h"

struct FrequencyTable {
  FrequencyTable();
  void Add(char c);
  void Remove(char c);
  size_t Count(char c) const;

private:
  static constexpr size_t letterCount = 2*('z' - 'a' + 1);
  std::array<size_t, letterCount> freq_;
};

absl::string_view MinContainingSubstring(absl::string_view s, absl::string_view t) {
  absl::string_view answer = s.substr(0, 0);
  FrequencyTable needleFreq;

  for (char c: t)
    needleFreq.Add(c);

  size_t matchCount = 0;
  FrequencyTable windowFreq;
  size_t windowSize = 0;
  size_t n = s.length();
  size_t m = t.length();

  for (size_t i = 0; i < n; i++) {
    // Aim for this iteration:
    // Find the minimum window containing characters of t that ends on s[i].
    char c = s[i];
    windowFreq.Add(c);
    windowSize++;

    if (windowFreq.Count(c) <= needleFreq.Count(c))
      matchCount++;

    if (matchCount == m) {
      char x = s[i + 1 - windowSize];
      windowFreq.Remove(x);
      windowSize--;

      while (windowFreq.Count(x) >= needleFreq.Count(x)) {
        x = s[i + 1 - windowSize];
        windowFreq.Remove(x);
        windowSize--;
      }

      matchCount--;

      if (answer.empty() || windowSize + 1 < answer.length())
        answer = s.substr(i - windowSize, windowSize + 1);
    }
  }

  return answer;
}

FrequencyTable::FrequencyTable() {
  std::fill(freq_.begin(), freq_.end(), 0);
}

void FrequencyTable::Add(char c) {
  if ('a' <= c && c <= 'z')
    freq_[c - 'a']++;
  else if ('A' <= c && c <= 'Z')
    freq_[(c - 'A') + ('z' - 'a' + 1)]++;
}

void FrequencyTable::Remove(char c) {
  if ('a' <= c && c <= 'z')
    freq_[c - 'a']--;
  else if ('A' <= c && c <= 'Z')
    freq_[(c - 'A') + ('z' - 'a' + 1)]--;
}

size_t FrequencyTable::Count(char c) const {
  if ('a' <= c && c <= 'z')
    return freq_[c - 'a'];
  if ('A' <= c && c <= 'Z')
    return freq_[(c - 'A') + ('z' - 'a' + 1)];
  return 0;
}