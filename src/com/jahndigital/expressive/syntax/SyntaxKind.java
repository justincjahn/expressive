package com.jahndigital.expressive.syntax;

/**
 * Used to easily reference the type of a SyntaxNode
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
    BadToken,
    EndOfFileToken,

    // Keywords and reserved words
    KeywordToken,
    FalseToken,
    TrueToken,

    // SyntaxNodes
    LiteralExpression,
    BinaryExpression,
    UnaryExpression,
    ParenthesisedExpression
}
