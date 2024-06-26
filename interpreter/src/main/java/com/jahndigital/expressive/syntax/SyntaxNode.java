package com.jahndigital.expressive.syntax;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a generic element of a syntax tree.
 */
public abstract class SyntaxNode
{
    public abstract SyntaxKind getKind();

    public abstract Iterable<SyntaxNode> getChildren();

    /**
     * Pretty prints a SyntaxNode
     *
     * @param node The SyntaxNode to print.
     */
    public static void pprint(SyntaxNode node)
    {
        pprint(node, "", true);
    }

    /**
     * Recursive method to pretty print.
     *
     * @param node The SyntaxNode to print.
     * @param indent The string to use for indentation.
     * @param isLast If the node is the last in the tree.
     */
    private static void pprint(SyntaxNode node, String indent, boolean isLast)
    {
        String marker = isLast ? "└──" : "├──";

        System.out.print(indent);
        System.out.print(marker);
        System.out.print(node.getKind());

        if (node instanceof SyntaxToken && ((SyntaxToken)node).getValue() != null) {
            System.out.print(" ");
            System.out.print(((SyntaxToken) node).getValue());
        }

        System.out.println();

        indent += isLast ? "    " : "│   ";

        SyntaxNode last = null;
        Iterator<SyntaxNode> itr = node.getChildren().iterator();

        try {
            last = itr.next();
        } catch (NoSuchElementException e) {
            // Ignore
        }

        while (itr.hasNext()) {
            last = itr.next();
        }

        for (SyntaxNode child : node.getChildren())
        {
            pprint(child, indent, child == last);
        }
    }
}
