package com.jahndigital.expressive;

/**
 * Walks the AST and evaluates the expression into an integer.
 */
final class Evaluator {
    private final ExpressionSyntaxNode _root;

    public Evaluator(ExpressionSyntaxNode root) {
        this._root = root;
    }

    public int evaluate() throws Exception {
        return evaluateExpression(_root);
    }

    private int evaluateExpression(ExpressionSyntaxNode root) throws Exception {
        if (root instanceof LiteralExpressionSyntaxNode) {
            return (int) ((LiteralExpressionSyntaxNode) root).getToken().getValue();
        }

        if (root instanceof UnaryExpressionSyntaxNode) {
            UnaryExpressionSyntaxNode u = (UnaryExpressionSyntaxNode) root;
            int operand = evaluateExpression(u.getOperand());
            SyntaxKind kind = u.getOperator().getKind();

            if (kind == SyntaxKind.PlusToken) {
                return operand;
            }

            if (kind == SyntaxKind.MinusToken) {
                return -operand;
            }

            throw new Exception(String.format("Unexpected unary operator %s", kind));
        }

        if (root instanceof BinaryExpressionSyntaxNode) {
            BinaryExpressionSyntaxNode b = (BinaryExpressionSyntaxNode) root;
            int left = evaluateExpression(b.getLeft());
            int right = evaluateExpression(b.getRight());

            SyntaxKind operation = b.getOperator().getKind();
            if (operation == SyntaxKind.PlusToken) {
                return left + right;
            } else if (operation == SyntaxKind.MinusToken) {
                return left - right;
            } else if (operation == SyntaxKind.StarToken) {
                return left * right;
            } else if (operation == SyntaxKind.SlashToken) {
                return left / right;
            }

            throw new Exception(String.format("Unexpected binary operator %s", operation));
        }

        if (root instanceof ParenthesisedExpressionSyntax) {
            ParenthesisedExpressionSyntax p = (ParenthesisedExpressionSyntax) root;
            return evaluateExpression(p.getExpression());
        }

        return 0;
    }
}
