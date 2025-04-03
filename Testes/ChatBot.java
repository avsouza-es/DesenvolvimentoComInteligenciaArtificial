import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;

public class Chatbot {
    private static final String OLLAMA_URL = "http://192.168.221.153:11434/api/generate"; // Ollama API local

    public static String askBetex(String question) {
        String context = VectorDatabase.search(question);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(OLLAMA_URL);
            request.setHeader("Content-Type", "application/json");

            JsonObject json = new JsonObject();
            json.addProperty("model", "betex"); // Nome do modelo no Ollama
            json.addProperty("prompt", "Contexto: " + context + "\nPergunta: " + question);
            json.addProperty("stream", false);

            StringEntity entity = new StringEntity(json.toString(), StandardCharsets.UTF_8);
            request.setEntity(entity);

            try (CloseableHttpResponse response = client.execute(request)) {
                JsonObject jsonResponse = JsonParser.parseReader(new java.io.InputStreamReader(response.getEntity().getContent())).getAsJsonObject();
                return jsonResponse.get("response").getAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao conectar com a IA.";
        }
    }
}
