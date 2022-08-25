package com.jahndigital.expressive.binding;

import java.lang.reflect.Type;

/**
 * Represents a typed version of {@link com.jahndigital.expressive.syntax.UnaryExpressionSyntaxNode}.
 */
public final class BoundUnaryExpression extends BoundExpression
{
    private final BoundUnaryOperation _operator;
    private final BoundExpression _operand;

    /**
     * Gets the {@link BoundUnaryOperationKind} of this expression.
     */
    public BoundUnaryOperationKind getOperatorKind()
    {
        return _operator.getOperatorKind();
    }

    /**
     * Gets the operand (right side) of this operation.
     */
    public BoundExpression getOperand()
    {
        return _operand;
    }

    /**
     * Init
     *
     * @param operator The unary operator
     * @param operand The operand (right side) of the operation.
     */
    public BoundUnaryExpression(BoundUnaryOperation operator, BoundExpression operand)
    {
        this._operator = operator;
        this._operand = operand;
    }

    @Override
    public Type getType()
    {
        return _operator.getResultType();
    }

    @Override
    public BoundNodeKind getKind()
    {
        return BoundNodeKind.UnaryExpression;
    }
}
