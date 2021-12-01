package com.jahndigital.expressive.binding;

import java.lang.reflect.Type;

/**
 * Represents an individual expression in a typed syntax tree.
 */
public abstract class BoundExpression extends BoundNode {
    public abstract Type getType();
}
