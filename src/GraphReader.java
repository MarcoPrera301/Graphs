import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GraphReader {

    public static Graph read(String filename)
    {
        Path filePath = Paths.get(filename);
        try
        {
            String content = Files.readString(filePath);
            return parseContent(content);
        }
        catch(IOException e)
        {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
        return null;
    }

    private static Graph parseContent(String content)
    {
        Graph graph = new Graph();
        String[] lines = content.trim().split("\n");

        for(String line : lines)
        {
            String trimmed = line.trim();
            if(trimmed.isEmpty() || trimmed.startsWith("#")) continue;

            String[] parts = trimmed.split("\\s+");
            if(parts.length < 3) continue;

            String from = parts[0];
            String to   = parts[1];
            int km;

            try
            {
                km = Integer.parseInt(parts[2]);
            }
            catch(NumberFormatException e)
            {
                System.err.println("Peso invalido en linea: " + line);
                continue;
            }

            graph.addEdge(from, to, km);
        }

        return graph;
    }
}
