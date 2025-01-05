#include "my_pow.h"

double PosPow(double x, int n);
double NegPow(double x, int n);

double MyPow(double x, int n) {
  if (n >= 0)
    return PosPow(x, n);
  return NegPow(x, n);
}

// Recursive version

double PosPowRec(double x, int n) {
  if (n == 0)
    return 1.0;

  double y = PosPowRec(x*x, n/2);
  // Or square PosPow(x, n/2), BUT this way you can make it tail-recursive, see below.

  if (n % 2 != 0)
    y *= x;

  return y;
}

// Tail-recursive form (call PosPow(x, n, 1.))

double PosPowTailRec(double x, int n, double y) {
  if (n == 0)
    return y;

  if (n % 2 == 0)
    return PosPowTailRec(x*x, n/2, y);

  return PosPowTailRec(x*x, n/2, x*y);
}

// Iterative version

double PosPow(double x, int n) {
  double y = 1.;

  while (n > 0) {
    if (n % 2 != 0)
      y *= x;

    x *= x;    
    n /= 2;
  }

  return y;
}

// This is only here for a clean n = -2147483648 case.

double NegPow(double x, int n) {
  double y = 1.;

  while (n < 0) {
    if (n % 2 != 0)
      y /= x;

    x *= x;
    n /= 2;
  }

  return y;
}