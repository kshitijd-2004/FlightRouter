run: runFDTest runBDTests
	java Frontend

runFDTest: FrontendDeveloperTests.class 
	java -jar ../junit5.jar --class-path=. --select-class=FrontendDeveloperTests

runBDTests: BackendDeveloperTests.class
	java -jar ../junit5.jar --class-path=. --select-class=BackendDeveloperTests

FrontendDeveloperTests.class: Frontend.java BackendPlaceholder.java TextUITester.java FrontendDeveloperTests.java
	javac -cp ../junit5.jar:. FrontendDeveloperTests.java

Frontend.class: Frontend.java
	javac Frontend.java

BackendPlaceholder.class: BackendPlaceholder.java
	javac BackendPlaceholder.java

TextUITester.class: TextUITester.java
	javac TextUITester.java

BackendDeveloperTests.class: DijkstraGraph.class BackendDeveloperTests.java BackendClass.class ShortestPathClass.class PlaceholderMap.class BaseGraph.class
	javac -cp ../junit5.jar BackendDeveloperTests.java ShortestPathClass.java BackendClass.java DijkstraGraph.java PlaceholderMap.java BaseGraph.java MapADT.java GraphADT.java ShortestPathInterface.java BackendInterface.java

BackendClass.class: BackendClass.java
	javac BackendClass.java

ShortestPathClass.class: ShortestPathClass.java ShortestPathInterface.java
	javac ShortestPathClass.java ShortestPathInterface.java

BaseGraph.class: BaseGraph.java
	javac BaseGraph.java

PlaceholderMap.class: PlaceholderMap.java MapADT.java
	javac PlaceholderMap.java MapADT.java

DijkstraGraph.class: DijkstraGraph.java BaseGraph.class GraphADT.java  
	javac -cp ../junit5.jar:. FrontendDeveloperTests.java
	javac BaseGraph.java GraphADT.java

clean:
	rm -f *.class

