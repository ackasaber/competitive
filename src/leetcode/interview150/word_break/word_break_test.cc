#include "word_break.h"

#include <gtest/gtest.h>

TEST(WordBreak, SingleUse) {
    EXPECT_TRUE(CanBeBroken("leetcode", {"leet", "code"}));
}

TEST(WordBreak, MultipleUse) {
    EXPECT_TRUE(CanBeBroken("applepenapple", {"apple", "pen"}));
}

TEST(WordBreak, Impossible) {
    EXPECT_FALSE(CanBeBroken("catsandog", {"cats", "dog", "sand", "and", "cat"}));
}

TEST(WordBreak, Trivial) {
    EXPECT_TRUE(CanBeBroken("abc", {"abc"}));
}