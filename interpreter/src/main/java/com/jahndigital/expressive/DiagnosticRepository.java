package com.jahndigital.expressive;

import com.jahndigital.expressive.binding.BoundExpression;
import com.jahndigital.expressive.extensibility.IFunction;
import com.jahndigital.expressive.syntax.*;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Holds a list of diagnostic information about the parsing, lexing, binding, and evaluation of a syntax string.
 */
public class DiagnosticRepository implements Iterable<Diagnostic>
{
    public static final DiagnosticRepository DefaultDiagnosticRepository = new DiagnosticRepository();

    private final ArrayList<Diagnostic> _diagnostics = new ArrayList<>();

    @Override
    public Iterator<Diagnostic> iterator()
    {
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
     * Clears the diagnostic repository.
     */
    public void reset()
    {
        _diagnostics.clear();
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
     * @param text The text that couldn't be cast.
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
     * @param text The text that couldn't be cast.
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


    /**
     * Tell the user that the parser encountered an unexpected {@link SyntaxToken} during parsing.
     *
     * @param currentToken The unexpected token.
     * @param expectedKind The token that was expected.
     */
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

    /**
     * Tell the user that they missed a closing parenthesis.
     *
     * @param length The span where the parentheses are missing.
     */
    public void addMissingClosingParentheses(TextSpan length)
    {
        _diagnostics.add(
            new Diagnostic(
                length,
                String.format("Missing closing parentheses beginning at position %d.", length.getStart()),
                DiagnosticLevel.CRIT
            )
        );
    }

    /**
     * Tell the user they tried to call a function that wasn't registered.
     *
     * @param currentToken The token representing the function.
     */
    public void addUnregisteredFunction(SyntaxToken currentToken)
    {
        _diagnostics.add(
            new Diagnostic(
                currentToken.getTextSpan(),
                String.format("Call to unregistered function <%s> during parsing at position %d.", currentToken.getValue(), currentToken.getPosition()),
                DiagnosticLevel.ERROR
            )
        );
    }

    /**
     * Adds an exception that was thrown during the Lexing, Parsing, or Binding process to the list of diagnostics.
     *
     * @param e The exception.
     */
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

    /**
     * Tell the user that an unknown expression was encountered during Binding.  This was probably because the binder
     * doesn't have feature parity with the parser.
     *
     * @param syntax The unknown expression.
     */
    public void addUnknownExpression(ExpressionSyntaxNode syntax)
    {
        _diagnostics.add(
            new Diagnostic(
                new TextSpan(0, 0),
                String.format("Attempt to bind an unknown expression <%s>.", syntax.getKind()),
                DiagnosticLevel.CRIT
            )
        );
    }

    /**
     * Tell the user that the unary operation they are attempting to perform isn't supported.
     *
     * @param syntax The unary operation {@link SyntaxNode} that failed.
     * @param boundOperand The operand that failed the type check.
     */
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

    /**
     * Tell the user that the binary operation they attempted to perform is unsupported.
     *
     * @param syntax The binary operation {@link SyntaxNode} that failed.
     * @param boundLeft The left operand.
     * @param boundRight The right operand.
     */
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

    /**
     * Tell the user that the number of arguments they provided does not match the required number of arguments for the
     * function call.
     *
     * @param syntax The offending function's {@link SyntaxToken}
     * @param providedArguments The number of arguments provided.
     * @param function The {@link IFunction} object that was supposed to be called.
     */
    public void addInvalidArgumentLength(SyntaxToken syntax, int providedArguments, IFunction function)
    {
        _diagnostics.add(
            new Diagnostic(
                syntax.getTextSpan(),
                String.format(
                    "Invalid number of arguments provided for function %s. The function accepts %d arguments, but %d were provided.",
                    function.getName(),
                    function.getArguments().size(),
                    providedArguments
                ),
                DiagnosticLevel.ERROR
            )
        );
    }

    /**
     * Tell the user that the argument they tried to pass to the function is out of range of the expected argument count.
     *
     * @param syntax The offending argument's {@link SyntaxToken}
     * @param index The index of the argument
     * @param function The {@link IFunction} object that was supposed to be called.
     */
    public void addInvalidArgumentAtIndex(SyntaxToken syntax, int index, IFunction function)
    {
        _diagnostics.add(
            new Diagnostic(
                syntax.getTextSpan(),
                String.format(
                    "Invalid number of arguments provided for function %s at index %d. The function only accepts %d arguments.",
                    function.getName(),
                    index,
                    function.getArguments().size()
                ),
                DiagnosticLevel.ERROR
            )
        );
    }

    /**
     * Tell the user that the argument at a specific index is not compatible with the type(s) specified by the {@link IFunction}
     * object's {@link com.jahndigital.expressive.extensibility.ArgumentDefinition} documentation.
     *
     * @param syntax The offending argument's {@link SyntaxToken}
     * @param index The index of the argument
     * @param function The {@link IFunction} object that was supposed to be called.
     * @param receivedType The resolved type of the argument the user tried to pass in.
     */
    public void addInvalidArgumentType(SyntaxToken syntax, int index, IFunction function, Type receivedType)
    {
        if (function.getArguments().size() < index) {
            addInvalidArgumentAtIndex(syntax, index, function);
            return;
        }

        _diagnostics.add(
            new Diagnostic(
                syntax.getTextSpan(),
                String.format(
                    "Invalid argument at index %d for function %s.  Type provided was %s, but expected one of %s",
                    index,
                    function.getName(),
                    receivedType,
                    function.getArguments().get(index).getTypes().stream().map(Type::getTypeName).collect(Collectors.joining(", ", "[", "]"))
                ),
                DiagnosticLevel.ERROR
            )
        );
    }
}
