package com.jahndigital.expressive.binding;

import java.lang.reflect.Type;

public final class BoundUnaryExpression extends BoundExpression
{
    private final BoundUnaryOperator _operator;
    private final BoundExpression _operand;

    public BoundUnaryOperatorKind getOperatorKind()
    {
        return _operator.getOperatorKind();
    }

    public BoundExpression getOperand()
    {
        return _operand;
    }

    public BoundUnaryExpression(BoundUnaryOperator operator, BoundExpression operand)
    {
        this._operator = operator;
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

