package com.jahndigital.expressive.binding;

import com.jahndigital.expressive.extensibility.IFunction;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * A type of {@link BoundExpression} object that represents a dynamic function call to a registered {@link IFunction}.
 * During evaluation, the `execute` method of the function will be called with evaluated arguments and a runtime context
 * as parameters.
 */
public class BoundFunctionExpression extends BoundExpression
{
    private final IFunction _function;
    private final List<BoundExpression> _arguments;

    /**
     * Init
     *
     * @param function The {@link IFunction} object that this {@link BoundExpression} represents.
     * @param arguments A list of arguments that must be passed to the {@link IFunction} object after evaluation.
     */
    public BoundFunctionExpression(IFunction function, List<BoundExpression> arguments)
    {
        _function = function;
        _arguments = arguments;
    }

    /**
     * Get the {@link IFunction} object.
     */
    public IFunction getFunction()
    {
        return _function;
    }

    /**
     * Returns a read only ordered list of {@link BoundExpression} objects representing the function's arguments.
     */
    public List<BoundExpression> getArguments()
    {
        return Collections.unmodifiableList(_arguments);
    }

    /**
     * Get the return type of the function.
     */
    @Override
    public Type getType()
    {
        return _function.getReturnType();
    }

    /**
     * Return the {@link BoundNodeKind} of this object.
     */
    @Override
    public BoundNodeKind getKind()
    {
        return BoundNodeKind.FunctionExpression;
    }
}
