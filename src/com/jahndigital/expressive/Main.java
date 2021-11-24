package com.jahndigital.expressive;

import com.jahndigital.expressive.binding.Binder;
import com.jahndigital.expressive.binding.BoundExpression;
import com.jahndigital.expressive.syntax.ExpressionSyntaxNode;
import com.jahndigital.expressive.syntax.SyntaxNode;
import com.jahndigital.expressive.syntax.SyntaxTree;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        boolean showTree = false;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">");
            String line = scanner.nextLine();

            if (line.equals("exit") || line.isEmpty()) {
                return;
            }

            if (line.equals("#tree")) {
                showTree = !showTree;
                System.out.println("Toggled tree.");
                continue;
            }

            SyntaxTree tree = SyntaxTree.parse(line);

            if (showTree) {
                SyntaxNode.pprint(tree.getRoot());
            }

            if (tree.getErrors().iterator().hasNext()) {
                tree.getErrors().forEach(System.out::println);
            } else {
                Binder binder = new Binder();
                BoundExpression boundExpression = null;

                try {
                    boundExpression = binder.bindExpression(tree.getRoot());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (binder.getErrors().iterator().hasNext()) {
                    tree.getErrors().forEach(System.out::println);
                    continue;
                }

                Evaluator evaluator = new Evaluator(boundExpression);

                try {
                    int result = evaluator.evaluate();
                    System.out.println(result);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}


