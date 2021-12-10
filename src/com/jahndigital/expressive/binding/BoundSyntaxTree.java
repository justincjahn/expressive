package com.jahndigital.expressive.binding;

import com.jahndigital.expressive.Diagnostic;
import com.jahndigital.expressive.DiagnosticLevel;
import com.jahndigital.expressive.DiagnosticRepository;
import com.jahndigital.expressive.Evaluator;
import com.jahndigital.expressive.syntax.SyntaxTree;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Diagnostic> getDiagnostics()
    {
        return _diagnostics.asReadOnly();
    }

    /**
     * Gets the root {@link BoundExpression} resulting from the binding process.
     *
     * @return A {@link BoundExpression} or {@link null}.
     */
    public BoundExpression getRoot()
    {
        return _root;
    }

    /**
     * Evaluates this bound syntax tree.
     *
     * @return The result of the evaluation.
     * @throws Exception If an unrecoverable error occurred during evaluation or there were errors during parsing.
     */
    public Object evaluate() throws Exception
    {
        List<Diagnostic> errors = getDiagnostics()
                .stream()
                .filter(x -> x.getLevel() == DiagnosticLevel.ERROR || x.getLevel() == DiagnosticLevel.CRIT)
                .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            throw new Exception(
                    String.format("Unable to evaluate expression: %d errors encountered during parsing.", errors.size())
            );
        }

        if (getRoot() == null) {
            return null;
        }

        Evaluator evaluator = new Evaluator(getRoot());
        return evaluator.evaluate();
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
