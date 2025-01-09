#include "word_search2.h"

#include <gtest/gtest.h>

TEST(WordSearch2, DescriptionTest) {
  std::vector<std::vector<char>> board1 = {
    {'o', 'a', 'a', 'n'},
    {'e', 't', 'a', 'e'},
    {'i', 'h', 'k', 'r'},
    {'i', 'f', 'l', 'v'}
  };
  std::vector<std::string> words1 = { "oath", "pea", "eat", "rain" };
  std::unordered_set<std::string> found1 = { "eat", "oath" };
  EXPECT_EQ(FindWords(board1, words1), found1);

  std::vector<std::vector<char>> board2 = {
    {'a', 'b'},
    {'c', 'd'}
  };
  std::vector<std::string> words2 = { "abcb" };
  std::unordered_set<std::string> found2;
  EXPECT_EQ(FindWords(board2, words2), found2);
}

TEST(WordSearch2, MyTests) {
  std::vector<std::vector<char>> board1 = {
    {'o', 'a', 'a', 'n'},
    {'e', 't', 'a', 'e'},
    {'i', 'h', 'k', 'r'},
    {'i', 'f', 'l', 'v'}
  };
  std::vector<std::string> words1 = { "a", "b", "c", "d" };
  std::unordered_set<std::string> found1 = { "a" };
  EXPECT_EQ(FindWords(board1, words1), found1);

  std::vector<std::vector<char>> board2 = {
    {'a'}
  };
  std::vector<std::string> words2 = { "a" };
  std::unordered_set<std::string> found2 = { "a" };
  EXPECT_EQ(FindWords(board2, words2), found2);

  std::vector<std::vector<char>> board3(12, std::vector<char>(12, 'a'));
  std::vector<std::string> words3 = { "aaaaaaaaab", "aaaaaaaaaz" };
  std::unordered_set<std::string> found3;
  EXPECT_EQ(FindWords(board3, words3), found3);
}