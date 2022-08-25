package com.jahndigital.expressive;

/**
 * Represents an issue that occurred during the lexing, parsing, and/or evaluation of a string.
 */
public final class Diagnostic {
    private final TextSpan _span;
    private final String _message;
    private final DiagnosticLevel _level;

    public TextSpan getSpan()
    {
        return _span;
    }

    /**
     * Gets the diagnostic message.
     *
     * @return The message.
     */
    public String getMessage()
    {
        return _message;
    }

    /**
     * Gets the Diagnostic Level
     *
     * @return The level.
     */
    public DiagnosticLevel getLevel()
    {
        return _level;
    }

    public Diagnostic(TextSpan span, String message, DiagnosticLevel level)
    {
        _span = span;
        _message = message;
        _level = level;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", _level, _message);
    }
}
