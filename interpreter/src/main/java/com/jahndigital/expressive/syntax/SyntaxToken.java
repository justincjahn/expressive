package com.jahndigital.expressive.syntax;

import com.jahndigital.expressive.TextSpan;

import java.util.ArrayList;

/**
 * Represents a token created by the Lexer
 */
public final class SyntaxToken extends SyntaxNode
{
    private final SyntaxKind _kind;
    private final int _position;
    private final String _text;
    private final Object _value;

    /**
     * Gets the index of the starting position in the original string.
     */
    public int getPosition()
    {
        return _position;
    }

    /**
     * Gets the raw text of the token.
     */
    public String getText()
    {
        return _text;
    }

    /**
     * Gets the value of the token, or null.
     */
    public Object getValue()
    {
        return _value;
    }

    /**
     * Returns an {@link TextSpan} object with the position and length of the token.
     */
    public TextSpan getTextSpan()
    {
        return new TextSpan(_position, _text.length());
    }

    /**
     * Init
     */
    public SyntaxToken(SyntaxKind kind, int position, String text, Object value)
    {
        this._kind = kind;
        this._position = position;
        this._text = text;
        this._value = value;
    }

    @Override
    public SyntaxKind getKind()
    {
        return _kind;
    }

    @Override
    public Iterable<SyntaxNode> getChildren()
    {
        return new ArrayList<>();
    }
}
