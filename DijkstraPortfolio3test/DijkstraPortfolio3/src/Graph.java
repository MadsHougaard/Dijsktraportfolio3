import java.util.*;
import javafx.util.Pair;

public class Graph {
    private ArrayList<Vertex> Vertices = new ArrayList<>();

    public Vertex addvertex(String id) 
    {
        Vertex newvertex = new Vertex(id);
        Vertices.add(newvertex);
        return newvertex;
    }

    public void addvertex(Vertex v) 
    {
        Vertices.add(v);
    }
    public Vertex getvertex(String s)
    {
        for(Vertex v : Vertices )
        {
            if (v.Name==s)
                return v;
        }
        return null;
    }

    public void newedge(Vertex from, Vertex to, int dist, int tim) 
    {
        Edge newedge = new Edge(from,to);
        newedge.distance=dist;
        newedge.time=tim;
    }

    public Pair<Integer, Map<Vertex,Vertex> > ShortestDistance(Vertex source, Vertex zink)
    {
        Map<Vertex,Vertex> PredecessorMap = new HashMap<>();
        Map<Vertex,Integer> DistanceMap = new HashMap<>();
        Map<Vertex,Integer> UnvisitedMap = new HashMap<>();
        // Initialiserer arrays
        for(Vertex v: Vertices)
        {
            DistanceMap.put(v,1000); // En værdi der er så stor at den aldrig nås
            PredecessorMap.put(v, null);
            UnvisitedMap.put(v, 1000); // En værdi der er så stor at den aldrig nås. Giver os punkter som vi har besøgt
        }

        DistanceMap.put(source,0); // lav startpunkt
        UnvisitedMap.put(source,0); // lav startpunkt på anden liste. Skal være nul fordi det er startpunkt dvs. ingen længde derhen
        // Implement Dijkstra

        // Gennemgå alle ubesøgte punkter og udregn distancen til punktet naboer
        // Så længe der stadig er ubesøgte punkter do stuff
        while (UnvisitedMap.size() > 0) 
        {
            Vertex currentVertex = getmin(UnvisitedMap); // Udvælg det ubesøgte punkt der har den korteste distance p.t.

            int currentDistance = UnvisitedMap.get(currentVertex); // Finder Node, tager distance værdi og gemmer i currentDistance

            UnvisitedMap.remove(currentVertex); // Fjern currentVertex fordi den ny antages som besøgt. Vi vønsker ikke at støde på den igen når vi gennemgår listen UnvisitedMap

            // Løb igennem alle Edges der er forbundet med currentVertex
            for(Edge edge : currentVertex.getOutEdges()) 
            {
                Vertex neighbourVertex = edge.getTovertex(); // Finder punkt

                int neighbourDistance = currentDistance + edge.distance; // Lav vores distance til nabo ved at finde den totale distance dertil

                if(neighbourDistance < DistanceMap.get(neighbourVertex) ) 
                {
                    DistanceMap.put(neighbourVertex, neighbourDistance);
                    PredecessorMap.put(neighbourVertex, currentVertex);
                } 
                // Sætter nabovertex distanceværdi lig med den totale distance dertil
                if (UnvisitedMap.get(neighbourVertex) != null) 
                {
                    UnvisitedMap.put(neighbourVertex, neighbourDistance);
                }
            }
        }

        for (Map.Entry<Vertex, Integer> entry : DistanceMap.entrySet()) 
        {
            System.out.println("Node: "+entry.getKey().Name+"  -  Distance: " + entry.getValue());
        }



        return (new Pair<Integer,Map<Vertex,Vertex>> (DistanceMap.get(zink), PredecessorMap));
    }

    // Returnerer vertex med korteste distance på en vilkårlig liste (Map)
    public Vertex getmin(Map<Vertex,Integer> qmap){
        int shortestDistance = 9999; // En værdi der er så stor at den aldrig nås
        Vertex shortestVertex = null;
        for (Map.Entry<Vertex, Integer> entry : qmap.entrySet()) 
        {

            if (entry.getValue() < shortestDistance) 
            {
                shortestVertex = entry.getKey();
                shortestDistance = entry.getValue();
            }
        }
        return shortestVertex;
    }
}



class Vertex {
    public String Name;
    public ArrayList<Edge> OutEdges = new ArrayList<>();
    public  Vertex(String id){
        Name=id;
    }
    public void addOutEdge(Edge outedge){
        OutEdges.add(outedge);
    }
    public ArrayList<Edge> getOutEdges() {return OutEdges;}
}

class Edge {
    private Vertex fromvertex;
    private Vertex tovertex;
    public int distance=0;
    public int time=0;

    public Vertex getTovertex() {
        return tovertex;
    }
    // Directional boolean
    public Edge(Vertex from, Vertex to) {
        fromvertex=from;
        tovertex=to;
        fromvertex.addOutEdge(this);
        // Hvis ikke directional
        tovertex.addOutEdge(this);
    }
}
