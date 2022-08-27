package com.jahndigital.expressive.extensibility;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

/**
 * Represents metadata about the arguments of an {@link IFunction}.
 */
public final class ArgumentDefinition
{
    private final Boolean _nullable;
    private final Type[] _type;
    private final String _name;
    private final String _description;

    /**
     * Init
     *
     * @param name The name of the argument.
     * @param description A short description of the argument.
     * @param nullable If the argument is nullable.
     * @param type One or more types this argument can accept.
     */
    public ArgumentDefinition(String name, String description, Boolean nullable, Type ... type)
    {
        _name = name;
        _description = description;
        _nullable = nullable;
        _type = type;
    }

    /**
     * Returns the name of this argument.
     */
    public String getName()
    {
        return _name;
    }

    /**
     * Returns a brief description of this argument.
     */
    public String getDescription()
    {
        return _description;
    }

    /**
     * Returns true if the argument may be null.
     */
    public boolean isNullable()
    {
        return _nullable;
    }

    /**
     * Returns a list of types accepted by this argument.
     */
    public Collection<Type> getTypes()
    {
        return Arrays.asList(_type);
    }
}
