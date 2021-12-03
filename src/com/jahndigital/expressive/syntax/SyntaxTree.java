package com.jahndigital.expressive.syntax;

import com.jahndigital.expressive.Diagnostic;
import com.jahndigital.expressive.DiagnosticLevel;
import com.jahndigital.expressive.DiagnosticRepository;
import com.jahndigital.expressive.binding.BoundExpression;
import com.jahndigital.expressive.binding.BoundSyntaxTree;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the root of a syntax tree, including any diagnostic information resulting from the lexing and parsing of
 * the tree.
 */
public final class SyntaxTree {
    private final DiagnosticRepository _diagnostics;
    private final ExpressionSyntaxNode _root;
    private final SyntaxToken _eofToken;

    /**
     * Parse the provided syntax string into a syntax tree.
     */
    public static SyntaxTree parse(String text) {
        return new Parser(text, new DiagnosticRepository()).parse();
    }

    /**
     * Gets diagnostic information from the parsing and binding.
     */
    public Iterable<Diagnostic> getDiagnostics() {
        return _diagnostics;
    }

    /**
     * Gets the root {@link SyntaxNode} of the parsed expression.
     */
    public ExpressionSyntaxNode getRoot() {
        return _root;
    }
    /**

     * Gets the End of File token for the parsed tree.
     */
    public SyntaxToken getEndOfFileToken() {
        return _eofToken;
    }

    /**
     * Init
     *
     * @param diagnostics
     * @param root
     * @param endOfFileToken
     */
    SyntaxTree(DiagnosticRepository diagnostics, ExpressionSyntaxNode root, SyntaxToken endOfFileToken) {
        _diagnostics = diagnostics;
        _root = root;
        _eofToken = endOfFileToken;
    }

    /**
     * Pretty print this syntax tree.
     */
    public void pprint()
    {
        SyntaxNode.pprint(getRoot());
    }

    /**
     * Binds this syntax tree and returns the {@link BoundExpression}.
     *
     * @exception Exception If errors exist before the binding process begins.
     */
    public BoundSyntaxTree bind() throws Exception {
        List<Diagnostic> errors = _diagnostics.asReadOnly().stream().filter(x -> x.getLevel() == DiagnosticLevel.ERROR).collect(Collectors.toList());
        if (!errors.isEmpty()) {
            throw new Exception("Cannot bind a syntax tree with lexer or parser errors.");
        }

        return BoundSyntaxTree.bind(this, _diagnostics);
    }
}
