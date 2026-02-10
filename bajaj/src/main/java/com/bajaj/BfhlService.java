package com.bajaj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BfhlService {
    
    @Value("${gemini.api.key}")
    private String geminiApiKey;
    
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    public List<Long> generateFibonacci(int n) {
        if (n <= 0) throw new IllegalArgumentException("Input must be positive");
        List<Long> result = new ArrayList<>();
        long a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            result.add(a);
            long temp = a + b;
            a = b;
            b = temp;
        }
        return result;
    }

    public List<Integer> filterPrimes(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) throw new IllegalArgumentException("Array cannot be empty");
        return numbers.stream().filter(this::isPrime).collect(Collectors.toList());
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public long calculateLCM(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) throw new IllegalArgumentException("Array cannot be empty");
        return numbers.stream().mapToLong(Integer::longValue).reduce(this::lcm).orElseThrow();
    }

    private long lcm(long a, long b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    public long calculateHCF(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) throw new IllegalArgumentException("Array cannot be empty");
        return numbers.stream().mapToLong(Integer::longValue).reduce(this::gcd).orElseThrow();
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

    public String getAIResponse(String question) {
        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("Question cannot be empty");
        }
        
        if (geminiApiKey == null || geminiApiKey.isEmpty()) {
            return "AI service not configured";
        }

        try {
            URL url = new URL(geminiApiUrl + "?key=" + geminiApiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = String.format(
                "{\"contents\":[{\"parts\":[{\"text\":\"Answer in one word only: %s\"}]}]}",
                question.replace("\"", "\\\"")
            );

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
                
                String responseStr = response.toString();
                int textStart = responseStr.indexOf("\"text\":") + 8;
                int textEnd = responseStr.indexOf("\"", textStart);
                if (textStart > 7 && textEnd > textStart) {
                    return responseStr.substring(textStart, textEnd).trim();
                }
            }
            return "Unable to process";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
