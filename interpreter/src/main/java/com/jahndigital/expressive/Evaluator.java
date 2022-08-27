package com.jahndigital.expressive;

import com.jahndigital.expressive.binding.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Walks the AST and evaluates the expression into an integer.
 */
public final class Evaluator
{
    private final BoundExpression _root;
    private Map<String, Object> _runtimeContext;

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
        return evaluate(new HashMap<>());
    }

    /**
     * Evaluates the expression, returning the result as an object.
     *
     * @param runtimeContext A map of strings to objects that are passed to
     *                       {@link com.jahndigital.expressive.extensibility.IFunction} objects during evaluation.
     * @throws Exception If an unrecoverable error was encountered during evaluation.
     */
    public Object evaluate(HashMap<String, Object> runtimeContext) throws Exception
    {
        _runtimeContext = runtimeContext;
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

        if (root instanceof BoundFunctionExpression) {
            BoundFunctionExpression funcExpression = (BoundFunctionExpression)root;

            List<Object> args = new ArrayList<>();
            for (BoundExpression boundArgs : funcExpression.getArguments()) {
                args.add(evaluateExpression(boundArgs));
            }

            try {
                return funcExpression.getFunction().execute(args, _runtimeContext);
            } catch (Exception e) {
                throw new FunctionExecutionFailedException(
                    e,
                    funcExpression.getFunction(),
                    args,
                    _runtimeContext
                );
            }
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
            BoundBinaryOperationKind operation = b.getOperatorKind();
            Object left = evaluateExpression(b.getLeft());

            // Support short-circuiting
            if (operation == BoundBinaryOperationKind.LogicalAnd && left.equals(false)) {
                return left;
            }

            if (operation == BoundBinaryOperationKind.LogicalOr && left.equals(true)) {
                return left;
            }

            Object right = evaluateExpression(b.getRight());

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
                    return _evaluateEquality(left, right);
                case NotEquals:
                    return !_evaluateEquality(left, right);
                case GreaterThan:
                    return _compareNumbers(left, right, 1, false);
                case GreaterThanOrEqualTo:
                    return _compareNumbers(left, right, 1, true);
                case LessThan:
                    return _compareNumbers(left, right, -1, false);
                case LessThanOrEqualTo:
                    return _compareNumbers(left, right, -1, true);
                default:
                    throw new Exception(String.format("Unexpected binary operator %s", operation));
            }
        }

        return 0;
    }

    /**
     * Negate an {@link Integer} or {@link BigDecimal}.
     *
     * @param num The number to negate.
     * @return The negated number
     * @throws Exception If an attempt was made to negate an unsupported type.
     */
    private static Object _negate(Object num) throws Exception
    {
        if (num instanceof Integer) {
            return -((int)num);
        }

        if (num instanceof BigDecimal) {
            return ((BigDecimal)num).negate();
        }

        throw new Exception(String.format("Invalid negation on type %s.", num.getClass()));
    }

    /**
     * Returns the provided object as a {@link BigDecimal} or throws an exception.
     *
     * @param num An {@link Integer} or {@link BigDecimal} object.
     * @return A {@link BigDecimal} representing the provided value.
     * @throws Exception If the conversion failed or an invalid type is provided.
     */
    private static BigDecimal _getNumberAsDecimal(Object num) throws Exception
    {
        if (num instanceof BigDecimal) {
            return (BigDecimal)num;
        }

        if (num instanceof Integer) {
            return new BigDecimal((int)num);
        }

        throw new Exception(String.format("Invalid conversion from type %s to decimal.", num.getClass()));
    }

    /**
     * Converts the provided objects to {@link BigDecimal} objects.
     *
     * @param left The left operand
     * @param right The right operand
     * @return An array of two values where index 0 is the left operand and 1 is the right.
     * @throws Exception If an error occurred parsing the arguments to decimals.
     */
    private static BigDecimal[] _getNumbersAsDecimal(Object left, Object right) throws Exception
    {
        return new BigDecimal[] { _getNumberAsDecimal(left), _getNumberAsDecimal(right) };
    }

    /**
     * Add two numeric values together.
     *
     * @param left The left operand
     * @param right The right operand
     * @return The sum of the two operands.
     * @throws Exception If an error occurred parsing the arguments to decimals.
     */
    private static Object _evaluateAddition(Object left, Object right) throws Exception
    {
        if (left instanceof Integer && right instanceof Integer) {
            return (int)left + (int)right;
        }

        BigDecimal[] values = _getNumbersAsDecimal(left, right);
        return values[0].add(values[1]);
    }

    /**
     * Subtract two numeric values.
     *
     * @param left The left operand
     * @param right The right operand
     * @return The difference of the two operands.
     * @throws Exception If an error occurred parsing the arguments to decimals.
     */
    private static Object _evaluateSubtraction(Object left, Object right) throws Exception
    {
        if (left instanceof Integer && right instanceof Integer) {
            return (int)left - (int)right;
        }

        BigDecimal[] values = _getNumbersAsDecimal(left, right);
        return values[0].subtract(values[1]);
    }

    /**
     * Multiply two numeric values.
     *
     * @param left The left operand
     * @param right The right operand
     * @return The product of the two operands.
     * @throws Exception If an error occurred parsing the arguments to decimals.
     */
    private static Object _evaluateMultiplication(Object left, Object right) throws Exception
    {
        if (left instanceof Integer && right instanceof Integer) {
            return (int)left * (int)right;
        }

        BigDecimal[] values = _getNumbersAsDecimal(left, right);
        return values[0].multiply(values[1]);
    }

    /**
     * Divide two numeric values.
     *
     * @param left The left operand
     * @param right The right operand
     * @return The quotient of the two operands.
     * @throws Exception If an error occurred parsing the arguments to decimals.
     */
    private static Object _evaluateDivision(Object left, Object right) throws Exception
    {
        BigDecimal[] values = _getNumbersAsDecimal(left, right);
        return values[0].divide(values[1], RoundingMode.HALF_EVEN);
    }

    /**
     * Determine if the left and right values are equal to each other.
     *
     * @param left The left operand
     * @param right The right operand
     * @return true if the two values are equal
     * @throws Exception If an error occurred parsing the arguments to decimals.
     */
    private static Boolean _evaluateEquality(Object left, Object right) throws Exception
    {
        // Comparing int and decimal types requires conversion
        if (left instanceof BigDecimal || right instanceof BigDecimal) {
            return _compareNumbers(left, right, 0, false);
        }

        return left.equals(right);
    }

    /**
     * Compares two numbers using Comparable.
     *
     * @param left The left operand.
     * @param right The right operand.
     * @param compare The integer to compare to.  -1 is less than, 0 is equal, and 1 is greater than.
     * @param equality If the comparison should also be true if the values are equal.
     * @return The result of the comparison as a {@link Boolean}.
     * @throws Exception If a failure occurred during type conversion.
     */
    private static Boolean _compareNumbers(Object left, Object right, int compare, boolean equality) throws Exception
    {
        int check;

        if (left instanceof Integer && right instanceof Integer) {
            check = Integer.compare((int)left, (int)right);
        } else {
            BigDecimal[] values = _getNumbersAsDecimal(left, right);
            check = values[0].compareTo(values[1]);
        }

        if (equality) {
            return check == compare || check == 0;
        }

        return check == compare;
    }
}
