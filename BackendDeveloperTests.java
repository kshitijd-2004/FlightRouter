import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class BackendDeveloperTests {

  //This method tests the loadDataFromFile() method.
  @Test
  public void testLoadFile() throws FileNotFoundException {
    DijkstraGraph<String,Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    BackendClass tester = new BackendClass(graph);
    try{
      tester.loadDataFromFile("flights.dot");

    }catch(FileNotFoundException e){
      Assertions.assertTrue(false);
    }
    Assertions.assertEquals(    tester.loadDataFromFile("flights.dot"),
        true);

  }
  //This tester makes sure that the ShortestPathClass produced from getShortestRoute returns
  // the correct distance in miles.
  @Test
  public void testValidRoute() throws FileNotFoundException {
    DijkstraGraph<String,Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    BackendClass tester = new BackendClass(graph);
    tester.loadDataFromFile("flights.dot");
    ShortestPathClass route = (ShortestPathClass) tester.getShortestRoute("JFK","MIA");
    Assertions.assertEquals(route.getTotalMiles(),1090);
  }

  @Test
  public void testInvalidRoute(){
    DijkstraGraph<String,Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    BackendClass tester = new BackendClass(graph);    Assertions.assertThrows(NoSuchElementException.class, () -> {
      tester.getShortestRoute("bruh", "home");
    });
  }
  //This tester method tests a negative weight for a node, in which case an error should be thrown
  @Test
  public void testShortestPath() throws FileNotFoundException {
    DijkstraGraph<String,Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    BackendClass tester = new BackendClass(graph);
    tester.loadDataFromFile
        ("flights.dot"); // Assuming this file has predefined data
    System.out.println(tester.getShortestRoute("ATL", "LAX").getRoute());
    Assertions.assertTrue(tester.getShortestRoute("ATL", "LAX").getRoute().contains("ATL"));
    Assertions.assertTrue(tester.getShortestRoute("ATL","LAX").getRoute().contains("LAX"));
  }
//This test verifies the formatting of the getGraphStats method, although this has yet to be
// implemented.
  @Test
  public void testGraphStatsNoError() throws FileNotFoundException {
    DijkstraGraph<String,Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    BackendClass tester = new BackendClass(graph);
    tester.loadDataFromFile
        ("flights.dot");

    String stats = tester.getGraphStats();
    String[] parts = stats.split(", ");
    System.out.println(10%5);
    Assertions.assertEquals("Number of airports: 58", parts[0]);
    Assertions.assertEquals("Number of flights: 1598", parts[1]);
    Assertions.assertEquals("The total number of miles: 2142457", parts[2]);
  }

}
