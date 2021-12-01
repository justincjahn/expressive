package com.jahndigital.expressive.syntax;

/**
 * Contains information about the precedence of different operations.
 */
final class SyntaxFacts
{
    /**
     * Gets the priority of the provided unary operator SyntaxKind.
     *
     * @param kind
     * @return
     */
    static int getUnaryOperatorPrecedence(SyntaxKind kind)
    {
        switch (kind) {
            case PlusToken:
            case MinusToken:
                return 3;
            default:
                return 0;
        }
    }

    /**
     * Gets the priority of the provided binary operator SyntaxKind.
     *
     * @param kind
     * @return
     */
    static int getBinaryOperatorPrecedence(SyntaxKind kind)
    {
        switch (kind) {
            case StarToken:
            case SlashToken:
                return 2;
            case PlusToken:
            case MinusToken:
                return 1;
            default:
                return 0;
        }
    }

    /**
     * Gets the SyntaxKind that represents the text provided, or returns {@link SyntaxKind#KeywordToken}.
     *
     * @param text The text to analyze.
     * @return A {@link SyntaxKind} that represents the token provided.
     */
    public static SyntaxKind getKeywordKind(String text)
    {
        switch (text) {
            case "true":
                return SyntaxKind.TrueToken;
            case "false":
                return SyntaxKind.FalseToken;
            default:
                return SyntaxKind.KeywordToken;
        }
    }
}
