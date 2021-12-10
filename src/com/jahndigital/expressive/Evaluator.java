package com.jahndigital.expressive;

import com.jahndigital.expressive.binding.*;

/**
 * Walks the AST and evaluates the expression into an integer.
 */
public final class Evaluator {
    private final BoundExpression _root;

    /**
     * Init
     *
     * @param root The {@link BoundNode} that will be evaluated.
     */
    public Evaluator(BoundExpression root) {
        this._root = root;
    }

    /**
     * Evaluates the expression, returning the result as an object.
     *
     * @throws Exception If an unrecoverable error was encountered during evaluation.
     */
    public Object evaluate() throws Exception {
        return evaluateExpression(_root);
    }

    /**
     * Recursively evaluates the provided expression based on its type.
     *
     * @param root The {@link BoundNode} to evaluate.
     * @return The result of the evaluation.
     * @throws Exception If an unrecoverable error was encountered during evaluation.
     */
    private Object evaluateExpression(BoundExpression root) throws Exception {
        if (root instanceof BoundLiteralExpression) {
            return ((BoundLiteralExpression) root).getValue();
        }

        if (root instanceof BoundUnaryExpression) {
            BoundUnaryExpression u = (BoundUnaryExpression)root;
            Object operand = evaluateExpression(u.getOperand());
            BoundUnaryOperationKind kind = u.getOperatorKind();

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

            BoundBinaryOperationKind operation = b.getOperatorKind();
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
                case Equals:
                    return left.equals(right);
                case NotEquals:
                    return !left.equals(right);
                default:
                    throw new Exception(String.format("Unexpected binary operator %s", operation));
            }
        }

        return 0;
    }
}
