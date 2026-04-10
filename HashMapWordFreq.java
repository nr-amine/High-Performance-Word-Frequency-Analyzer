/* Amine NOUAR : Je déclare qu'il s'agit de mon propre travail et que ce travail
a été entièrement écrit par un être humain. */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class HashMapWordFreq {
    /*the modifications turned out to not be too drastic compared to the other files
      (provided we're using a java-native class) */
    private static HashMap<String, Integer> createOccurencesMap(BufferedReader reader) throws IOException {
        HashMap<String, Integer> freqmap = new HashMap<>();
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
            System.err.println("Usage: java HashMapWordFreq <file> [k]");
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
            FileReader file = new FileReader(name);
            BufferedReader br = new BufferedReader(file);
            HashMap<String, Integer> wordFreq = createOccurencesMap(br);
            br.close();
            file.close();

            for (int i = 0; i < k; i++) {
                if (wordFreq.isEmpty()) {
                    break;
                }
                String max_Key = null;
                Integer max_Val = -1;

                /*adapted directly from 
                https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap/1066607#1066607*/
                for (String key : wordFreq.keySet()) {
                    Integer val = wordFreq.get(key);
                    if (val != null && val > max_Val) {
                        max_Val = val;
                        max_Key = key;
                    }
                }

                if (max_Key != null) {
                    System.out.println(max_Key + " : " + max_Val);
                    wordFreq.remove(max_Key);
                }
            }

        } catch (IOException e) {
            System.err.println("file reading error");
        } catch (Exception e) {
             System.err.println("unexpected error");
        }
    }
}
