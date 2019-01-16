import java.util.*;

/**
 * Determining the N-biggest earthquakes.
 * @author Ahmed Ibrahim
 */


public class LargestQuakes {
    
    public int indexOfLargest (ArrayList<QuakeEntry> data){
        
        int largestIndex = 0;
        int size = data.size();
        
        for(int index = 1; index < size ; ++index){
           if(data.get(index).getMagnitude() > data.get(largestIndex).getMagnitude()){
               largestIndex = index;
           }
        }
        
        return largestIndex;
    }
    
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
       
        if(howMany > quakeData.size()){
            howMany = quakeData.size();
        }
        
        for(int count = 0; count < howMany ; ++count){
            int largestIndex = indexOfLargest(copy);
            answer.add(copy.get(largestIndex));
            copy.remove(largestIndex);
        }
        
        return answer;
    }
    
    public void findLargestQuakes(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        // for(QuakeEntry qe : list){
            // System.out.println(qe);
        // }
        System.out.println("read data for "+list.size()+" quakes");
        
        // int largestIndex = indexOfLargest(list);
        // System.out.printf("Largest index = %d, Largest magnitude = %f\n",largestIndex,list.get(largestIndex).getMagnitude());
        
        ArrayList<QuakeEntry> largest = getLargest(list, 5);
        
        for(QuakeEntry qe : largest){
            System.out.println(qe);
        }
    }
}
