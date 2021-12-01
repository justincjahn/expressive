package com.jahndigital.expressive;

import com.jahndigital.expressive.binding.*;
import com.jahndigital.expressive.syntax.*;

/**
 * Walks the AST and evaluates the expression into an integer.
 */
public final class Evaluator {
    private final BoundExpression _root;

    public Evaluator(BoundExpression root) {
        this._root = root;
    }

    public Object evaluate() throws Exception {
        return evaluateExpression(_root);
    }

    private Object evaluateExpression(BoundExpression root) throws Exception {
        if (root instanceof BoundLiteralExpression) {
            return ((BoundLiteralExpression) root).getValue();
        }

        if (root instanceof BoundUnaryExpression) {
            BoundUnaryExpression u = (BoundUnaryExpression)root;
            int operand = (int)evaluateExpression(u.getOperand());
            BoundUnaryOperatorKind kind = u.getOperatorKind();

            switch (kind) {
                case Identity:
                    return operand;
                case Negation:
                    return -operand;
                default:
                    throw new Exception(String.format("Unexpected unary operator %s", kind));
            }
        }

        if (root instanceof BoundBinaryExpression) {
            BoundBinaryExpression b = (BoundBinaryExpression)root;
            int left = (int)evaluateExpression(b.getLeft());
            int right = (int)evaluateExpression(b.getRight());

            BoundBinaryOperatorKind operation = b.getOperatorKind();
            switch (operation) {
                case Addition:
                    return left + right;
                case Subtraction:
                    return left - right;
                case Multiplication:
                    return left * right;
                case Division:
                    return left / right;
                default:
                    throw new Exception(String.format("Unexpected binary operator %s", operation));
            }
        }

//        if (root instanceof ParenthesisedExpressionSyntax) {
//            ParenthesisedExpressionSyntax p = (ParenthesisedExpressionSyntax) root;
//            return evaluateExpression(p.getExpression());
//        }

        return 0;
    }
}
