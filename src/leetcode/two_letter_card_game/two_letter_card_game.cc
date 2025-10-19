#include "two_letter_card_game.h"

#include <stdexcept>

namespace {

// It's not emphasized in the problem statement, but there can be multiples
// of each card in the input. If there are no multiples, the problem becomes
// trivial.
//
// Therefore, we first count frequencies of each type of card. We distinguish
// three types of cards:
//
//  1. x*, second letter is not x
//  2. *x, first letter is not x
//  3. xx
//
// We separate all cards in three piles accordingly. The card frequencies
// are stored by the zero-based index of the non-x letter. For the pile 3
// we, of course, care only about its size.
//
// Thus, we get the compressed input of the problem. (Arguably, this should've
// been the input in the problem statement.)

struct FrequencyTables {
  std::vector<int> pile1;
  std::vector<int> pile2;
  int n3;
};

FrequencyTables ToFrequencyTables(const std::vector<std::string>& cards, char x) {
  constexpr size_t letterCount = 'j' - 'a' + 1;
  std::vector<int> pile1(letterCount);
  std::vector<int> pile2(letterCount);
  int n3 = 0;

  for (const auto& card: cards) {
    if (card.length() != 2)
      throw std::runtime_error("card must have 2 letters");

    if (card[0] == x) {
      if (card[1] == x) {
        ++n3;
      } else {
        ++pile1[card[1] - 'a'];
      }
    } else if (card[1] == x) {
      ++pile2[card[0] - 'a'];
    }
  }

  return {pile1, pile2, n3};
}

// We'll construct the solution from the partial solutions for individual piles.
// It's actually far from evident, but the partial solution for a single pile
// depends only on the pile size and maximum frequency of a card in the pile.
//
// To see that, first notice that the unpaired cards bear the same letter code.
// Let's name this letter code Q and consider an arbitrary pairing that only
// has unpaired Q-cards. If there are no or only one unpaired card, this pairing
// is the solution. Therefore, we limit ourselves only to the case when the
// number of unpaired cards >= 2.
//
// Let's find a pair in our pairing that doesn't contain Q-cards. If there is
// such a pair, we can break it and pair its constituent cards with the unpaired
// Q-cards instead, and thus increase the number of constructed pairs by one.
// This construction means that if the solution has >= 2 unpaired cards, all
// its pairs must contain Q-cards. This means that we have this case iff
// one of the cards has frequency higher than the number of all other cards
// in the pile. Such a frequency is easy to find, since it's the maximum
// frequency.
//
// This logic allows us to find the partial solution just by the pile size
// and maximum card frequency.

struct SampleStats {
  int count;
  int max;
};

SampleStats Stat(const std::vector<int>& freq) {
  int max = 0;
  int count = 0;

  for (const auto& f: freq) {
    max = std::max(max, f);
    count += f;
  }

  return {count, max};
}

int SinglePileSolution(int count, int max) {
  return (max >= count - max) ? count - max : count / 2;
}

}

int FindMaxScore(const std::vector<std::string>& cards, char x) {
  auto [pile1, pile2, n3] = ToFrequencyTables(cards, x);
  auto [n1, m1] = Stat(pile1);
  auto [n2, m2] = Stat(pile2);

  // As if the previous mental acrobatics wasn't enough, we'll also need to
  // see how the combined solution looks like. There are three cases to consider.
  // First of all, if the third pile is big enough, we might not even bother
  // with the partial solutions and just notice that we can't get better than
  // just pairing all cards from piles 1 and 2 with cards from pile 3.
  // The unpaired cards, if any, are pile 3 cards.

  if (n3 >= n1 + n2)
    return n1 + n2;

  // For the other two cases, we first find the partial solutions for piles 1 and 2.
  int p1 = SinglePileSolution(n1, m1);
  int p2 = SinglePileSolution(n2, m2);
  // Let's count the number of unpaired cards in them.
  int k1 = n1 - 2*p1;
  int k2 = n2 - 2*p2;

  // The second case is when the total number of unpaired cards in partial
  // solutions is greater than (or equals to) the size of pile 3.
  // In this case we pair all pile 3 cards with unpaired cards in the partial
  // solutions.
  if (n3 <= k1 + k2)
    return p1 + p2 + n3;

  // For k1 + k2 < n3 < n1 + n2, we have enough pile 3 cards to pair
  // unpaired cards from the partial solutions, and we can also break some
  // "inner" pairs from the partial solutions and pair their constituent cards
  // with pile 3 cards instead. The number of pairs in the solution in this case
  // is p1 + p2 + k1 + k2 + (n3 - k1 - k2) div 2, but it can be simplified
  // to the following simple formula:
  return (n3 + n1 + n2)/2;
}
