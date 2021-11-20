package com.jahndigital.expressive;

import java.util.ArrayList;

/**
 * Represents a token created by the Lexer
 */
public class SyntaxToken extends SyntaxNode {
    private final SyntaxKind _kind;
    private final int _position;
    private final String _text;
    private final Object _value;

    @Override
    public SyntaxKind getKind() {
        return _kind;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        return new ArrayList<>();
    }

    public int getPosition() {
        return _position;
    }

    public String getText() {
        return _text;
    }

    public Object getValue() {
        return _value;
    }

    public SyntaxToken(SyntaxKind kind, int position, String text, Object value) {
        this._kind = kind;
        this._position = position;
        this._text = text;
        this._value = value;
    }
}
