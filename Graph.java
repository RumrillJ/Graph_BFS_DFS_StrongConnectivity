
import java.util.*;

// GRaph Class Implementation
class Graph {

    private final int V;
    private final LinkedList<Integer> adj[];

    //Graph Constructor
    Graph(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    //Add Graph Edge
    void addEdge(int v, int w)
    {
        adj[v].add(w); //add V to W
    }


    void DFSUtility(int v, boolean visited[])
    {
        //Boolean for Visited set
        visited[v] = true;
        System.out.print(v + " ");


        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n])
                DFSUtility(n, visited);
        }
    }


    void DFS(int v)
    {
        boolean visited[] = new boolean[V];
        DFSUtility(v, visited);
    }

    void BFS(int s)
    {
        boolean visited[] = new boolean[V];

        LinkedList<Integer> queue = new LinkedList<>();

        visited[s]=true;
        queue.add(s);

        while (!queue.isEmpty())
        {
            s = queue.poll();
            System.out.print(s+" ");

            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }

    Graph getTranspose()
    {
        Graph g = new Graph(V);
        for (int v = 0; v < V; v++)
        {
            Iterator<Integer> i = adj[v].listIterator();
            while (i.hasNext())
                g.adj[i.next()].add(v);
        }
        return g;
    }


    void fillOrder(int v, boolean visited[], Stack stack)
    {
        //mark as visited to stack
        visited[v] = true;

        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext())
        {
            int n = i.next();
            if(!visited[n])
                fillOrder(n, visited, stack);
        }

        stack.push(v);
    }

    //Strong Connectivity function
    void printSCCs()
    {
        Stack stack = new Stack();


        boolean visited[] = new boolean[V];
        for(int i = 0; i < V; i++)
            visited[i] = false;


        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                fillOrder(i, visited, stack);

        Graph gr = getTranspose();

        for (int i = 0; i < V; i++)
            visited[i] = false;

        while (stack.empty() == false)
        {
            // Pop a vertex from stack
            int v = (int)stack.pop();

            // Print Strongly connected component of the popped vertex
            if (visited[v] == false)
            {
                gr.DFSUtility(v, visited);
                System.out.println();
            }
        }
    }
    boolean isCycle()
    {

        //Vector Creation
        int[] in_degree = new int[this.V];
        Arrays.fill(in_degree, 0);

        //Traverse Verticles
        for (int u = 0; u < V; u++)
        {
            for (int v : adj[u])
                in_degree[v]++;
        }

        //Integer Queue
        Queue<Integer> q;
        q = new LinkedList<>();
        for (int i = 0; i < V; i++)
            if (in_degree[i] == 0)
                q.add(i);


        int count = 0;

        Vector<Integer> top_order;
        top_order = new Vector<>();

        while (!q.isEmpty())
        {
            int u = q.poll();
            top_order.add(u);

            adj[u].stream().filter(itr -> (--in_degree[itr] == 0)).forEachOrdered(itr -> {
                q.add(itr);
            });
            count++;
        }

        //Check for cycle
        return count != this.V;
    }

    // Driver Code
    public static void main(String args[])
    {
        Graph g = new Graph(5);

        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        System.out.println("Depth First Traversal from vertex 1: ");
        g.DFS(1);

        System.out.println("");

        System.out.println("Breadth First Traversal from vertex 1: )");
        g.BFS(1);

        System.out.println("");
        System.out.println("The Following Are Strong Connectivity: ");
        g.printSCCs();

        if (g.isCycle())
            System.out.println("There is a Direct Cycle");
        else
            System.out.println("There is not a Direct Cycle");
    }
}

