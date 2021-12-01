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

    // SyntaxNodes
    LiteralExpression,
    BinaryExpression,
    UnaryExpression,
    ParenthesisedExpression
}
