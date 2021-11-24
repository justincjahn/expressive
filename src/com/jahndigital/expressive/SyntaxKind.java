package com.jahndigital.expressive;

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
    NumberExpression,
    BinaryExpression,
    UnaryExpression, ParenthesisedExpression
}
