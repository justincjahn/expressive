package com.jahndigital.expressive;

import java.util.ArrayList;

/**
 * Reads the provided text string and converts it into tokens.
 */
public class Lexer {
    private final ArrayList<String> _errors = new ArrayList<>();
    private final String _text;
    private int _position = 0;

    public Iterable<String> getErrors() {
        return _errors;
    }

    protected char getCurrent() {
        if (_position >= _text.length()) {
            return '\0';
        }

        return _text.charAt(_position);
    }

    public Lexer(String text) {
        _text = text;
    }

    private void Next() {
        _position++;
    }

    /**
     * Get the next token from the text and return it as a SyntaxToken object.
     *
     * @return The next token in the text, or an EndOfFileToken.
     */
    public SyntaxToken NextToken() {
        if (_position >= _text.length()) {
            return new SyntaxToken(SyntaxKind.EndOfFileToken, _position, "\0", null);
        }

        if (Character.isDigit(getCurrent())) {
            int start = _position;

            while (Character.isDigit(getCurrent())) {
                Next();
            }

            String text = _text.substring(start, _position);

            Integer num = null;
            try {
                num = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                _errors.add(String.format("ERROR: The number '%s' is not a valid Int32", _text));
            }

            return new SyntaxToken(SyntaxKind.NumberToken, start, text, num);
        }

        if (Character.isWhitespace(getCurrent())) {
            int start = _position;

            while (Character.isWhitespace(getCurrent())) {
                Next();
            }

            String text = _text.substring(start, _position);
            return new SyntaxToken(SyntaxKind.WhitespaceToken, start, text, null);
        }

        if (getCurrent() == '+') {
            return new SyntaxToken(SyntaxKind.PlusToken, _position++, "+", null);
        }

        if (getCurrent() == '-') {
            return new SyntaxToken(SyntaxKind.MinusToken, _position++, "-", null);
        }

        if (getCurrent() == '*') {
            return new SyntaxToken(SyntaxKind.StarToken, _position++, "*", null);
        }

        if (getCurrent() == '/') {
            return new SyntaxToken(SyntaxKind.SlashToken, _position++, "/", null);
        }

        if (getCurrent() == '(') {
            return new SyntaxToken(SyntaxKind.OpenParenthesisToken, _position++, "(", null);
        }

        if (getCurrent() == ')') {
            return new SyntaxToken(SyntaxKind.CloseParenthesisToken, _position++, ")", null);
        }

        _errors.add(String.format("ERROR: Bad character input '%s' at position %d.", getCurrent(), _position));
        return new SyntaxToken(SyntaxKind.BadToken, _position++, _text.substring(_position - 1, _position), null);
    }
}
