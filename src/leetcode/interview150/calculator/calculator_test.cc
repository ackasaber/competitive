#include "calculator.h"

#include <gtest/gtest.h>

TEST(Calculator, SimpleExpressions) {
    EXPECT_EQ(Calculate("23"), 23);
    EXPECT_EQ(Calculate("-23"), -23);
    EXPECT_EQ(Calculate("2+2"), 4);
    EXPECT_EQ(Calculate("1 + 1"), 2);
    EXPECT_EQ(Calculate(" 2-1 + 2 "), 3);
}

TEST(Calculator, SimpleBrackets) {
    EXPECT_EQ(Calculate("5+(-5)"), 0);
    EXPECT_EQ(Calculate("5+(-5-1)"), -1);
}

TEST(Calculator, ComplexExpression) {
    EXPECT_EQ(Calculate("(1+(4+5+2)-3)+(6+8)"), 23);
}
