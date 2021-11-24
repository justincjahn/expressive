package com.jahndigital.expressive.syntax;

import java.util.Arrays;

public class UnaryExpressionSyntaxNode extends ExpressionSyntaxNode
{
    SyntaxToken _operatorToken;
    ExpressionSyntaxNode _operand;

    public SyntaxToken getOperator()
    {
        return _operatorToken;
    }

    public ExpressionSyntaxNode getOperand()
    {
        return _operand;
    }

    public UnaryExpressionSyntaxNode(SyntaxToken token, ExpressionSyntaxNode operand)
    {
        _operatorToken = token;
        _operand = operand;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.UnaryExpression;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        return Arrays.asList(_operatorToken, _operand);
    }
}
