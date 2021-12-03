package com.jahndigital.expressive.binding;

import com.jahndigital.expressive.Diagnostic;
import com.jahndigital.expressive.DiagnosticRepository;
import com.jahndigital.expressive.syntax.SyntaxTree;
import com.sun.istack.internal.Nullable;

/**
 * Represents a typed version of a {@link SyntaxTree}.
 */
public final class BoundSyntaxTree {
    private final DiagnosticRepository _diagnostics;
    private final BoundExpression _root;

    /**
     * Init
     *
     * @param tree The {@link SyntaxTree} to walk and bind.
     * @param diagnostics The repository to use when reporting issues with binding.
     */
    public static BoundSyntaxTree bind(SyntaxTree tree, DiagnosticRepository diagnostics)
    {
        return new Binder(diagnostics).bind(tree);
    }

    /**
     * Gets a list of diagnostic information from the lexing, parsing, and binding process.
     */
    public Iterable<Diagnostic> getDiagnostics()
    {
        return _diagnostics;
    }

    /**
     * Gets the root {@link BoundExpression} resulting from the binding process.
     *
     * @return A {@link BoundExpression} or {@link null}.
     */
    @Nullable
    public BoundExpression getRoot()
    {
        return _root;
    }

    /**
     * Init
     *
     * @param diagnostics The repository to use when reporting issues with binding.
     * @param root The root {@link BoundExpression} node
     */
    BoundSyntaxTree(DiagnosticRepository diagnostics, BoundExpression root)
    {
        _diagnostics = diagnostics;
        _root = root;
    }
}
