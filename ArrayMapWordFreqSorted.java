/* Amine NOUAR : Je déclare qu'il s'agit de mon propre travail et que ce travail
a été entièrement écrit par un être humain. */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ArrayMapWordFreqSorted {
    
    private static SortedArrayMap<String, Integer> createOccurencesMap(BufferedReader reader) throws IOException {
        SortedArrayMap<String, Integer> freqmap = new SortedArrayMap<>();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.toLowerCase();
            line = line.replaceAll("[^a-z]", " ");
            String[] words = line.split(" ");
            for (String word : words) {
                if (!word.isBlank()) {
                    Integer count = freqmap.get(word);
                    if (count == null) {
                        freqmap.put(word, 1);
                    } else {
                        freqmap.put(word, count + 1);
                    }
                }
            }
        }
        return freqmap;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java ArrayMapWordFreq <file> [k]");
            return;
        }

        String name = args[0];
        int k = 10;
        if (args.length >= 2) {
            try {
                k = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("k must be an integer.");
                return;
            }
        }
        try {
            FileReader f = new FileReader(name);
            BufferedReader br = new BufferedReader(f);
            SortedArrayMap<String, Integer> wordFreq = createOccurencesMap(br);
            br.close();
            f.close();
            Arrays.sort(wordFreq.values, 0, wordFreq.size(), (a, b) -> b.value().compareTo(a.value()));
            for (int i = 0; i < k; i++) {
                if (k >= wordFreq.size()) {
                    break;
                }
                System.out.println(wordFreq.values[i].key() + ": " + wordFreq.values[i].value());
            }

        } catch (IOException e) {
            System.err.println("file reading error");
        } catch (Exception e) {
             System.err.println("unexpected error");
        }
    }
}
