package com.jahndigital.expressive;

import com.jahndigital.expressive.binding.BoundExpression;
import com.jahndigital.expressive.syntax.BinaryExpressionSyntaxNode;
import com.jahndigital.expressive.syntax.SyntaxKind;
import com.jahndigital.expressive.syntax.SyntaxToken;
import com.jahndigital.expressive.syntax.UnaryExpressionSyntaxNode;

import java.util.*;

/**
 * Holds a list of diagnostic information about the parsing, lexing, binding, and evaluation of a syntax string.
 */
public class DiagnosticRepository implements Iterable<Diagnostic> {
    private final ArrayList<Diagnostic> _diagnostics = new ArrayList<>();

    @Override
    public Iterator<Diagnostic> iterator() {
        return _diagnostics.iterator();
    }

    /**
     * Gets a readonly {@link List} of diagnostic information.
     */
    public List<Diagnostic> asReadOnly()
    {
        return Collections.unmodifiableList(_diagnostics);
    }

    /**
     * Report an invalid character during lexing as an error.
     *
     * @param current The invalid character.
     * @param position The starting position of the character in the syntax string.
     */
    public void addBadCharacterInput(char current, int position)
    {
        _diagnostics.add(
                new Diagnostic(
                        new TextSpan(position, 1),
                        String.format("Bad character '%s' detected during lexing at position %d.", current, position),
                        DiagnosticLevel.ERROR
                )
        );
    }

    /**
     * Report an invalid cast to an Int32 during lexing as an error.
     *
     * @param text The text that couldn't be casted.
     * @param position The starting position of the text in the syntax string.
     */
    public void addInvalidCastToInt32(String text, int position)
    {
        _diagnostics.add(
                new Diagnostic(
                        new TextSpan(position, text.length()),
                        String.format("Unable to cast '%s' as a valid Int32 at position %d.", text, position),
                        DiagnosticLevel.ERROR
                )
        );
    }

    /**
     * Report an invalid cast to a Decimal during lexing as an error.
     *
     * @param text The text that couldn't be casted.
     * @param position The starting position of the text in the syntax string.
     */
    public void addInvalidCastToDecimal(String text, int position)
    {
        _diagnostics.add(
                new Diagnostic(
                        new TextSpan(position, text.length()),
                        String.format("Unable to cast '%s' as a valid Decimal at position %d.", text, position),
                        DiagnosticLevel.ERROR
                )
        );
    }

    public void addUnexpectedToken(SyntaxToken currentToken, SyntaxKind expectedKind)
    {
        _diagnostics.add(
                new Diagnostic(
                        currentToken.getTextSpan(),
                        String.format("Unexpected token <%s> during parsing at position %d. Expected <%s>.", currentToken.getKind(), currentToken.getPosition(), expectedKind),
                        DiagnosticLevel.ERROR
                )
        );
    }

    public void addException(Exception e)
    {
        _diagnostics.add(
                new Diagnostic(
                        new TextSpan(0, -1),
                        e.getMessage(),
                        DiagnosticLevel.CRIT
                )
        );
    }

    public void addInvalidUnaryOperator(UnaryExpressionSyntaxNode syntax, BoundExpression boundOperand)
    {
        _diagnostics.add(
                new Diagnostic(
                        syntax.getOperator().getTextSpan(),
                        String.format("Unary Operator '%s' is not defined for type %s.", syntax.getOperator().getText(), boundOperand.getType()),
                        DiagnosticLevel.ERROR
                )
        );
    }

    public void addInvalidBinaryOperation(BinaryExpressionSyntaxNode syntax, BoundExpression boundLeft, BoundExpression boundRight)
    {
        _diagnostics.add(
                new Diagnostic(
                        syntax.getOperator().getTextSpan(),
                        String.format("Binary Operator '%s' is not defined for types %s and %s.", syntax.getOperator().getText(), boundLeft.getType(), boundRight.getType()),
                        DiagnosticLevel.ERROR
                )
        );
    }
}
