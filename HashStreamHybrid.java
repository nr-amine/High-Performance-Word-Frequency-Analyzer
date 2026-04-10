import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class HashStreamHybrid {

    private static HashMap<String, Integer> createOccurencesMap(BufferedReader reader) throws IOException {
        HashMap<String, Integer> freqmap = new HashMap<>();
        String line;
        
        /*Used a classic C-like approach to avoid replaceAll and split*/
        StringBuilder bui = new StringBuilder(); 

        while ((line = reader.readLine()) != null){
            for (int i = 0; i < line.length(); i++) {
                Character c = line.charAt(i);

                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    bui.append(Character.toLowerCase(c));
            } else {
                    if (bui.length() > 0){
                        String word = bui.toString();
                        bui.setLength(0);
                        Integer count = freqmap.get(word);
                        if (count == null) {
                            freqmap.put(word, 1);
                        } else {
                            freqmap.put(word, count + 1);
                        }
                    }
                }
            }
            if (bui.length() > 0) {
                String word = bui.toString();
                bui.setLength(0);
                
                Integer count = freqmap.get(word);
                if (count == null) {
                    freqmap.put(word, 1);
                } else {
                    freqmap.put(word, count + 1);
                }
            }
        }
        return freqmap;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java HashStreamHybrid <file> [k]");
            return;
        }

        String name = args[0];
        int k = 10;
        if (args.length >= 2) {
            try {
                k = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("k must be an integer");
            }
        }
        try {
            FileReader file = new FileReader(name);
            BufferedReader br = new BufferedReader(file);
            HashMap<String, Integer> wordFreq = createOccurencesMap(br);
            br.close();
            file.close();

            /*Useful sources: https://bit.ly/49qiHXq
                              https://bit.ly/4jdfNZt */
            wordFreq.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(k)
                .forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));
                /*better than shell command!!!*/

        } catch (IOException e) {   
            System.err.println("file reading error");
        } catch (Exception e) {
             System.err.println("unexpected error");
        }
    }
}