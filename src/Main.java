import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class Main {
    private static DataProcessor processor = new DataProcessor();
    private static final String DEFAULT_CSV = "data/students.csv";

    public static void main(String[] args) throws IOException {
        // Load default data if exists
        File csvFile = new File(DEFAULT_CSV);
        if (csvFile.exists()) {
            processor.loadFromCSV(DEFAULT_CSV);
            System.out.println("Loaded default data from " + DEFAULT_CSV);
        } else {
            System.out.println("Default CSV not found at " + csvFile.getAbsolutePath() + ". Waiting for user upload.");
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        
        // Serve Static Files
        server.createContext("/", new StaticFileHandler());
        
        // API to get data
        server.createContext("/api/data", exchange -> {
            String response = processor.toJson();
            sendResponse(exchange, response, "application/json");
        });

        // API to upload CSV content
        server.createContext("/api/upload", exchange -> {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String csvData = br.lines().collect(Collectors.joining("\n"));
                processor.loadFromData(csvData);
                sendResponse(exchange, "{\"status\":\"success\"}", "application/json");
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("Dashboard Server started!");
        System.out.println("Open your browser and visit: http://localhost:8081");
    }

    private static void sendResponse(HttpExchange exchange, String response, String contentType) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        byte[] bytes = response.getBytes("utf-8");
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) path = "/index.html";
            
            File file = new File("web" + path);
            if (!file.exists()) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }

            String contentType = "text/plain";
            if (path.endsWith(".html")) contentType = "text/html";
            else if (path.endsWith(".css")) contentType = "text/css";
            else if (path.endsWith(".js")) contentType = "application/javascript";

            exchange.getResponseHeaders().set("Content-Type", contentType);
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            exchange.sendResponseHeaders(200, fileBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(fileBytes);
            os.close();
        }
    }
}
