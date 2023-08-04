import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {
		this.trainZero = new TNode(0);
		TNode busZero = new TNode(0);
		trainZero.setDown(busZero);

		TNode walkZero = new TNode(0);
		busZero.setDown(walkZero);

		int locationsTotal = locations.length;
		int stopsTotal = busStops.length;
		int stationsTotal = trainStations.length;

		for(int walk = 0;walk<locationsTotal;walk++)
		{
			TNode walkTemp = new TNode(locations[walk]);
			walkZero.setNext(walkTemp);
			walkZero = walkZero.getNext();
		}
		walkZero = busZero.getDown();

		for(int bus = 0; bus<stopsTotal; bus++)
		{
			TNode stopTemp = new TNode(busStops[bus]);
			busZero.setNext(stopTemp);
			busZero = busZero.getNext();
		}
		busZero = trainZero.getDown();
		walkZero = busZero.getDown();

		TNode temp = trainZero;

		for(int train = 0; train<stationsTotal;train++)
		{	
			TNode stationTemp = new TNode(trainStations[train]);
			trainZero.setNext(stationTemp);
			trainZero = trainZero.getNext();
		}

		trainZero = temp;
		TNode station = trainZero.getNext();
		busZero = busZero.getNext();
		walkZero = walkZero.getNext();

		for(int i = 0; i<locationsTotal;i++)
		{
			if(station!=null&&busZero!=null&&station.getLocation()==busZero.getLocation()&&busZero.getLocation()==walkZero.getLocation())
			{
				station.setDown(busZero);
				busZero.setDown(walkZero);

				station = station.getNext();
				busZero = busZero.getNext();

			}

			if(busZero!=null&&busZero.getLocation()==walkZero.getLocation())
			{
				busZero.setDown(walkZero);
				busZero = busZero.getNext();
			}

			walkZero = walkZero.getNext();

		}

	}
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
		for(TNode stat = trainZero;stat!=null;stat=stat.getNext())
		{
			if(stat.getNext()!=null&&stat.getNext().getLocation()==station)
			{
				TNode temp = stat.getNext().getNext();
				if (temp!=null)
					stat.setNext(temp);
				else
					stat.setNext(null);
			}
		}
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
	    
		TNode bus = trainZero.getDown();
		TNode newStop = new TNode(busStop);

		while(bus!=null)
		{
			if(bus.getNext()==null&&bus.getLocation()<busStop)
			{
				bus.setNext(newStop);
			}
			else 
			{if(bus.getNext()!=null&&bus.getNext().getLocation()>busStop&&bus.getLocation()<busStop)
			{
				newStop.setNext(bus.getNext());
				bus.setNext(newStop);
			}}
			bus = bus.getNext();
		}


		/*for(TNode bus = trainZero.getDown();bus==null||bus.getLocation()<=busStop;bus = bus.getNext())
		{
			
			if(bus.getNext()==null)
			{
				bus.setNext(new TNode(busStop));
			}
			
			else if(bus.getLocation()<busStop && bus.getNext().getLocation()>busStop)
				{
					TNode newBus = new TNode(busStop);
					if(bus.getNext()==null)
					{
						bus.setNext(newBus);
					}
					
					else
					{
					newBus.setNext(bus.getNext());
					bus.setNext(newBus);
					}

					for(TNode train = trainZero;train.getLocation()<busStop;train = train.getNext())
					{
						if(train.getLocation()==busStop)
							train.setDown(bus);
					}
				}
			
		}
		*/
		bus = trainZero.getDown(); 
		TNode busDown = bus.getDown();

		while(busDown!=null)
		{
			if(bus!=null&&busDown!=null&&bus.getLocation()==busDown.getLocation())
			{
				bus.setDown(busDown);
				bus = bus.getNext();
			}

			busDown = busDown.getNext();

		}

	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {

	    ArrayList<TNode> path = new ArrayList<TNode>();
		TNode start = trainZero;

		while(start.getNext()!=null&&start.getNext().getLocation()<=destination)
		{
			path.add(start);
			TNode temp = start.getNext();
			if(temp!=null&&start.getNext().getLocation()==destination){
				path.add(start.getNext());
				//return path;
			}

			start = start.getNext();
		}
		path.add(start);
		start = start.getDown();

		while(start.getNext()!=null&&start.getNext().getLocation()<=destination)
		{
			path.add(start);
			TNode temp = start.getNext();
			if(temp!=null&&start.getNext().getLocation()==destination){
				path.add(start.getNext());
				//return path;
			}

			start = start.getNext();
		}
		path.add(start);
		TNode busFinal = start;
		start = start.getDown();

		while(start.getNext()!=null&&start.getLocation()<=destination)
		{
			if(start.getLocation()==busFinal.getLocation()&&start.getLocation()==destination)
				return path;
			path.add(start);
			start = start.getNext();
		}
	    
		return path;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
		TNode trainZeroCopy = new TNode(trainZero.getLocation());
	    
		TNode train = trainZero.getNext();
		TNode bus = trainZero.getDown();
		TNode walk = bus.getDown().getNext();
		bus = bus.getNext(); 

		TNode busCopy = new TNode(0);
		TNode walkCopy = new TNode(0);
		TNode temp = trainZeroCopy;

		trainZeroCopy.setDown(busCopy);
		busCopy.setDown(walkCopy);
		
		while(train!=null)
		{
			trainZeroCopy.setNext(new TNode(train.getLocation()));
			train = train.getNext();
			trainZeroCopy = trainZeroCopy.getNext();
		}

		while(bus!=null)
		{
			busCopy.setNext(new TNode(bus.getLocation()));
			bus = bus.getNext();
			busCopy = busCopy.getNext();
		}

		while(walk!=null)
		{
			walkCopy.setNext(new TNode(walk.getLocation()));
			walkCopy = walkCopy.getNext();
			walk = walk.getNext();
		}

		trainZeroCopy = temp;
		busCopy = trainZeroCopy.getDown();
		walkCopy = busCopy.getDown();

		walk = trainZero.getDown().getDown();
		while(walk!=null)
		{
			if(trainZeroCopy!=null&&busCopy!=null&&trainZeroCopy.getLocation()==busCopy.getLocation()&&busCopy.getLocation()==walkCopy.getLocation())
			{
				trainZeroCopy.setDown(busCopy);
				busCopy.setDown(walkCopy);
				trainZeroCopy = trainZeroCopy.getNext();
				busCopy = busCopy.getNext();
			}

			else if(busCopy!=null&&walkCopy!=null&&busCopy.getLocation()==walkCopy.getLocation())
			{
				busCopy.setDown(walkCopy);
				busCopy = busCopy.getNext();
			}

			walkCopy = walkCopy.getNext();
			walk = walk.getNext();
		}

		trainZeroCopy = temp;

		return trainZeroCopy;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {
		int stops = scooterStops.length;
		TNode scoot = new TNode(0);
		TNode scootZero = scoot;
		for(int sc = 0;sc<stops;sc++)
		{
			TNode temp = new TNode(scooterStops[sc]);
			scoot.setNext(temp);
			scoot = scoot.getNext();
		}

		TNode bus = trainZero.getDown();
		TNode walk = bus.getDown();
		bus.setDown(scootZero);
		scootZero.setDown(walk);
		scoot = scootZero.getNext();
		walk = walk.getNext();
		bus = bus.getNext();

		while(walk!=null)
		{
			if(bus!=null&&scoot!=null&&bus.getLocation()==scoot.getLocation()&&scoot.getLocation()==walk.getLocation())
			{
				bus.setDown(scoot);
				scoot.setDown(walk);

				scoot = scoot.getNext();
				bus = bus.getNext();
			}

			if(scoot!=null&&scoot.getLocation()==walk.getLocation())
			{
				scoot.setDown(walk);
				scoot = scoot.getNext();
			}
		
			walk = walk.getNext();
		}
	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
