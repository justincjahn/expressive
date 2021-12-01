package com.jahndigital.expressive.binding;

import com.jahndigital.expressive.syntax.SyntaxKind;
import java.lang.reflect.Type;

/**
 * Represents a typed version of a {@link com.jahndigital.expressive.syntax.UnaryExpressionSyntaxNode}.  If an incompatible
 * type is found in a unary operation, the error handler may decide what to do.
 */
final class BoundUnaryOperator {
    private final SyntaxKind _syntaxKind;
    private final BoundUnaryOperatorKind _operatorKind;
    private final Type _operandType;
    private final Type _resultType;

    /**
     * Gets the SyntaxKind object that represents this operation.
     */
    public SyntaxKind getSyntaxKind()
    {
        return _syntaxKind;
    }

    /**
     * Gets the type-inspected operation. (E.g. a '-' on an integer is {@link BoundUnaryOperatorKind#Negation}).
     */
    public BoundUnaryOperatorKind getOperatorKind()
    {
        return _operatorKind;
    }

    /**
     * Gets the type of the operand.
     */
    public Type getOperandType()
    {
        return _operandType;
    }

    /**
     * Gets the resultant type after the operation is evaluated.
     */
    public Type getResultType()
    {
        return _resultType;
    }

    /**
     * Init
     *
     * @param syntaxKind The SyntaxKind object that represents this operation.
     * @param operatorKind The type-inspected operation. (E.g. a '-' on an integer is {@link BoundUnaryOperatorKind#Negation}).
     * @param operandType The type of the operand and the result of the evaluation.
     */
    private BoundUnaryOperator(SyntaxKind syntaxKind, BoundUnaryOperatorKind operatorKind, Type operandType)
    {
        this(syntaxKind, operatorKind, operandType, operandType);
    }

    /**
     * Init
     *
     * @param syntaxKind The SyntaxKind object that represents this operation.
     * @param operatorKind The type-inspected operation. (E.g. a '-' on an integer is {@link BoundUnaryOperatorKind#Negation}).
     * @param operandType The type of the operand.
     * @param resultType The type of the resulting operation.
     */
    private BoundUnaryOperator(SyntaxKind syntaxKind, BoundUnaryOperatorKind operatorKind, Type operandType, Type resultType)
    {
        this._syntaxKind = syntaxKind;
        this._operatorKind = operatorKind;
        this._operandType = operandType;
        this._resultType = resultType;
    }

    /**
     * An array that lists all the possible permutations of unary operations in the language.
     */
    private static BoundUnaryOperator[] _operators = {
        new BoundUnaryOperator(SyntaxKind.PlusToken, BoundUnaryOperatorKind.Identity, Integer.class),
        new BoundUnaryOperator(SyntaxKind.MinusToken, BoundUnaryOperatorKind.Negation, Integer.class),

        // Logical
        new BoundUnaryOperator(SyntaxKind.ExclamationPointToken, BoundUnaryOperatorKind.LogicalNegation, Boolean.class)
    };

    /**
     * Returns a {@link BoundUnaryOperator} if a compatible one is found based on the provided {@link SyntaxKind} and type.
     *
     * @param kind The SyntaxKind that represents the operation.
     * @param operandType The type of the operand.
     * @return A compatible operation or null if one wasn't found.
     */
    public static BoundUnaryOperator bind(SyntaxKind kind, Type operandType)
    {
        for (BoundUnaryOperator op : _operators) {
            if (op.getSyntaxKind() == kind && op.getOperandType() == operandType) {
                return op;
            }
        }

        return null;
    }
}
