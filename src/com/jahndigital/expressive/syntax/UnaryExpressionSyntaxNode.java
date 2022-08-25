package com.jahndigital.expressive.syntax;

import java.util.Arrays;

/**
 * Represents an expression that performs an operation on a single operand.
 */
public class UnaryExpressionSyntaxNode extends ExpressionSyntaxNode
{
    SyntaxToken _operatorToken;
    ExpressionSyntaxNode _operand;

    /**
     * Gets the operator of the unary operation.
     */
    public SyntaxToken getOperator()
    {
        return _operatorToken;
    }

    /**
     * Gets the operand of the operation.
     */
    public ExpressionSyntaxNode getOperand()
    {
        return _operand;
    }

    /**
     * Init
     */
    public UnaryExpressionSyntaxNode(SyntaxToken token, ExpressionSyntaxNode operand)
    {
        _operatorToken = token;
        _operand = operand;
    }

    @Override
    public SyntaxKind getKind()
    {
        return SyntaxKind.UnaryExpression;
    }

    @Override
    public Iterable<SyntaxNode> getChildren()
    {
        return Arrays.asList(_operatorToken, _operand);
    }
}
