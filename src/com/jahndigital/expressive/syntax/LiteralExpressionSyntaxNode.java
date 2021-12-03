package com.jahndigital.expressive.syntax;

import java.util.Arrays;

/**
 * Represents a literal value.
 */
public final class LiteralExpressionSyntaxNode extends ExpressionSyntaxNode {
    private final SyntaxToken _token;
    private final Object _value;

    /**
     * Gets the {@link SyntaxToken} that represents this literal value.
     */
    public SyntaxToken getToken() {
        return _token;
    }

    /**
     * Get the value of the literal.
     */
    public Object getValue()
    {
        if (_value == null) {
            return getToken().getValue();
        }

        return _value;
    }

    /**
     * Init the object with a null value.
     */
    public LiteralExpressionSyntaxNode(SyntaxToken t) {
        this(t, null);
    }

    /**
     * Init the object with the provided value.
     */
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
