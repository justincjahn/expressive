package com.jahndigital.expressive.binding;

import java.lang.reflect.Type;

public final class BoundUnaryExpression extends BoundExpression
{
    private final BoundUnaryOperatorKind _kind;
    private final BoundExpression _operand;

    public BoundUnaryOperatorKind getOperatorKind()
    {
        return _kind;
    }

    public BoundExpression getOperand()
    {
        return _operand;
    }

    public BoundUnaryExpression(BoundUnaryOperatorKind kind, BoundExpression operand)
    {
        this._kind = kind;
        this._operand = operand;
    }

    @Override
    public Type getType() {
        return _operand.getType();
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.UnaryExpression;
    }
}

