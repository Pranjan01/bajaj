# BFHL REST API

Production-ready REST API with fibonacci, prime, lcm, hcf, and AI capabilities.

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+

### Local Development

```bash
# Build
mvn clean package

# Run (set your Chitkara email and Gemini API key)
java -jar target/bfhl-api-1.0.0.jar
```

### Environment Variables

```bash
GEMINI_API_KEY=your_gemini_api_key_here
```

Update `OFFICIAL_EMAIL` in `BfhlController.java` with your Chitkara email.

## API Endpoints

### POST /bfhl

**Fibonacci:**
```json
{"fibonacci": 7}
```
Response: `{"is_success":true,"official_email":"...","data":[0,1,1,2,3,5,8]}`

**Prime:**
```json
{"prime": [2,4,7,9,11]}
```
Response: `{"is_success":true,"official_email":"...","data":[2,7,11]}`

**LCM:**
```json
{"lcm": [12,18,24]}
```
Response: `{"is_success":true,"official_email":"...","data":72}`

**HCF:**
```json
{"hcf": [24,36,60]}
```
Response: `{"is_success":true,"official_email":"...","data":12}`

**AI:**
```json
{"AI": "What is the capital city of Maharashtra?"}
```
Response: `{"is_success":true,"official_email":"...","data":"Mumbai"}`

### GET /health

Response: `{"is_success":true,"official_email":"..."}`

## Deployment

### Render

1. Push code to GitHub (public repo)
2. Go to [Render Dashboard](https://dashboard.render.com/)
3. New → Web Service
4. Connect repository
5. Settings:
   - **Build Command:** `mvn clean package`
   - **Start Command:** `java -jar target/bfhl-api-1.0.0.jar`
   - **Environment Variables:** Add `GEMINI_API_KEY`
6. Deploy

### Railway

1. Push to GitHub
2. Go to [Railway](https://railway.app/)
3. New Project → Deploy from GitHub
4. Select repository
5. Add environment variable: `GEMINI_API_KEY`
6. Deploy automatically

### Vercel (with serverless adapter)

Not recommended for Spring Boot. Use Render or Railway instead.

## Testing

```bash
# Health check
curl http://localhost:8080/health

# Fibonacci
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"fibonacci":7}'

# Prime
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"prime":[2,4,7,9,11]}'

# LCM
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"lcm":[12,18,24]}'

# HCF
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"hcf":[24,36,60]}'

# AI
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"AI":"What is the capital city of Maharashtra?"}'
```

## Error Handling

- **400 Bad Request:** Invalid input, missing fields, wrong data types
- **500 Internal Server Error:** Unexpected errors
- All errors return `is_success: false` with error message

## Security Features

- Input validation on all endpoints
- Type checking for all parameters
- Boundary condition handling
- No sensitive data exposure
- Environment-based configuration

## Project Structure

```
bajaj/
├── pom.xml
├── src/main/
│   ├── java/com/bajaj/
│   │   ├── BfhlApplication.java
│   │   ├── BfhlController.java
│   │   ├── BfhlService.java
│   │   └── ApiResponse.java
│   └── resources/
│       └── application.properties
└── README.md
```

## Get Gemini API Key

1. Visit https://aistudio.google.com
2. Sign in with Google
3. Click "Get API Key"
4. Create API key in new project
5. Copy and set as environment variable
