#include "two_letter_card_game.h"

#include <gtest/gtest.h>

namespace {

void AddRepeats(std::vector<std::string>& cards, const std::string& card, int n) {
  for (int i = 0; i < n; ++i)
    cards.push_back(card);
}

}

TEST(TwoCardGameScore, DescriptionTest) {
  EXPECT_EQ(FindMaxScore({"aa", "ab", "ba", "ac"}, 'a'), 2);
  EXPECT_EQ(FindMaxScore({"aa", "ab", "ba"}, 'a'), 1);
  EXPECT_EQ(FindMaxScore({"aa", "ab", "ba", "ac"}, 'b'), 0);
}

TEST(TwoCardGameScore, AllXX) {
  EXPECT_EQ(FindMaxScore({"aa", "aa", "aa"}, 'a'), 0);
}

TEST(TwoCardGameScore, XXMatches) {
  EXPECT_EQ(FindMaxScore({"aa", "aa", "ba", "ab"}, 'a'), 2);
}

TEST(TwoCardGameScore, SingleGroupMatches) {
  std::vector<std::string> cards;
  cards.push_back("ab");
  AddRepeats(cards, "ac", 2);
  AddRepeats(cards, "ad", 3);
  AddRepeats(cards, "ae", 6);
  EXPECT_EQ(FindMaxScore(cards, 'a'), 6);
}

TEST(TwoCardGameScore, Test830) {
  std::vector<std::string> cards;
  AddRepeats(cards, "ba", 13);
  AddRepeats(cards, "bc", 8);
  AddRepeats(cards, "bb", 14);
  AddRepeats(cards, "ab", 9);
  AddRepeats(cards, "cb", 13);
  EXPECT_EQ(FindMaxScore(cards, 'b'), 28);
}
