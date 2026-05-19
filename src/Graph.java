import java.util.ArrayList;
import java.util.List;

public class Graph {

    public static final int INF = Integer.MAX_VALUE / 2;

    private List<String> cities;
    private int[][] matrix;

    public Graph()
    {
        this.cities = new ArrayList<>();
        this.matrix = new int[0][0];
    }

    //agrega ciudad si no existe
    public void addCity(String name)
    {
        if(getIndex(name) >= 0) return;

        cities.add(name.toLowerCase());
        int n = cities.size();

        int[][] newMatrix = new int[n][n];
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                newMatrix[i][j] = (i == j) ? 0 : INF;
            }
        }

        //copia la matriz anterior
        for(int i = 0; i < n-1; i++)
        {
            for(int j = 0; j < n-1; j++)
            {
                newMatrix[i][j] = matrix[i][j];
            }
        }

        this.matrix = newMatrix;
    }

    //agrega o actualiza arco dirigido
    public void addEdge(String from, String to, int km)
    {
        addCity(from);
        addCity(to);
        matrix[getIndex(from)][getIndex(to)] = km;
    }

    //elimina arco (pone INF), retorna false si no existe
    public boolean removeEdge(String from, String to)
    {
        int i = getIndex(from);
        int j = getIndex(to);
        if(i < 0 || j < 0) return false;
        matrix[i][j] = INF;
        return true;
    }

    public int getIndex(String name)
    {
        return cities.indexOf(name.toLowerCase());
    }

    public String getCity(int index)
    {
        return cities.get(index);
    }

    public int size()
    {
        return cities.size();
    }

    public List<String> getCities()
    {
        return this.cities;
    }

    //retorna copia de la matriz
    public int[][] getMatrix()
    {
        int n = cities.size();
        int[][] copy = new int[n][n];
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                copy[i][j] = matrix[i][j];
            }
        }
        return copy;
    }

    public void printMatrix()
    {
        int n = cities.size();

        System.out.printf("%-20s", "");
        for(String c : cities) System.out.printf("%-20s", c);
        System.out.println();

        for(int i = 0; i < n; i++)
        {
            System.out.printf("%-20s", cities.get(i));
            for(int j = 0; j < n; j++)
            {
                String cell = (matrix[i][j] >= INF) ? "INF" : String.valueOf(matrix[i][j]);
                System.out.printf("%-20s", cell);
            }
            System.out.println();
        }
    }
}