package com.bajaj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class BfhlController {
    
    private static final String OFFICIAL_EMAIL = "your.email@chitkara.edu.in";
    
    @Autowired
    private BfhlService service;

    @PostMapping("/bfhl")
    public ResponseEntity<ApiResponse> handleBfhl(@RequestBody Map<String, Object> request) {
        try {
            if (request == null || request.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, OFFICIAL_EMAIL, null, "Empty request"));
            }

            if (request.size() != 1) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, OFFICIAL_EMAIL, null, "Request must contain exactly one key"));
            }

            String key = request.keySet().iterator().next();
            Object value = request.get(key);

            Object result;
            switch (key) {
                case "fibonacci":
                    if (!(value instanceof Number)) {
                        return ResponseEntity.badRequest()
                            .body(new ApiResponse(false, OFFICIAL_EMAIL, null, "fibonacci requires integer"));
                    }
                    result = service.generateFibonacci(((Number) value).intValue());
                    break;

                case "prime":
                    if (!(value instanceof List)) {
                        return ResponseEntity.badRequest()
                            .body(new ApiResponse(false, OFFICIAL_EMAIL, null, "prime requires array"));
                    }
                    List<Integer> primeInput = ((List<?>) value).stream()
                        .map(o -> ((Number) o).intValue())
                        .toList();
                    result = service.filterPrimes(primeInput);
                    break;

                case "lcm":
                    if (!(value instanceof List)) {
                        return ResponseEntity.badRequest()
                            .body(new ApiResponse(false, OFFICIAL_EMAIL, null, "lcm requires array"));
                    }
                    List<Integer> lcmInput = ((List<?>) value).stream()
                        .map(o -> ((Number) o).intValue())
                        .toList();
                    result = service.calculateLCM(lcmInput);
                    break;

                case "hcf":
                    if (!(value instanceof List)) {
                        return ResponseEntity.badRequest()
                            .body(new ApiResponse(false, OFFICIAL_EMAIL, null, "hcf requires array"));
                    }
                    List<Integer> hcfInput = ((List<?>) value).stream()
                        .map(o -> ((Number) o).intValue())
                        .toList();
                    result = service.calculateHCF(hcfInput);
                    break;

                case "AI":
                    if (!(value instanceof String)) {
                        return ResponseEntity.badRequest()
                            .body(new ApiResponse(false, OFFICIAL_EMAIL, null, "AI requires string"));
                    }
                    result = service.getAIResponse((String) value);
                    break;

                default:
                    return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, OFFICIAL_EMAIL, null, "Invalid key: " + key));
            }

            return ResponseEntity.ok(new ApiResponse(true, OFFICIAL_EMAIL, result));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, OFFICIAL_EMAIL, null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, OFFICIAL_EMAIL, null, "Internal error"));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse> health() {
        return ResponseEntity.ok(new ApiResponse(true, OFFICIAL_EMAIL, null));
    }
}
