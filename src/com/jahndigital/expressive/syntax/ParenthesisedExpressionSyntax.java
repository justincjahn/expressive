package com.jahndigital.expressive.syntax;

import java.util.Arrays;

/**
 * Represents parenthesised expressions, such as (1 + 2)
 */
public final class ParenthesisedExpressionSyntax extends ExpressionSyntaxNode {
    private final SyntaxToken _openToken;
    private final ExpressionSyntaxNode _expression;
    private final SyntaxToken _closeToken;

    public ExpressionSyntaxNode getExpression() {
        return _expression;
    }

    public ParenthesisedExpressionSyntax(SyntaxToken openToken, ExpressionSyntaxNode expression, SyntaxToken closeToken) {
        this._openToken = openToken;
        this._expression = expression;
        this._closeToken = closeToken;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.ParenthesisedExpression;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        return Arrays.asList(_openToken, _expression, _closeToken);
    }
}
