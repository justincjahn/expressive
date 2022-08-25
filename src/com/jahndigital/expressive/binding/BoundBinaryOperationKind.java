package com.jahndigital.expressive.binding;

/**
 * Values that represent possible type-inferenced binary operations.
 */
public enum BoundBinaryOperationKind
{
    Addition,
    Subtraction,
    Multiplication,
    Division,

    // Logic
    LogicalAnd,
    LogicalOr,
    Equals,
    NotEquals,
    GreaterThan,
    GreaterThanOrEqualTo,
    LessThan,
    LessThanOrEqualTo,
}
