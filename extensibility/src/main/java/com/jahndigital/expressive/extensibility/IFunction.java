package com.jahndigital.expressive.extensibility;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Defines a contract for objects that may be registered to expressive as callable functions.
 * Expressive will validate that the types in the expression are supported by the arguments you define.  Additionally, it
 * will ensure that the return type of the `execute` function is supported as well.
 */
public interface IFunction
{
    /**
     * Gets the name of the function.
     *
     * @return The name
     */
    String getName();

    /**
     * Gets the return type of the execute function.
     *
     * @return The return type
     */
    Type getReturnType();

    /**
     * Gets a list of input arguments in the order they must appear.
     *
     * @return A list of {@link ArgumentDefinition} objects.
     */
    List<ArgumentDefinition> getArguments();

    /**
     * Execute the function with the provided arguments and execution context.
     *
     * @param args An ordered list of arguments.
     * @param ctx The execution contact.  Contains arbitrary objects that may change during runtime.
     * @return The result of the function.
     */
    Object execute(List<Object> args, Map<String, Object> ctx);
}
