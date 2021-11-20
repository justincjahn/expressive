package com.jahndigital.expressive;

/**
 * Walks the AST and evaluates the expression into an integer.
 */
public class Evaluator {
    private final ExpressionSyntax _root;

    public Evaluator(ExpressionSyntax root) {
        this._root = root;
    }

    public int evaluate() throws Exception {
        return evaluateExpression(_root);
    }

    private int evaluateExpression(ExpressionSyntax root) throws Exception {
        if (root instanceof NumberExpressionSyntax) {
            return (int) ((NumberExpressionSyntax) root).getToken().getValue();
        }

        if (root instanceof BinaryExpressionSyntax) {
            BinaryExpressionSyntax b = (BinaryExpressionSyntax) root;
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
