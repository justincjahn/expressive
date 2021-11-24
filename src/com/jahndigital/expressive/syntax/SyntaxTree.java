package com.jahndigital.expressive.syntax;

/**
 * Represents the head of an Abstract Syntax Tree (AST).
 */
public final class SyntaxTree {
    private final Iterable<String> _errors;
    private final ExpressionSyntaxNode _root;
    private final SyntaxToken _eofToken;

    public Iterable<String> getErrors() {
        return _errors;
    }

    public ExpressionSyntaxNode getRoot() {
        return _root;
    }

    public SyntaxToken getEndOfFileToken() {
        return _eofToken;
    }

    public SyntaxTree(Iterable<String> errors, ExpressionSyntaxNode root, SyntaxToken endOfFileToken) {
        this._errors = errors;
        this._root = root;
        this._eofToken = endOfFileToken;
    }

    public static SyntaxTree parse(String text) {
        Parser parser = new Parser(text);
        return parser.parse();
    }
}
