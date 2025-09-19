#include "calculator.h"

#include <cctype>
#include <optional>
#include <stdexcept>
#include <vector>

namespace {

constexpr char NumberToken = '\0';

struct Token {
  char Type = NumberToken;
  int Value = 0;
};

class TokenParser {
 public:
  explicit TokenParser(std::string_view source) : _rest(source) {}
  std::optional<Token> NextToken();

 private:
  std::string_view _rest;

  void SkipWhitespace();
  int ReadInt();
};

std::optional<Token> TokenParser::NextToken() {
  SkipWhitespace();

  if (_rest.empty()) {
    return {};
  }

  Token token;

  switch (_rest[0]) {
    case '(':
    case ')':
    case '-':
    case '+':
      token.Type = _rest[0];
      _rest.remove_prefix(1);
      break;
    default:
      token.Type = NumberToken;
      token.Value = ReadInt();
      break;
  }

  return token;
}

int TokenParser::ReadInt() {
  int value = 0;
  size_t n = 0;

  while (n < _rest.length() && std::isdigit(_rest[n])) {
    value = value * 10 + (_rest[n] - '0');
    n++;
  }

  if (n == 0) {
    throw std::runtime_error("expected number");
  }

  _rest.remove_prefix(n);
  return value;
}

void TokenParser::SkipWhitespace() {
  size_t n = 0;

  while (n < _rest.length() && _rest[n] == ' ') {
    n++;
  }

  _rest.remove_prefix(n);
}

template <typename T>
T Pop(std::vector<T>& stack) {
  if (stack.empty()) {
    throw std::runtime_error("empty stack");
  }
  T x = stack.back();
  stack.pop_back();
  return x;
}

template <typename T>
void Push(std::vector<T>& stack, T x) {
  stack.push_back(std::move(x));
}

void Simplify(std::vector<char>& ops, std::vector<int>& values) {
  while (!ops.empty() && ops.back() != '(') {
    switch (ops.back()) {
      case '+': {
        int arg2 = Pop(values);
        int arg1 = Pop(values);
        Push(values, arg1 + arg2);
        break;
      }
      case '-': {
        int arg2 = Pop(values);
        int arg1 = Pop(values);
        Push(values, arg1 - arg2);
        break;
      }
      case '~': {
        int arg = Pop(values);
        Push(values, -arg);
        break;
      }
    }
    ops.pop_back();
  }
}

}  // namespace

int Calculate(std::string_view expr) {
  std::vector<char> ops;
  std::vector<int> values;
  TokenParser parser(expr);
  char lastToken = '(';

  while (auto token = parser.NextToken()) {
    switch (token->Type) {
      case NumberToken:
        Push(values, token->Value);
        Simplify(ops, values);
        break;
      case '(':
      case '+':
        Push(ops, token->Type);
        break;
      case '-':
        Push(ops, lastToken == NumberToken || lastToken == ')' ? '-' : '~');
        break;
      case ')':
        if (Pop(ops) != '(') {
          throw std::runtime_error("unmatched )");
        }
        Simplify(ops, values);
    }
    lastToken = token->Type;
  }

  Simplify(ops, values);

  if (values.size() != 1) {
    throw std::runtime_error("invalid expression");
  }

  return values.front();
}