package com.jahndigital.expressive.extensibility;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Example of how to build a custom function for expressive.
 */
public class HelloWorldFunction implements IFunction {
    @Override
    public String getName()
    {
        return "HELLO";
    }

    @Override
    public Type getReturnType()
    {
        return Integer.class;
    }

    @Override
    public List<ArgumentDefinition> getArguments()
    {
        return Collections.singletonList(
            new ArgumentDefinition("arg1", "The first argument.", true, Integer.class)
        );
    }

    @Override
    public Object execute(List<Object> args, Map<String, Object> ctx)
    {
        return ((int)args.get(0)) * 10;
    }
}
