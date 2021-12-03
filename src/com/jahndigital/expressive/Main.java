package com.jahndigital.expressive;

import com.jahndigital.expressive.binding.BoundSyntaxTree;
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
                tree.pprint();
            }

            BoundSyntaxTree boundTree;
            if (tree.getDiagnostics().iterator().hasNext()) {
                tree.getDiagnostics().forEach(System.out::println);
            } else {
                try {
                    boundTree = tree.bind();

                    if (boundTree.getDiagnostics().iterator().hasNext()) {
                        boundTree.getDiagnostics().forEach(System.out::println);
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                Evaluator evaluator = new Evaluator(boundTree.getRoot());
                try {
                    Object result = evaluator.evaluate();
                    System.out.println(result);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
