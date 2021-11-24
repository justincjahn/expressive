package com.jahndigital.expressive;

import java.util.Arrays;

/**
 * Represents a static numeric value.
 */
public final class LiteralExpressionSyntaxNode extends ExpressionSyntaxNode {
    private final SyntaxToken _token;

    public SyntaxToken getToken() {
        return _token;
    }

    public LiteralExpressionSyntaxNode(SyntaxToken t) {
        _token = t;
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
