package com.jahndigital.expressive;

import com.jahndigital.expressive.binding.*;
import com.sun.javaws.exceptions.InvalidArgumentException;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
    public Evaluator(BoundExpression root)
    {
        this._root = root;
    }

    /**
     * Evaluates the expression, returning the result as an object.
     *
     * @throws Exception If an unrecoverable error was encountered during evaluation.
     */
    public Object evaluate() throws Exception
    {
        return evaluateExpression(_root);
    }

    /**
     * Recursively evaluates the provided expression based on its type.
     *
     * @param root The {@link BoundNode} to evaluate.
     * @return The result of the evaluation.
     * @throws Exception If an unrecoverable error was encountered during evaluation.
     */
    private Object evaluateExpression(BoundExpression root) throws Exception
    {
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
                    return _negate(operand);
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
                    return _evaluateAddition(left, right);
                case Subtraction:
                    return _evaluateSubtraction(left, right);
                case Multiplication:
                    return _evaluateMultiplication(left, right);
                case Division:
                    return _evaluateDivision(left, right);
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

    private static Object _negate(Object num) throws Exception
    {
        if (num instanceof Integer) {
            return -((int)num);
        }

        if (num instanceof BigDecimal) {
            return ((BigDecimal)num).negate();
        }

        // Shouldn't ever get here
        throw new Exception("Only numbers and decimal values can be negated.");
    }

    private static BigDecimal _getNumberAsDecimal(Object num) throws Exception
    {
        if (num instanceof BigDecimal) {
            return (BigDecimal)num;
        }

        if (num instanceof Integer) {
            return new BigDecimal((int)num);
        }

        throw new InvalidArgumentException(new String[] { "num", "Object provided must either be a decimal or an integer." });
    }

    private static BigDecimal[] _getNumbersAsDecimal(Object left, Object right) throws Exception
    {
        return new BigDecimal[] { _getNumberAsDecimal(left), _getNumberAsDecimal(right) };
    }

    private static Object _evaluateAddition(Object left, Object right) throws Exception
    {
        if (left instanceof Integer && right instanceof Integer) {
            return (int)left + (int)right;
        }

        BigDecimal[] values = _getNumbersAsDecimal(left, right);
        return values[0].add(values[1]);
    }

    private static Object _evaluateSubtraction(Object left, Object right) throws Exception
    {
        if (left instanceof Integer && right instanceof Integer) {
            return (int)left - (int)right;
        }

        BigDecimal[] values = _getNumbersAsDecimal(left, right);
        return values[0].subtract(values[1]);
    }

    private static Object _evaluateMultiplication(Object left, Object right) throws Exception
    {
        if (left instanceof Integer && right instanceof Integer) {
            return (int)left * (int)right;
        }

        BigDecimal[] values = _getNumbersAsDecimal(left, right);
        return values[0].multiply(values[1]);
    }

    private static Object _evaluateDivision(Object left, Object right) throws Exception
    {
        BigDecimal[] values = _getNumbersAsDecimal(left, right);
        return values[0].divide(values[1], RoundingMode.HALF_EVEN);
    }
}
