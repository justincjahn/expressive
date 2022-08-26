package com.jahndigital.expressive;

import com.jahndigital.expressive.extensibility.IFunction;

import java.util.HashSet;
import java.util.Iterator;

/**
 * A repository built-in functions as {@link IFunction}s.
 */
public final class FunctionRepository implements Iterable<IFunction>
{
    public static final FunctionRepository DefaultFunctionRepository = new FunctionRepository();

    private final HashSet<IFunction> _functions = new HashSet<>();

    @Override
    public Iterator<IFunction> iterator()
    {
        return _functions.iterator();
    }

    public FunctionRepository add(IFunction function)
    {
        _functions.add(function);
        return this;
    }
}
