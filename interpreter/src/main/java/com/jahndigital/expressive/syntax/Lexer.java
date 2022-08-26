package com.jahndigital.expressive.syntax;

import com.jahndigital.expressive.DiagnosticRepository;

import java.math.BigDecimal;

/**
 * Reads the provided text string and converts it into tokens.
 */
final class Lexer
{
    private final DiagnosticRepository _diagnostics;
    private final String _text;
    private int _position = 0;

    /**
     * Look ahead in the token array without changing the current position.
     *
     * @param offset The number of spaces to look ahead.
     * @return The token at position + offset, or a null-terminator.
     */
    private char peek(int offset)
    {
        int index = _position + offset;

        if (index >= _text.length()) {
            return '\0';
        }

        return _text.charAt(index);
    }

    /**
     * Gets the character at the current position, or a null terminator if we've reached the end.
     */
    private char getCurrent()
    {
        return peek(0);
    }

    /**
     *
     * @param text The expression to lex.
     * @param diagnostics The repository to use when reporting issues with lexing.
     */
    public Lexer(String text, DiagnosticRepository diagnostics)
    {
        _text = text;
        _diagnostics = diagnostics;
    }

    /**
     * Increments the position forward by one.
     */
    private void next()
    {
        _position++;
    }

    /**
     * Get the next token from the text and return it as a SyntaxToken object.
     *
     * @return The next token in the text, or an EndOfFileToken.
     */
    SyntaxToken nextToken()
    {
        if (_position >= _text.length()) {
            return new SyntaxToken(SyntaxKind.EndOfFileToken, _position, "\0", null);
        }

        if (Character.isDigit(getCurrent())) {
            int start = _position;
            int numPeriods = 0;

            while (Character.isDigit(getCurrent()) || getCurrent() == '.') {
                if (getCurrent() == '.')
                {
                    numPeriods++;
                }

                next();
            }

            String text = _text.substring(start, _position);

            if (numPeriods > 0) {
                BigDecimal num = null;

                try {
                    num = new BigDecimal(text);
                } catch (NumberFormatException e) {
                    _diagnostics.addInvalidCastToDecimal(_text, _position);
                }

                return new SyntaxToken(SyntaxKind.NumberToken, start, text, num);
            }

            Integer num = null;
            try {
                num = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                _diagnostics.addInvalidCastToInt32(_text, _position);
            }

            return new SyntaxToken(SyntaxKind.NumberToken, start, text, num);
        }

        if (Character.isWhitespace(getCurrent())) {
            int start = _position;

            while (Character.isWhitespace(getCurrent())) {
                next();
            }

            String text = _text.substring(start, _position);
            return new SyntaxToken(SyntaxKind.WhitespaceToken, start, text, null);
        }

        if (Character.isLetter(getCurrent())) {
            int start = _position;

            while (Character.isLetter(getCurrent()) || Character.isDigit(getCurrent())) {
                next();
            }

            String text = _text.substring(start, _position);
            SyntaxKind kind = SyntaxFacts.getKeywordKind(text);
            return new SyntaxToken(kind, start, text, null);
        }

        switch (getCurrent()) {
            case '+':
                return new SyntaxToken(SyntaxKind.PlusToken, _position++, "+", null);
            case '-':
                return new SyntaxToken(SyntaxKind.MinusToken, _position++, "-", null);
            case '*':
                return new SyntaxToken(SyntaxKind.StarToken, _position++, "*", null);
            case '/':
                return new SyntaxToken(SyntaxKind.SlashToken, _position++, "/", null);
            case '(':
                return new SyntaxToken(SyntaxKind.OpenParenthesisToken, _position++, "(", null);
            case ')':
                return new SyntaxToken(SyntaxKind.CloseParenthesisToken, _position++, ")", null);
            case '&':
            {
                if (peek(1) == '&') {
                    return new SyntaxToken(SyntaxKind.AndToken, _position += 2, "&&", null);
                }

                break;
            }
            case '|':
            {
                if (peek(1) == '|') {
                    return new SyntaxToken(SyntaxKind.OrToken, _position += 2, "||", null);
                }

                break;
            }
            case '=': {
                if (peek(1) == '=') {
                    return new SyntaxToken(SyntaxKind.EqualityToken, _position += 2, "==", null);
                }

                break;
            }
            case '!': {
                if (peek(1) == '=') {
                    return new SyntaxToken(SyntaxKind.NegatedEqualityToken, _position += 2, "!=", null);
                } else {
                    return new SyntaxToken(SyntaxKind.ExclamationPointToken, _position++, "!", null);
                }
            }
            case '>': {
                if (peek(1) == '=') {
                    return new SyntaxToken(SyntaxKind.GreaterThanEqualToken, _position += 2, ">=", null);
                } else {
                    return new SyntaxToken(SyntaxKind.GreaterThanToken, _position++, ">", null);
                }
            }
            case '<': {
                if (peek(1) == '=') {
                    return new SyntaxToken(SyntaxKind.LessThanEqualToken, _position += 2, "<=", null);
                } else {
                    return new SyntaxToken(SyntaxKind.LessThanToken, _position++, "<", null);
                }
            }
        }

        _diagnostics.addBadCharacterInput(getCurrent(), _position);
        return new SyntaxToken(SyntaxKind.BadToken, _position++, _text.substring(_position - 1, _position), null);
    }
}
