package com.jahndigital.expressive.binding;

import java.lang.reflect.Type;

/**
 * Represents an individual type-safe expression the tree.
 */
public abstract class BoundExpression extends BoundNode
{
    public abstract Type getType();
}
