package com.jahndigital.expressive.binding;

/**
 * Represents a generic element of a typed syntax tree
 */
public abstract class BoundNode {
    public abstract BoundNodeKind getKind();
}
