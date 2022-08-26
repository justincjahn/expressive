package com.jahndigital.expressive.binding;

import java.lang.reflect.Type;

/**
 * Represents a typed version of a {@link com.jahndigital.expressive.syntax.BinaryExpressionSyntaxNode}.
 */
public final class BoundBinaryExpression extends BoundExpression
{
    private final BoundExpression _left;
    private final BoundBinaryOperation _operator;
    private final BoundExpression _right;

    /**
     * Gets the left side of the operation.
     */
    public BoundExpression getLeft()
    {
        return _left;
    }

    /**
     * Gets the kind of operation that should occur.
     */
    public BoundBinaryOperationKind getOperatorKind()
    {
        return _operator.getOperatorKind();
    }

    /**
     * Gets the right side of the operation.
     */
    public BoundExpression getRight()
    {
        return _right;
    }

    /**
     * Init
     *
     * @param left The left side of the operation.
     * @param operator The kind of operation that should occur.
     * @param right The right side of the operation.
     */
    BoundBinaryExpression(BoundExpression left, BoundBinaryOperation operator, BoundExpression right)
    {
        this._left = left;
        this._operator = operator;
        this._right = right;
    }

    @Override
    public Type getType()
    {
        return _operator.getResultType();
    }

    @Override
    public BoundNodeKind getKind()
    {
        return BoundNodeKind.BinaryExpression;
    }
}
