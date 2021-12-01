package com.jahndigital.expressive.syntax;

import java.util.Arrays;

/**
 * Represents a static numeric value.
 */
public final class LiteralExpressionSyntaxNode extends ExpressionSyntaxNode {
    private final SyntaxToken _token;

    private final Object _value;

    public SyntaxToken getToken() {
        return _token;
    }

    public Object getValue()
    {
        if (_value == null) {
            return getToken().getValue();
        }

        return _value;
    }

    public LiteralExpressionSyntaxNode(SyntaxToken t) {
        this(t, null);
    }

    public LiteralExpressionSyntaxNode(SyntaxToken t, Object value)
    {
        _token = t;
        _value = value;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.LiteralExpression;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        return Arrays.asList(new SyntaxNode[]{_token});
    }
}
