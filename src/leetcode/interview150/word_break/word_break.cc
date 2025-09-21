#include "word_break.h"

#include <memory>
#include <unordered_set>

bool CanBeBroken(std::string_view s, const std::vector<std::string>& wordDict) {
    size_t n = s.length();
    std::vector<size_t> breakables;
    breakables.push_back(0);

    size_t maxWordLen = 0;
    std::unordered_set<std::string_view> words;
    words.reserve(n);

    for (const auto& word: wordDict) {
        if (word.length() > maxWordLen) {
            maxWordLen = word.length();
        }
        words.insert(word);
    }

    for (size_t i = 0; i < n; i++) {
        auto it = breakables.crbegin();

        while (it != breakables.crend() && i - *it + 1 <= maxWordLen) {
            std::string_view lastWord = s.substr(*it, i - *it + 1);
            // LeetCode reports an address sanitizer error here if std::string_view
            // is used, but I couldn't get why is that.
            if (words.contains(lastWord)) {
                breakables.push_back(i+1);
                break;
            }
            it++;
        }
    }

    return !breakables.empty() && breakables.back() == n;
}
