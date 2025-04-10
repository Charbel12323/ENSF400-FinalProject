package com.coveros.training.cartesianproduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Utility class to calculate the Cartesian product of a list of sets.
 * <p>
 * Each combination is represented as a list of strings. The final output is a
 * comma-separated list of combinations, where each combination is enclosed in parentheses.
 * </p>
 */
public class CartesianProduct {

    /**
     * Calculates the Cartesian product of the given list of sets.
     *
     * @param sets a list of sets whose Cartesian product is to be computed
     * @return a String representation of the Cartesian product
     */
    public static String calculate(List<Set<String>> sets) {
        if (sets == null || sets.isEmpty()) {
            return "";
        }
        // Start with an empty combination
        List<List<String>> product = new ArrayList<>();
        product.add(new ArrayList<>());

        // For each set, add each element to every partial combination so far.
        for (Set<String> set : sets) {
            List<List<String>> temp = new ArrayList<>();
            for (List<String> partial : product) {
                for (String element : set) {
                    List<String> newPartial = new ArrayList<>(partial);
                    newPartial.add(element);
                    temp.add(newPartial);
                }
            }
            product = temp;
        }

        // Format the product as a comma-separated list of combinations
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < product.size(); i++) {
            List<String> combination = product.get(i);
            sb.append("(");
            for (int j = 0; j < combination.size(); j++) {
                sb.append(combination.get(j));
                if (j < combination.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")");
            if (i < product.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
