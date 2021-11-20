package com.jahndigital.expressive;

/**
 * Used to easily reference the type of a SyntaxNode
 */
enum SyntaxKind
{
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

    NumberExpression,
    BinaryExpression,
    ParenthesisedExpression
}
