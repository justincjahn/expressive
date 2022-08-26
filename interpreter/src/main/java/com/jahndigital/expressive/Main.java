package com.jahndigital.expressive;

import com.jahndigital.expressive.binding.BoundSyntaxTree;
import com.jahndigital.expressive.syntax.SyntaxTree;

import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
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

            // Print parsing diagnostics if available
            if (tree.getDiagnostics().iterator().hasNext()) {
                tree.getDiagnostics().forEach(System.out::println);
            } else {
                BoundSyntaxTree boundTree;

                try {
                    boundTree = tree.bind();

                    // Print binding diagnostics if available
                    if (boundTree.getDiagnostics().iterator().hasNext()) {
                        boundTree.getDiagnostics().forEach(System.out::println);
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                // Evaluate
                try {
                    Object result = boundTree.evaluate();
                    System.out.println(result);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
