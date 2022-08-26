package com.jahndigital.expressive.binding;

import com.jahndigital.expressive.syntax.SyntaxKind;
import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * An instance of this object represents a typed unary operation. If an incompatible type is found in the operand, the
 * error handler may decide what to do.
 */
final class BoundUnaryOperation
{
    private final SyntaxKind _syntaxKind;
    private final BoundUnaryOperationKind _operatorKind;
    private final Type _operandType;
    private final Type _resultType;

    /**
     * Returns a {@link BoundUnaryOperation} if a compatible one is found based on the provided {@link SyntaxKind} and type.
     *
     * @param kind The SyntaxKind that represents the operation.
     * @param operandType The type of the operand.
     * @return A compatible operation or null if one wasn't found.
     */
    public static BoundUnaryOperation bind(SyntaxKind kind, Type operandType)
    {
        for (BoundUnaryOperation op : _operators) {
            if (op.getSyntaxKind() == kind && op.getOperandType() == operandType) {
                return op;
            }
        }

        return null;
    }

    /**
     * Init
     *
     * @param syntaxKind The SyntaxKind object that represents this operation.
     * @param operatorKind The type-inspected operation. (E.g. a '-' on an integer is {@link BoundUnaryOperationKind#Negation}).
     * @param operandType The type of the operand and the result of the evaluation.
     */
    private BoundUnaryOperation(SyntaxKind syntaxKind, BoundUnaryOperationKind operatorKind, Type operandType)
    {
        this(syntaxKind, operatorKind, operandType, operandType);
    }

    /**
     * Init
     *
     * @param syntaxKind The SyntaxKind object that represents this operation.
     * @param operatorKind The type-inspected operation. (E.g. a '-' on an integer is {@link BoundUnaryOperationKind#Negation}).
     * @param operandType The type of the operand.
     * @param resultType The type of the resulting operation.
     */
    private BoundUnaryOperation(SyntaxKind syntaxKind, BoundUnaryOperationKind operatorKind, Type operandType, Type resultType)
    {
        this._syntaxKind = syntaxKind;
        this._operatorKind = operatorKind;
        this._operandType = operandType;
        this._resultType = resultType;
    }

    /**
     * Gets the SyntaxKind object that represents this operation.
     */
    public SyntaxKind getSyntaxKind()
    {
        return _syntaxKind;
    }

    /**
     * Gets the type-inspected operation. (E.g. a '-' on an integer is {@link BoundUnaryOperationKind#Negation}).
     */
    public BoundUnaryOperationKind getOperatorKind()
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
     * An array that lists all the possible permutations of unary operations in the language.
     */
    private static final BoundUnaryOperation[] _operators = {
        new BoundUnaryOperation(SyntaxKind.PlusToken, BoundUnaryOperationKind.Identity, Integer.class),
        new BoundUnaryOperation(SyntaxKind.MinusToken, BoundUnaryOperationKind.Negation, Integer.class),

        // Decimal
        new BoundUnaryOperation(SyntaxKind.PlusToken, BoundUnaryOperationKind.Identity, BigDecimal.class),
        new BoundUnaryOperation(SyntaxKind.MinusToken, BoundUnaryOperationKind.Negation, BigDecimal.class),

        // Logical
        new BoundUnaryOperation(SyntaxKind.ExclamationPointToken, BoundUnaryOperationKind.LogicalNegation, Boolean.class)
    };
}
