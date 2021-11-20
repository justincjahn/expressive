package com.jahndigital.expressive;

import java.util.Arrays;

/**
 * Represents a static numeric value.
 */
public final class NumberExpressionSyntax extends ExpressionSyntax {
    private final SyntaxToken _token;

    public SyntaxToken getToken() {
        return _token;
    }

    public NumberExpressionSyntax(SyntaxToken numberToken) {
        _token = numberToken;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.NumberExpression;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        return Arrays.asList(new SyntaxNode[]{_token});
    }
}
