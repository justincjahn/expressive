package com.jahndigital.expressive.binding;

import com.jahndigital.expressive.DiagnosticRepository;
import com.jahndigital.expressive.syntax.*;

/**
 * Walks a {@link ExpressionSyntaxNode} and generates a type-safe tree of {@link BoundExpression} objects that can be evaluated.
 */
final class Binder
{
    /**
     * A list of error messages that occurred when parsing the {@link ExpressionSyntaxNode}(s).
     */
    private final DiagnosticRepository _diagnostics;

    /**
     * Init
     *
     * @param diagnostics The repository to use when reporting issues with binding.
     */
    Binder(DiagnosticRepository diagnostics)
    {
        _diagnostics = diagnostics;
    }

    /**
     * Bind to the provided SyntaxTree and return a {@link BoundSyntaxTree}.
     */
    BoundSyntaxTree bind(SyntaxTree tree)
    {
        try {
            BoundExpression bound = bindExpression(tree.getRoot());
            return new BoundSyntaxTree(_diagnostics, bound);
        } catch (Exception e) {
            _diagnostics.addException(e);
            return new BoundSyntaxTree(_diagnostics, null);
        }
    }

    /**
     * Generate a typed tree and return it.
     *
     * @param syntax The syntax node(s) to walk.
     * @throws Exception If a fatal error occurred when walking or type checking.
     */
    private BoundExpression bindExpression(ExpressionSyntaxNode syntax) throws Exception
    {
        switch (syntax.getKind()) {
            case LiteralExpression:
                return bindLiteralExpression((LiteralExpressionSyntaxNode)syntax);
            case UnaryExpression:
                return bindUnaryExpression((UnaryExpressionSyntaxNode)syntax);
            case BinaryExpression:
                return bindBinaryExpression((BinaryExpressionSyntaxNode)syntax);
            case ParenthesisedExpression:
                return bindExpression(((ParenthesisedExpressionSyntax)syntax).getExpression());
            default:
                throw new Exception(String.format("Unexpected syntax %s", syntax.getKind()));
        }
    }

    /**
     * Binds a token literal (E.g., true, 1, "hello")
     *
     * @param syntax The {@link SyntaxNode} to bind
     */
    private BoundExpression bindLiteralExpression(LiteralExpressionSyntaxNode syntax)
    {
        Object value = 0;

        Object tokenValue = syntax.getValue();
        if (tokenValue != null) {
            value = tokenValue;
        }

        return new BoundLiteralExpression(value);
    }

    /**
     * Binds a unary operation (E.g., -1)
     *
     * @param syntax The {@link SyntaxNode} to bind.
     * @throws Exception If the unary operation was called on an incompatible type.
     */
    private BoundExpression bindUnaryExpression(UnaryExpressionSyntaxNode syntax) throws Exception
    {
        BoundExpression boundOperand = bindExpression(syntax.getOperand());
        BoundUnaryOperation boundOperator = BoundUnaryOperation.bind(syntax.getOperator().getKind(), boundOperand.getType());

        if (boundOperator == null) {
            _diagnostics.addInvalidUnaryOperator(syntax, boundOperand);
            return boundOperand;
        }

        return new BoundUnaryExpression(boundOperator, boundOperand);
    }

    /**
     * Binds a binary operation (E.g, 1 + 1, true AND false)
     *
     * @param syntax The {@link SyntaxNode} to bind.
     * @throws Exception If the binary operation was called on one or more incompatible types.
     */
    private BoundExpression bindBinaryExpression(BinaryExpressionSyntaxNode syntax) throws Exception
    {
        BoundExpression boundLeft = bindExpression(syntax.getLeft());
        BoundExpression boundRight = bindExpression(syntax.getRight());
        BoundBinaryOperation boundOperator = BoundBinaryOperation.bind(syntax.getOperator().getKind(), boundLeft.getType(), boundRight.getType());

        if (boundOperator == null) {
            _diagnostics.addInvalidBinaryOperation(syntax, boundLeft, boundRight);
            return boundLeft;
        }

        return new BoundBinaryExpression(boundLeft, boundOperator, boundRight);
    }
}
