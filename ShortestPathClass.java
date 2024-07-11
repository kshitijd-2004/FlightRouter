import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ShortestPathClass implements ShortestPathInterface{
  private String start;
  private  String destination;
  private DijkstraGraph<String, Integer> graph;



  public ShortestPathClass(String start, String destination,
      DijkstraGraph<String, Integer> graph) throws NoSuchElementException {
      if(!graph.containsNode(start) || !graph.containsNode(destination)){
        throw new NoSuchElementException("Airport not found, Please Try Again.");
      }
      this.start = start;
      this.destination = destination;
      this.graph = graph;

  }


  /**
   * This method returns a list of airports along the shortest path route from start to destination
   * @return Arraylist
   */
  @Override
  public List<String> getRoute() {
    List<String> data =
        graph.shortestPathData(this.start,this.destination);
    return data;
  }

  @Override
  public List<String> milesPerSegment() {

   List<String> toReturn = new ArrayList<>();
    double x = graph.computeShortestPath(this.start,this.destination).cost;
    DijkstraGraph.SearchNode current = graph.computeShortestPath(this.start,this.destination);
    DijkstraGraph.SearchNode predecessor = current.predecessor;

    //USE: current.node.data.toString();
    while (predecessor != null) {
      String segment =
          predecessor.node.data.toString() + " - " + current.node.data.toString() + ": " +
              (int)(current.cost- predecessor.cost);
      toReturn.add(0,segment);
      current = predecessor;
      predecessor = predecessor.predecessor;
    }
    return toReturn;
  }

  @Override
  public double getTotalMiles() {
    return graph.shortestPathCost(this.start,this.destination);
  }
}
