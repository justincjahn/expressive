package com.jahndigital.expressive.binding;

import com.jahndigital.expressive.syntax.*;

import java.util.ArrayList;

/**
 * Walks a {@link ExpressionSyntaxNode} and generates a typed tree used to evaluate expressions.
 */
public final class Binder
{
    /**
     * A list of error messages that occurred when parsing the {@link ExpressionSyntaxNode}(s).
     */
    private final ArrayList<String> _diagnostics = new ArrayList<>();

    /**
     * Gets a list of errors that occurred during binding.
     */
    public Iterable<String> getErrors() {
        return _diagnostics;
    }

    /**
     * Generate a typed tree and return it.
     *
     * @param syntax The syntax node(s) to walk.
     * @throws Exception If a fatal error occurred when walking or type checking.
     */
    public BoundExpression bindExpression(ExpressionSyntaxNode syntax) throws Exception {
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
    private BoundExpression bindLiteralExpression(LiteralExpressionSyntaxNode syntax) {
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
    private BoundExpression bindUnaryExpression(UnaryExpressionSyntaxNode syntax) throws Exception {
        BoundExpression boundOperand = bindExpression(syntax.getOperand());
        BoundUnaryOperator boundOperator = BoundUnaryOperator.bind(syntax.getOperator().getKind(), boundOperand.getType());

        if (boundOperator == null) {
            _diagnostics.add(String.format("ERROR: Unary Operator '%s' is not defined for type %s.", syntax.getOperator().getText(), boundOperand.getType()));
            return boundOperand;
        }

        return new BoundUnaryExpression(boundOperator, boundOperand);
    }

    /**
     * Binds a binary operaion (E.g, 1 + 1, true AND false)
     *
     * @param syntax The {@link SyntaxNode} to bind.
     * @throws Exception If the binary operation was called on one or more incompatible types.
     */
    private BoundExpression bindBinaryExpression(BinaryExpressionSyntaxNode syntax) throws Exception {
        BoundExpression boundLeft = bindExpression(syntax.getLeft());
        BoundExpression boundRight = bindExpression(syntax.getRight());
        BoundBinaryOperator boundOperator = BoundBinaryOperator.bind(syntax.getOperator().getKind(), boundLeft.getType(), boundRight.getType());

        if (boundOperator == null) {
            _diagnostics.add(String.format("ERROR: Binary Operator '%s' is not defined for types %s and %s.", syntax.getOperator().getText(), boundLeft.getType(), boundRight.getType()));
            return boundLeft;
        }

        return new BoundBinaryExpression(boundLeft, boundOperator, boundRight);
    }
}
