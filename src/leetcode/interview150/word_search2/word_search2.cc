#include "word_search2.h"

#include <memory>

struct Trie {
  static constexpr int letterCount = 'z' - 'a' + 1;
  std::unique_ptr<Trie> children[letterCount];
  const std::string* leaf = nullptr;
  Trie() = default;
  explicit Trie(const std::vector<std::string>& words);
  void Add(const std::string& word);
};

void Dfs(std::vector<std::vector<char>>& board, size_t r, size_t c, Trie* trie, std::unordered_set<std::string>& found);

std::unordered_set<std::string> FindWords(const std::vector<std::vector<char>>& board, const std::vector<std::string>& words) {
  Trie trie(words);
  size_t n = board.size();
  size_t m = board[0].size();
  auto copy = board;
  std::unordered_set<std::string> found;

  for (size_t r = 0; r < n; r++) {
    for (size_t c = 0; c < m; c++) {
      Dfs(copy, r, c, &trie, found);
    }
  }

  return found;
}

Trie::Trie(const std::vector<std::string>& words) {
  for (const auto& word: words) {
    Add(word);
  }
}

void Trie::Add(const std::string& word) {
  Trie* node = this;

  for (char c: word) {
    if (!node->children[c - 'a']) {
      node->children[c - 'a'] = std::make_unique<Trie>();
    }

    node = node->children[c - 'a'].get();
  }

  node->leaf = &word;
}

void Dfs(std::vector<std::vector<char>>& board, size_t r, size_t c, Trie* trie, std::unordered_set<std::string>& found) {
  // r and c are valid indices
  // trie != nullptr
  size_t n = board.size();
  size_t m = board[0].size();
  char letter = board[r][c];

  if (letter == '\0') {
    return;
  }

  Trie* subTrie = trie->children[letter - 'a'].get();

  if (!subTrie) {
    return;
  }

  if (subTrie->leaf) {
    found.insert(*(subTrie->leaf));
    // In case the same word can be found in two different places of the board, remove the first encounter
    subTrie->leaf = nullptr;
  }

  // Forbid returns
  board[r][c] = '\0';

  if (r > 0) {
    Dfs(board, r-1, c, subTrie, found);
  }
  if (r+1 < n) {
    Dfs(board, r+1, c, subTrie, found);
  }
  if (c > 0) {
    Dfs(board, r, c-1, subTrie, found);
  }
  if (c+1 < m) {
    Dfs(board, r, c+1, subTrie, found);
  }

  board[r][c] = letter;
}