package com.jahndigital.expressive.binding;

import java.lang.reflect.Type;

/**
 * Represents a token literal in a typed syntax tree.
 */
public final class BoundLiteralExpression extends BoundExpression {
    private Object _value;

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
    public BoundLiteralExpression(Object value)
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

