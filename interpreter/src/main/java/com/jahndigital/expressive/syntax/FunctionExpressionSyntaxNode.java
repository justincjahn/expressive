package com.jahndigital.expressive.syntax;

import com.jahndigital.expressive.extensibility.IFunction;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an {@link IFunction} object that should be called dynamically during evaluation to produce a literal value.
 */
public final class FunctionExpressionSyntaxNode extends ExpressionSyntaxNode
{
    private final SyntaxToken _functionNameToken;
    private final IFunction _function;
    private final List<ExpressionSyntaxNode> _argumentTokens;

    /**
     * Init
     *
     * @param functionNameToken The {@link SyntaxToken} containing the function's name.
     * @param function The {@link IFunction} object, if resolved.
     * @param argumentTokens A list of {@link ExpressionSyntaxNode} representing the function's arguments.
     */
    public FunctionExpressionSyntaxNode(SyntaxToken functionNameToken, @Nullable IFunction function, List<ExpressionSyntaxNode> argumentTokens)
    {
        _functionNameToken = functionNameToken;
        _function = function;
        _argumentTokens = argumentTokens;
    }

    /**
     * Gets the {@link SyntaxToken} that represents the name of the function.
     */
    public SyntaxToken getFunctionName()
    {
        return _functionNameToken;
    }

    /**
     * Get the {@link IFunction} object if it was resolved, otherwise null.
     */
    public @Nullable IFunction getFunction()
    {
        return _function;
    }

    /**
     * Get a read only list of arguments that must be passed to the {@link IFunction} during evaluation.
     */
    public List<ExpressionSyntaxNode> getArguments()
    {
        return Collections.unmodifiableList(_argumentTokens);
    }

    @Override
    public SyntaxKind getKind()
    {
        return SyntaxKind.FunctionExpression;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        ArrayList<SyntaxNode> children = new ArrayList<>();
        children.add(_functionNameToken);
        children.addAll(_argumentTokens);
        return children;
    }
}
