package com.jahndigital.expressive;

import com.jahndigital.expressive.binding.*;

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
            Object operand = evaluateExpression(u.getOperand());
            BoundUnaryOperatorKind kind = u.getOperatorKind();

            switch (kind) {
                case Identity:
                    return operand;
                case Negation:
                    return -((int)operand);
                case LogicalNegation:
                    return !((boolean)operand);
                default:
                    throw new Exception(String.format("Unexpected unary operator %s", kind));
            }
        }

        if (root instanceof BoundBinaryExpression) {
            BoundBinaryExpression b = (BoundBinaryExpression)root;
            Object left = evaluateExpression(b.getLeft());
            Object right = evaluateExpression(b.getRight());

            BoundBinaryOperatorKind operation = b.getOperatorKind();
            switch (operation) {
                case Addition:
                    return (int)left + (int)right;
                case Subtraction:
                    return (int)left - (int)right;
                case Multiplication:
                    return (int)left * (int)right;
                case Division:
                    return (int)left / (int)right;
                case LogicalAnd:
                    return (boolean)left && (boolean)right;
                case LogicalOr:
                    return (boolean)left || (boolean)right;
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
