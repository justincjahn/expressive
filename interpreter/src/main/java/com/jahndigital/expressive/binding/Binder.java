package com.jahndigital.expressive.binding;

import com.jahndigital.expressive.DiagnosticRepository;
import com.jahndigital.expressive.extensibility.ArgumentDefinition;
import com.jahndigital.expressive.syntax.*;

import java.util.ArrayList;
import java.util.List;

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
     */
    private BoundExpression bindExpression(ExpressionSyntaxNode syntax)
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
            case FunctionExpression:
                return bindFunctionExpression((FunctionExpressionSyntaxNode)syntax);
            default:
                _diagnostics.addUnknownExpression(syntax);
                return new BoundUnknownExpression();
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
     * Binds a function that's called during evaluation.
     *
     * @param syntax The {@link SyntaxNode} to bind.
     */
    private BoundExpression bindFunctionExpression(FunctionExpressionSyntaxNode syntax)
    {
        List<ExpressionSyntaxNode> expressionSyntaxArguments = syntax.getArguments();
        List<ArgumentDefinition> functionArguments = syntax.getFunction().getArguments();

        if (expressionSyntaxArguments.size() != functionArguments.size()) {
            _diagnostics.addInvalidArgumentLength(syntax.getFunctionName(), expressionSyntaxArguments.size(), syntax.getFunction());
        }

        ArrayList<BoundExpression> arguments = new ArrayList<>();
        for (int i = 0; i < expressionSyntaxArguments.size(); i++) {
            BoundExpression arg = bindExpression(expressionSyntaxArguments.get(i));

            if (functionArguments.size() > i && !functionArguments.get(i).getTypes().contains(arg.getType())) {
                _diagnostics.addInvalidArgumentType(syntax.getFunctionName(), i, syntax.getFunction(), arg.getType());
            }

            arguments.add(arg);
        }

        return new BoundFunctionExpression(syntax.getFunction(), arguments);
    }

    /**
     * Binds a unary operation (E.g., -1)
     *
     * @param syntax The {@link SyntaxNode} to bind.
     */
    private BoundExpression bindUnaryExpression(UnaryExpressionSyntaxNode syntax)
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
     */
    private BoundExpression bindBinaryExpression(BinaryExpressionSyntaxNode syntax)
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
