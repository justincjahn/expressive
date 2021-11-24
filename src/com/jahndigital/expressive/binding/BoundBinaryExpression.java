package com.jahndigital.expressive.binding;

import java.lang.reflect.Type;

public final class BoundBinaryExpression extends BoundExpression
{
    private final BoundExpression _left;
    private final BoundBinaryOperatorKind _operatorKind;
    private final BoundExpression _right;

    public BoundExpression getLeft()
    {
        return _left;
    }

    public BoundBinaryOperatorKind getOperatorKind()
    {
        return _operatorKind;
    }

    public BoundExpression getRight()
    {
        return _right;
    }

    public BoundBinaryExpression(BoundExpression left, BoundBinaryOperatorKind operatorKind, BoundExpression right)
    {
        this._left = left;
        this._operatorKind = operatorKind;
        this._right = right;
    }

    @Override
    public Type getType() {
        return _left.getType();
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.BinaryExpression;
    }
}
