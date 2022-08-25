package com.jahndigital.expressive.syntax;

import java.util.Arrays;

/**
 * Represents an expression that performs an operation on a left and right operand.
 */
public final class BinaryExpressionSyntaxNode extends ExpressionSyntaxNode
{
    private final ExpressionSyntaxNode _left;
    private final SyntaxToken _operator;
    private final ExpressionSyntaxNode _right;

    /**
     * Gets the {@link SyntaxKind} of the expression.
     */
    @Override
    public SyntaxKind getKind()
    {
        return SyntaxKind.BinaryExpression;
    }

    /**
     * Gets the left, operation, and right nodes of the expression.
     */
    @Override
    public Iterable<SyntaxNode> getChildren()
    {
        return Arrays.asList(_left, _operator, _right);
    }

    /**
     * Gets the left side of the operation.
     */
    public ExpressionSyntaxNode getLeft()
    {
        return _left;
    }

    /**
     * Gets the operation of the expression.
     */
    public SyntaxToken getOperator()
    {
        return _operator;
    }

    /**
     * Gets the right side of the operation.
     */
    public ExpressionSyntaxNode getRight()
    {
        return _right;
    }

    /**
     * Init
     */
    public BinaryExpressionSyntaxNode(ExpressionSyntaxNode left, SyntaxToken operator, ExpressionSyntaxNode right)
    {
        this._left = left;
        this._operator = operator;
        this._right = right;
    }
}
