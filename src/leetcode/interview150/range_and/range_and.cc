#include "range_and.h"

int RangeAnd(int start, int end) {
  int result = 0;
  int shift = 0;
  
  while (start != end) {
    start >>= 1;
    end >>= 1;
    shift++;
  }

  return start << shift;
}