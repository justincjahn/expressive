package com.jahndigital.expressive.binding;

import com.jahndigital.expressive.syntax.SyntaxKind;

import java.lang.reflect.Type;

/**
 * Represents a typed version of a {@link com.jahndigital.expressive.syntax.BinaryExpressionSyntaxNode}.  If two incompatible
 * types are found in a binary operation, the error handler may decide what to do.
 */
public final class BoundBinaryOperator {
    private final SyntaxKind _syntaxKind;
    private final BoundBinaryOperatorKind _operatorKind;
    private final Type _leftType;
    private final Type _rightType;
    private final Type _resultType;

    /**
     * Gets the SyntaxKind object that represents this operation.
     */
    public SyntaxKind getSyntaxKind()
    {
        return _syntaxKind;
    }

    /**
     * Gets the type-inspected operation. (E.g. a '+' between two integers is {@link BoundBinaryOperatorKind#Addition}).
     */
    public BoundBinaryOperatorKind getOperatorKind()
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
     * Init
     *
     * @param syntaxKind The SyntaxKind object that represents this operation.
     * @param operatorKind Gets the type-inspected operation. (E.g. a '+' between two integers is {@link BoundBinaryOperatorKind#Addition}.
     * @param operandType The type of the left and right operand, as well as the type of the evaluated result.
     */
    private BoundBinaryOperator(SyntaxKind syntaxKind, BoundBinaryOperatorKind operatorKind, Type operandType)
    {
       this(syntaxKind, operatorKind, operandType, operandType, operandType);
    }

    /**
     *
     * @param syntaxKind The SyntaxKind object that represents this operation.
     * @param operatorKind Gets the type-inspected operation. (E.g. a '+' between two integers is {@link BoundBinaryOperatorKind#Addition}.
     * @param operandsType The type of the left and right operand.
     * @param resultType The type of the evaluated result.
     */
    private BoundBinaryOperator(SyntaxKind syntaxKind, BoundBinaryOperatorKind operatorKind, Type operandsType, Type resultType)
    {
        this(syntaxKind, operatorKind, operandsType, operandsType, resultType);
    }

    /**
     * Init
     *
     * @param syntaxKind The SyntaxKind object that represents this operation.
     * @param operatorKind The type-inspected operation. (E.g. a '+' between two integers is {@link BoundBinaryOperatorKind#Addition}.
     * @param leftType The type of the left operand.
     * @param rightType The type of the right operand.
     * @param resultType The resultant type after the operation is evaluated.  (E.g. Integer > Integer results in a Boolean)
     */
    private BoundBinaryOperator(SyntaxKind syntaxKind, BoundBinaryOperatorKind operatorKind, Type leftType, Type rightType, Type resultType)
    {
        _syntaxKind = syntaxKind;
        _operatorKind = operatorKind;
        _leftType = leftType;
        _rightType = rightType;
        _resultType = resultType;
    }

    /**
     * An array that lists all the possible permutations of binary operations in the language.
     */
    private static BoundBinaryOperator[] _operators = {
            new BoundBinaryOperator(SyntaxKind.PlusToken, BoundBinaryOperatorKind.Addition, Integer.class),
            new BoundBinaryOperator(SyntaxKind.MinusToken, BoundBinaryOperatorKind.Subtraction, Integer.class),
            new BoundBinaryOperator(SyntaxKind.StarToken, BoundBinaryOperatorKind.Multiplication, Integer.class),
            new BoundBinaryOperator(SyntaxKind.SlashToken, BoundBinaryOperatorKind.Division, Integer.class),

            // Logical
            new BoundBinaryOperator(SyntaxKind.AndToken, BoundBinaryOperatorKind.LogicalAnd, Boolean.class),
            new BoundBinaryOperator(SyntaxKind.OrToken, BoundBinaryOperatorKind.LogicalOr, Boolean.class),
            new BoundBinaryOperator(SyntaxKind.EqualityToken, BoundBinaryOperatorKind.Equals, Boolean.class),
            new BoundBinaryOperator(SyntaxKind.NegatedEqualityToken, BoundBinaryOperatorKind.NotEquals, Boolean.class),

            new BoundBinaryOperator(SyntaxKind.EqualityToken, BoundBinaryOperatorKind.Equals, Integer.class, Boolean.class),
            new BoundBinaryOperator(SyntaxKind.NegatedEqualityToken, BoundBinaryOperatorKind.NotEquals, Integer.class, Boolean.class)
    };

    /**
     * Returns a {@link BoundBinaryOperator} if a compatible one is found based on the provided {@link SyntaxKind} and types.
     *
     * @param kind  The SyntaxKind that represents the operation.
     * @param leftType The type of the left operand.
     * @param rightType The type of the right operand.
     * @return A compatible operation or null if one wasn't found.
     */
    public static BoundBinaryOperator bind(SyntaxKind kind, Type leftType, Type rightType)
    {
        for (BoundBinaryOperator op : _operators) {
            if (op.getSyntaxKind() == kind && op.getLeftType() == leftType && op.getRightType() == rightType) {
                return op;
            }
        }

        return null;
    }
}
