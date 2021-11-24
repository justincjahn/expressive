package com.jahndigital.expressive.binding;

import com.jahndigital.expressive.syntax.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public final class Binder
{
    private final ArrayList<String> _diagnostics = new ArrayList<>();

    public Iterable<String> getErrors() {
        return _diagnostics;
    }

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

    private BoundExpression bindLiteralExpression(LiteralExpressionSyntaxNode syntax) {
        int value = 0;
        Object tokenValue = syntax.getToken().getValue();

        if (tokenValue != null && tokenValue instanceof Integer) {
            value = (int)tokenValue;
        }

        return new BoundLiteralExpression(value);
    }

    private BoundExpression bindUnaryExpression(UnaryExpressionSyntaxNode syntax) throws Exception {
        BoundExpression boundOperand = bindExpression(syntax.getOperand());
        BoundUnaryOperatorKind boundOperator = bindUnaryOperatorKind(syntax.getOperator(), boundOperand.getType());

        if (boundOperator == null) {
            _diagnostics.add(String.format("ERROR: Unary Operator %s is not defined for type %s.", syntax.getOperator(), boundOperand.getType()));
            return boundOperand;
        }

        return new BoundUnaryExpression(boundOperator, boundOperand);
    }

    private BoundUnaryOperatorKind bindUnaryOperatorKind(SyntaxToken operator, Type operandType) throws Exception {
        if (operandType != Integer.class) {
            return null;
        }

        switch (operator.getKind()) {
            case PlusToken:
                return BoundUnaryOperatorKind.Identity;
            case MinusToken:
                return BoundUnaryOperatorKind.Negation;
            default:
                throw new Exception(String.format("Unexpected unary operator kind %s", operator.getKind()));
        }
    }

    private BoundExpression bindBinaryExpression(BinaryExpressionSyntaxNode syntax) throws Exception {
        BoundExpression boundLeft = bindExpression(syntax.getLeft());
        BoundExpression boundRight = bindExpression(syntax.getRight());
        BoundBinaryOperatorKind boundOperator = bindBinaryOperatorKind(syntax.getOperator(), boundLeft.getType(), boundRight.getType());

        if (boundOperator == null) {
            _diagnostics.add(String.format("ERROR: Unary Operator %s is not defined for types %s and %s.", syntax.getOperator(), boundLeft.getType(), boundRight.getType()));
            return boundLeft;
        }

        return new BoundBinaryExpression(boundLeft, boundOperator, boundRight);
    }

    private BoundBinaryOperatorKind bindBinaryOperatorKind(SyntaxToken operator, Type leftType, Type rightType) throws Exception {
        if (leftType != Integer.class || rightType != Integer.class) {
            return null;
        }

        switch (operator.getKind()){
            case PlusToken:
                return BoundBinaryOperatorKind.Addition;
            case MinusToken:
                return BoundBinaryOperatorKind.Subtraction;
            case StarToken:
                return BoundBinaryOperatorKind.Multiplication;
            case SlashToken:
                return BoundBinaryOperatorKind.Division;
            default:
                throw new Exception(String.format("Unexpected binary operator kind %s", operator.getKind()));
        }
    }
}