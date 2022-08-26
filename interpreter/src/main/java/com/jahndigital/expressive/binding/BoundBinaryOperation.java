package com.jahndigital.expressive.binding;

import com.jahndigital.expressive.syntax.SyntaxKind;

import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * An instance of this object represents a typed binary operation. If two incompatible types are found in a binary
 * operation, the error handler may decide what to do.
 */
public final class BoundBinaryOperation
{
    private final SyntaxKind _syntaxKind;
    private final BoundBinaryOperationKind _operatorKind;
    private final Type _leftType;
    private final Type _rightType;
    private final Type _resultType;

    /**
     * Returns a {@link BoundBinaryOperation} if a compatible one is found based on the provided {@link SyntaxKind} and types.
     *
     * @param kind  The SyntaxKind that represents the operation.
     * @param leftType The type of the left operand.
     * @param rightType The type of the right operand.
     * @return A compatible operation or null if one wasn't found.
     */
    static BoundBinaryOperation bind(SyntaxKind kind, Type leftType, Type rightType)
    {
        for (BoundBinaryOperation op : _operators) {
            if (op.getSyntaxKind() == kind && op.getLeftType() == leftType && op.getRightType() == rightType) {
                return op;
            }
        }

        return null;
    }

    /**
     * Init with a single type for left, right, and result.
     *
     * @param syntaxKind The SyntaxKind object that represents this operation.
     * @param operatorKind Gets the type-inspected operation. (E.g. a '+' between two integers is {@link BoundBinaryOperationKind#Addition}).
     * @param operandType The type of the left and right operand, as well as the type of the evaluated result.
     */
    private BoundBinaryOperation(SyntaxKind syntaxKind, BoundBinaryOperationKind operatorKind, Type operandType)
    {
        this(syntaxKind, operatorKind, operandType, operandType, operandType);
    }

    /**
     * Init with a single left and right type, and a different result type.
     *
     * @param syntaxKind The SyntaxKind object that represents this operation.
     * @param operatorKind Gets the type-inspected operation. (E.g. a '+' between two integers is {@link BoundBinaryOperationKind#Addition}).
     * @param operandsType The type of the left and right operand.
     * @param resultType The type of the evaluated result.
     */
    private BoundBinaryOperation(SyntaxKind syntaxKind, BoundBinaryOperationKind operatorKind, Type operandsType, Type resultType)
    {
        this(syntaxKind, operatorKind, operandsType, operandsType, resultType);
    }

    /**
     * Init with different left, right, and result types.
     *
     * @param syntaxKind The SyntaxKind object that represents this operation.
     * @param operatorKind The type-inspected operation. (E.g. a '+' between two integers is {@link BoundBinaryOperationKind#Addition}).
     * @param leftType The type of the left operand.
     * @param rightType The type of the right operand.
     * @param resultType The resultant type after the operation is evaluated.  (E.g. Integer > Integer results in a Boolean)
     */
    private BoundBinaryOperation(SyntaxKind syntaxKind, BoundBinaryOperationKind operatorKind, Type leftType, Type rightType, Type resultType)
    {
        _syntaxKind = syntaxKind;
        _operatorKind = operatorKind;
        _leftType = leftType;
        _rightType = rightType;
        _resultType = resultType;
    }

    /**
     * Gets the SyntaxKind object that represents this operation.
     */
    public SyntaxKind getSyntaxKind()
    {
        return _syntaxKind;
    }

    /**
     * Gets the type-inspected operation. (E.g. a '+' between two integers is {@link BoundBinaryOperationKind#Addition}).
     */
    public BoundBinaryOperationKind getOperatorKind()
    {
        return _operatorKind;
    }

    /**
     * Gets the type of the left operand.
     */
    public Type getLeftType()
    {
        return _leftType;
    }

    /**
     * Gets the type of the right operand.
     */
    public Type getRightType()
    {
        return _rightType;
    }

    /**
     * Gets the resultant type after the operation is evaluated.  (E.g. Integer > Integer results in a Boolean)
     */
    public Type getResultType()
    {
        return _resultType;
    }

    /**
     * An array that lists all the possible permutations of binary operations in the language.
     */
    private static final BoundBinaryOperation[] _operators = {
        // Mathematical Integers
        new BoundBinaryOperation(SyntaxKind.PlusToken, BoundBinaryOperationKind.Addition, Integer.class),
        new BoundBinaryOperation(SyntaxKind.MinusToken, BoundBinaryOperationKind.Subtraction, Integer.class),
        new BoundBinaryOperation(SyntaxKind.StarToken, BoundBinaryOperationKind.Multiplication, Integer.class),
        new BoundBinaryOperation(SyntaxKind.SlashToken, BoundBinaryOperationKind.Division, Integer.class),

        // Mathematical Decimal
        new BoundBinaryOperation(SyntaxKind.PlusToken, BoundBinaryOperationKind.Addition, BigDecimal.class),
        new BoundBinaryOperation(SyntaxKind.MinusToken, BoundBinaryOperationKind.Subtraction, BigDecimal.class),
        new BoundBinaryOperation(SyntaxKind.StarToken, BoundBinaryOperationKind.Multiplication, BigDecimal.class),
        new BoundBinaryOperation(SyntaxKind.SlashToken, BoundBinaryOperationKind.Division, BigDecimal.class),

        // Mathematical Decimal + Integer
        new BoundBinaryOperation(SyntaxKind.PlusToken, BoundBinaryOperationKind.Addition, Integer.class, BigDecimal.class, BigDecimal.class),
        new BoundBinaryOperation(SyntaxKind.PlusToken, BoundBinaryOperationKind.Addition, BigDecimal.class, Integer.class, BigDecimal.class),
        new BoundBinaryOperation(SyntaxKind.MinusToken, BoundBinaryOperationKind.Subtraction, Integer.class, BigDecimal.class, BigDecimal.class),
        new BoundBinaryOperation(SyntaxKind.MinusToken, BoundBinaryOperationKind.Subtraction, BigDecimal.class, Integer.class, BigDecimal.class),
        new BoundBinaryOperation(SyntaxKind.StarToken, BoundBinaryOperationKind.Multiplication, Integer.class, BigDecimal.class, BigDecimal.class),
        new BoundBinaryOperation(SyntaxKind.StarToken, BoundBinaryOperationKind.Multiplication, BigDecimal.class, Integer.class, BigDecimal.class),
        new BoundBinaryOperation(SyntaxKind.SlashToken, BoundBinaryOperationKind.Division, Integer.class, BigDecimal.class, BigDecimal.class),
        new BoundBinaryOperation(SyntaxKind.SlashToken, BoundBinaryOperationKind.Division, BigDecimal.class, Integer.class, BigDecimal.class),

        // Logical Boolean
        new BoundBinaryOperation(SyntaxKind.AndToken, BoundBinaryOperationKind.LogicalAnd, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.OrToken, BoundBinaryOperationKind.LogicalOr, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.EqualityToken, BoundBinaryOperationKind.Equals, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.NegatedEqualityToken, BoundBinaryOperationKind.NotEquals, Boolean.class),

        // Logical Integer
        new BoundBinaryOperation(SyntaxKind.EqualityToken, BoundBinaryOperationKind.Equals, Integer.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.NegatedEqualityToken, BoundBinaryOperationKind.NotEquals, Integer.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.GreaterThanToken, BoundBinaryOperationKind.GreaterThan, Integer.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.GreaterThanEqualToken, BoundBinaryOperationKind.GreaterThanOrEqualTo, Integer.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.LessThanToken, BoundBinaryOperationKind.LessThan, Integer.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.LessThanEqualToken, BoundBinaryOperationKind.LessThanOrEqualTo, Integer.class, Boolean.class),

        // Logical Decimal
        new BoundBinaryOperation(SyntaxKind.EqualityToken, BoundBinaryOperationKind.Equals, BigDecimal.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.NegatedEqualityToken, BoundBinaryOperationKind.NotEquals, BigDecimal.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.GreaterThanToken, BoundBinaryOperationKind.GreaterThan, BigDecimal.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.GreaterThanEqualToken, BoundBinaryOperationKind.GreaterThanOrEqualTo, BigDecimal.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.LessThanToken, BoundBinaryOperationKind.LessThan, BigDecimal.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.LessThanEqualToken, BoundBinaryOperationKind.LessThanOrEqualTo, BigDecimal.class, Boolean.class),

        // Logical Decimal + Integer
        new BoundBinaryOperation(SyntaxKind.GreaterThanToken, BoundBinaryOperationKind.GreaterThan, BigDecimal.class, Integer.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.GreaterThanEqualToken, BoundBinaryOperationKind.GreaterThanOrEqualTo, BigDecimal.class, Integer.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.LessThanToken, BoundBinaryOperationKind.LessThan, BigDecimal.class, Integer.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.LessThanEqualToken, BoundBinaryOperationKind.LessThanOrEqualTo, BigDecimal.class, Integer.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.GreaterThanToken, BoundBinaryOperationKind.GreaterThan, Integer.class, BigDecimal.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.GreaterThanEqualToken, BoundBinaryOperationKind.GreaterThanOrEqualTo, Integer.class, BigDecimal.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.LessThanToken, BoundBinaryOperationKind.LessThan, Integer.class, BigDecimal.class, Boolean.class),
        new BoundBinaryOperation(SyntaxKind.LessThanEqualToken, BoundBinaryOperationKind.LessThanOrEqualTo, Integer.class, BigDecimal.class, Boolean.class),
    };
}
