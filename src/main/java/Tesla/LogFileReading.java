package Tesla;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author lzn
 * @date 2023/04/17 21:26
 * @description
 */
public class LogFileReading {

    private static final Map<String, Integer> TAG_MAP = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(LogFileReading.class);

    private final int startIndexForHashTag;

    private final String outputFilePrefix = "output-file";

    private final String localPath = "D:\\logs\\file_output\\";

    public static void main(String[] args) {
        LogFileReading logFileReading = new LogFileReading();
        logFileReading.readFile("D:\\logs\\file_input\\file.log");
    }

    static {
        TAG_MAP.put("one", 1);
        TAG_MAP.put("two", 2);
        TAG_MAP.put("three", 3);
        TAG_MAP.put("four", 4);
        TAG_MAP.put("five", 5);
        TAG_MAP.put("six", 6);
        TAG_MAP.put("seven", 7);
        TAG_MAP.put("eight", 8);
        TAG_MAP.put("nine", 9);
        TAG_MAP.put("ten", 10);
    }

    public LogFileReading() {
        //timestamp + partition + uuid = 16 + 36 + 1 = 53
        startIndexForHashTag = 53;
    }

    public void readFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            // Use LinkedHashMap to ensure the output order is same as the input order
            Map<String, List<String>> valueMap = new LinkedHashMap<>();
            line:
            while (line != null) {
                boolean isValid = isLineValid(line);
                if (!isValid) {
                    log.info("The line values did not match the rules, continue");
                    line = reader.readLine();
                    continue;
                }
                String hashtag = line.substring(startIndexForHashTag);
                if (hashtag.length() == 0) {
                    log.info("The hashtag is empty, continue");
                    line = reader.readLine();
                    continue;
                }
                hashtag = hashtag.replaceAll("#", "");
                String[] hashtags = hashtag.split(",");
                int sum = 0;
                for (String tag : hashtags) {
                    if (!TAG_MAP.containsKey(tag)) {
                        log.info("The hashtag {} is invalid, continue", tag);
                        line = reader.readLine();
                        continue line;
                    }
                    sum += TAG_MAP.get(tag);
                }
                String[] threeRules = line.substring(0, startIndexForHashTag).split(",");
                String timestamp = threeRules[0];
                String partition = threeRules[1];
                String uuid = threeRules[2];
                valueMap.putIfAbsent(partition, new ArrayList<>());
                valueMap.get(partition).add(timestamp + "," + uuid + "," + sum);
                line = reader.readLine();
            }
            outputToFile(valueMap);
            log.info("The file has been read");
        } catch (IOException e) {
            log.error("File read error: " + e.getMessage());
        }
    }

    private void outputToFile(Map<String, List<String>> valueMap) {
        log.info("valueMap: {}", valueMap);
        FileOutputStream outputStream = null;
        try {
            for (Map.Entry<String, List<String>> entry : valueMap.entrySet()) {
                String partition = entry.getKey();
                List<String> values = entry.getValue();
                String fileName = localPath + outputFilePrefix + "-" + partition + ".csv";
                outputStream = new FileOutputStream(fileName);
                for (String content : values) {
                    outputStream.write(content.getBytes());
                    String newLine = System.getProperty("line.separator");
                    outputStream.write(newLine.getBytes());
                }

                log.info("Content size for {} is {}", fileName, values.size());
                outputStream.flush();
                outputStream.close();
                log.info("The file {} has been modified", fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isLineValid(String line) {
        if (line == null || line.length() == 0 || line.length() < startIndexForHashTag) {
            return false;
        }
        String[] values = line.split(",");
        if (values.length < 3) {
            return false;
        }

        // Verify the timestamp
        try {
            long timestamp = Long.parseLong(values[0]);
            if (timestamp <= 0 || timestamp > System.currentTimeMillis()) {
                return false;
            }
        } catch (NumberFormatException e) {
            log.error("The timestamp is invalid: {}", e.getMessage());
            return false;
        }

        // Verify the partition
        try {
            int partition = Integer.parseInt(values[1]);
            if (partition < 1 || partition > 4) {
                return false;
            }
        } catch (Exception e) {
            log.error("The partition is invalid: {}", e.getMessage());
            return false;
        }

        // Verify the uuid
        try {
            UUID.fromString(values[2]);
        } catch (Exception e) {
            log.error("The uuid is invalid: {}", e.getMessage());
            return false;
        }

        return true;
    }
}
