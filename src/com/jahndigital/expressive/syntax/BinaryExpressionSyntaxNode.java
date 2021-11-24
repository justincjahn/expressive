package com.jahndigital.expressive.syntax;

import java.util.Arrays;

/**
 * Represents syntax that performs an action on a 'left' and 'right' operand.
 */
public final class BinaryExpressionSyntaxNode extends ExpressionSyntaxNode {
    private final ExpressionSyntaxNode _left;
    private final SyntaxToken _operator;
    private final ExpressionSyntaxNode _right;

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.BinaryExpression;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        return Arrays.asList(_left, _operator, _right);
    }

    public ExpressionSyntaxNode getLeft() {
        return _left;
    }

    public SyntaxToken getOperator() {
        return _operator;
    }

    public ExpressionSyntaxNode getRight() {
        return _right;
    }

    public BinaryExpressionSyntaxNode(ExpressionSyntaxNode left, SyntaxToken operator, ExpressionSyntaxNode right) {
        this._left = left;
        this._operator = operator;
        this._right = right;
    }
}
