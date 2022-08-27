package com.jahndigital.expressive.syntax;

/**
 * Represents possible types of {@link SyntaxNode}s.
 */
public enum SyntaxKind
{
    // Tokens
    NumberToken,
    WhitespaceToken,
    PlusToken,
    MinusToken,
    StarToken,
    SlashToken,
    OpenParenthesisToken,
    CloseParenthesisToken,
    CommaToken,
    ExclamationPointToken,
    BadToken,
    EndOfFileToken,

    // Keywords and reserved words
    KeywordToken,
    FalseToken,
    TrueToken,

    // Boolean
    AndToken,
    OrToken,
    EqualityToken,
    NegatedEqualityToken,
    GreaterThanToken,
    GreaterThanEqualToken,
    LessThanToken,
    LessThanEqualToken,

    // SyntaxNodes
    LiteralExpression,
    FunctionExpression,
    BinaryExpression,
    UnaryExpression,
    ParenthesisedExpression
}
