package com.jahndigital.expressive.binding;

import java.lang.reflect.Type;

public final class BoundLiteralExpression extends BoundExpression {
    private Object _value;

    public Object getValue()
    {
        return _value;
    }

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

