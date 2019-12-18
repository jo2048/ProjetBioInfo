package main;

/**
 * The main class
 *
 */
public class Project {
	
	public static final String targetSize = "16320";
	public static final String collectionNumber = "5";
	public static final Boolean writeOnfile = true;
	
	public static final boolean multithreading = false;
	
	public static TimeMonitor timeMonitor;
	
    public static void main(String args[])
    {	
    	if (args.length == 0) {

        	timeMonitor = new TimeMonitor();

        	FragmentList fl = FragmentList.getFragmentsFromFile("Collections/"+targetSize+"/collection"+collectionNumber+".fasta");
    		OverlapGraph graph = new OverlapGraph(fl);    	
    		
    		timeMonitor.measure("Time to build graph");
    		
    		HamiltonPath path = graph.getHamiltonPath();
    				
    		timeMonitor.measure("Time to find hamilton path");
    		
    		GapPropagator gp = new GapPropagator(fl);
    		
    		FragmentBuilder[] t = gp.propagateGaps(path);
    		
    		timeMonitor.measure("Time to propagate gaps");
    		
    		Fragment f = ConsensusVote.consensusVote(t);
    		
    		timeMonitor.measure("Time to vote the consensus");
    		
    		if (writeOnfile) {
    			FragmentList.writeToFile(f, targetSize, "Collections/"+targetSize+"/result"+collectionNumber+".fasta");
    			FragmentList.writeToFile(f.getComplementary(), targetSize, "Collections/"+targetSize+"/result-ic"+collectionNumber+".fasta");
    		}
    	}
    	/*
    	else {
    		for (int i = 0; i<args.length; i++)
    			System.out.println(args[i]);
    		String fileToRead = args[0];
    		
    		FragmentList fl = FragmentList.getFragmentsFromFile("Collections/"+targetSize+"/collection"+collectionNumber+".fasta");
    		
    		OverlapGraph graph = new OverlapGraph(fl);    	
    		HamiltonPath path = graph.getHamiltonPath();
    		
    		GapPropagator gp = new GapPropagator(fl);
    		Fragment[] t = gp.propagateGaps(path);
    		
    		Fragment f = ConsensusVote.consensusVote(t);
        	
        	String fileToWrite = args[2];
        	String fileToWrite_ic = args[4];
        	
        	FragmentList.writeToFile(f, Integer.toString(10000), fileToWrite);
    		FragmentList.writeToFile(f.getComplementary(), Integer.toString(10000), fileToWrite_ic);

    	}
    	*/
    }
}