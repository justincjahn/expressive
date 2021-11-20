package com.jahndigital.expressive;

import java.util.Arrays;

/**
 * Represents syntax that performs an action on a 'left' and 'right' operand.
 */
public final class BinaryExpressionSyntax extends ExpressionSyntax {
    private final ExpressionSyntax _left;
    private final SyntaxToken _operator;
    private final ExpressionSyntax _right;

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.BinaryExpression;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        return Arrays.asList(_left, _operator, _right);
    }

    public ExpressionSyntax getLeft() {
        return _left;
    }

    public SyntaxToken getOperator() {
        return _operator;
    }

    public ExpressionSyntax getRight() {
        return _right;
    }

    public BinaryExpressionSyntax(ExpressionSyntax left, SyntaxToken operator, ExpressionSyntax right) {
        this._left = left;
        this._operator = operator;
        this._right = right;
    }
}
