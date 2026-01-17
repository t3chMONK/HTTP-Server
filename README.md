# What this server will do

- Listen on a port (example: **8080**)
- Accept browser or curl connections
- Parse HTTP requests
- Support:
    - **GET**
    - **HEAD**
- Return:
    - Proper HTTP status line
    - Headers
    - Body (only for GET)

# Run the server

```bash
javac SimpleHttpServer.java
java SimpleHttpServer
```

Output:

```
HTTPServer running on port8080
```

# Test it

### Browser

```
http://localhost:8080
```

### curl

```bash
curl http://localhost:8080
```

### HEAD request

```bash
curl -I http://localhost:8080
```

Output:

```bash
└─$ curl -I http://localhost:8080

HTTP/1.1 200 OK
Content-Type: text/html
Content-Length: 51
Connection: close
```

# ⚠️ Important security note

This server is vulnerable to:

- Directory traversal
- Request smuggling
- Header injection
- Slowloris
- No input validation

