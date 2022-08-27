package com.jahndigital.expressive;

import com.jahndigital.expressive.extensibility.IFunction;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Thrown when a function throws an exception during evaluation.
 */
public class FunctionExecutionFailedException extends Exception {
    private final IFunction _function;
    private final List<Object> _evaluatedArguments;
    private final Map<String, Object> _runtimeContext;
    private final Exception _innerException;

    /**
     * Init
     *
     * @param innerException The {@link Exception} thrown by the {@link IFunction} call.
     * @param function The {@link IFunction} that was called.
     * @param evaluatedArguments A list of the evaluated evaluatedArguments passed in to the {@link IFunction}.
     * @param runtimeContext The runtime runtimeContext passed in to the {@link IFunction}
     */
    public FunctionExecutionFailedException(Exception innerException, IFunction function, List<Object> evaluatedArguments, Map<String,Object> runtimeContext)
    {
        super(innerException.getMessage());

        _function = function;
        _evaluatedArguments = evaluatedArguments;
        _runtimeContext = runtimeContext;
        _innerException = innerException;
    }

    public IFunction getFunction()
    {
        return _function;
    }

    public List<Object> getEvaluatedArguments()
    {
        return Collections.unmodifiableList(_evaluatedArguments);
    }

    public Map<String,Object> getRuntimeContext()
    {
        return Collections.unmodifiableMap(_runtimeContext);
    }

    public Exception getInnerException()
    {
        return _innerException;
    }
}
