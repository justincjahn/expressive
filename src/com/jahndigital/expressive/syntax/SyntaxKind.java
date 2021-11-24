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

    // SyntaxNodes
    LiteralExpression,
    BinaryExpression,
    UnaryExpression,
    ParenthesisedExpression
}
