package com.jahndigital.expressive;

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
                Evaluator evaluator = new Evaluator(tree.getRoot());

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


