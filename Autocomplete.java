import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

// A data type that provides autocomplete functionality for a given set of
// string and weights, using Term and BinarySearchDeluxe.
public class Autocomplete {
    private Term[] terms;

    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new NullPointerException();
        }
        this.terms = new Term[terms.length];
        this.terms = terms;
        Arrays.sort(this.terms);    // sort makes the searching easier
    }

    // All terms that start with the given prefix, in descending order of
    // weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new NullPointerException();
        }

        //Search for the first index by the order of the prefix
        int first_index = BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix), Term.byPrefixOrder(prefix.length()));
        if (first_index == -1) {
            return null;    //if cannot find the first_index its not possible to find the rest.
        }

        //Search for the last index by the order of the prefix
        int last_index = BinarySearchDeluxe.lastIndexOf(terms, new Term(prefix), Term.byPrefixOrder(prefix.length()));
        Term[] matches = new Term[last_index - first_index + 1];
        int i = 0;
        while (i < matches.length) {
            matches[i] = this.terms[first_index];
            first_index++;
            i++;
        }
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;

    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new NullPointerException();
        }
        int first_index = BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix), Term.byPrefixOrder(prefix.length()));
        int last_index = BinarySearchDeluxe.lastIndexOf(terms, new Term(prefix), Term.byPrefixOrder(prefix.length()));
        return last_index - first_index + 1;
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }
    }
}
