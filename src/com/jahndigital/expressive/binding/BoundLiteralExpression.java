package com.jahndigital.expressive.binding;

import java.lang.reflect.Type;

/**
 * Represents a typed version of a {@link com.jahndigital.expressive.syntax.LiteralExpressionSyntaxNode}.
 */
public final class BoundLiteralExpression extends BoundExpression {
    private final Object _value;

    /**
     * Get the raw value of the expression.
     */
    public Object getValue()
    {
        return _value;
    }

    /**
     * Init
     *
     * @param value The raw value of the literal.
     */
    BoundLiteralExpression(Object value)
    {
        _value = value;
    }

    @Override
    public Type getType() {
        return _value.getClass();
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.LiteralExpression;
    }
}
