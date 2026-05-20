import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithm {

    private Graph graph;
    private int[][] dist;
    private int[][] next;

    public Algorithm(Graph graph)
    {
        this.graph = graph;
        run();
    }

    //ejecuta floyd-warshall
    public void run()
    {
        int n = graph.size();
        dist = graph.getMatrix();
        next = new int[n][n];

        //inicializa next
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                if(dist[i][j] < Graph.INF && i != j)
                    next[i][j] = j;
                else
                    next[i][j] = -1;
            }
        }

        //triple loop principal
        for(int k = 0; k < n; k++)
        {
            for(int i = 0; i < n; i++)
            {
                for(int j = 0; j < n; j++)
                {
                    if(dist[i][k] < Graph.INF && dist[k][j] < Graph.INF)
                    {
                        int via = dist[i][k] + dist[k][j];
                        if(via < dist[i][j])
                        {
                            dist[i][j] = via;
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
        }
    }

    public int getDistance(String from, String to)
    {
        int i = graph.getIndex(from);
        int j = graph.getIndex(to);
        if(i < 0 || j < 0) return Graph.INF;
        return dist[i][j];
    }

    //reconstruye la ruta
    public List<String> getPath(String from, String to)
    {
        int i = graph.getIndex(from);
        int j = graph.getIndex(to);

        if(i < 0 || j < 0 || dist[i][j] >= Graph.INF) return Collections.emptyList();

        List<String> path = new ArrayList<>();
        path.add(graph.getCity(i));

        while(i != j)
        {
            i = next[i][j];
            path.add(graph.getCity(i));
        }

        return path;
    }


//algoritmo de centro de grafo
    public String getCenter()
    {
        int n = graph.size();
        if(n == 0) return null;

        int minEcc = Graph.INF;
        int centerIndex = -1;

        for(int i = 0; i < n; i++)
        {
            int ecc = 0;

            for(int w = 0; w < n; w++)
            {
                if(w == i) continue;
                if(dist[w][i] >= Graph.INF) { ecc = Graph.INF; break; }
                if(dist[w][i] > ecc) ecc = dist[w][i];
            }

            if(ecc < minEcc)
            {
                minEcc = ecc;
                centerIndex = i;
            }
        }

        return centerIndex >= 0 ? graph.getCity(centerIndex) : null;
    }

    public void printDistMatrix()
    {
        int n = graph.size();

        System.out.printf("%-20s", "");
        for(String c : graph.getCities()) System.out.printf("%-20s", c);
        System.out.println();

        for(int i = 0; i < n; i++)
        {
            System.out.printf("%-20s", graph.getCity(i));
            for(int j = 0; j < n; j++)
            {
                String cell = (dist[i][j] >= Graph.INF) ? "INF" : String.valueOf(dist[i][j]);
                System.out.printf("%-20s", cell);
            }
            System.out.println();
        }
    }
}