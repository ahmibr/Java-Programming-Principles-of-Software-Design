
/**
 * Find N-closest quakes
 * @author Ahmed Ibrahim
 */

import java.util.*;

public class ClosestQuakes {
    public ArrayList<QuakeEntry> getClosest(ArrayList<QuakeEntry> quakeData, Location current, int howMany) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        int size = quakeData.size();
        ArrayList<Boolean> removed = new ArrayList<Boolean>(Collections.nCopies(size, false));
        
        if(howMany > size){
            howMany = size;
        }
        
        for(int count = 0; count < howMany ; ++count){
            QuakeEntry minQuake = null;
            Location minLocation = null;
            int minIndex = -1;
            
            for(int index = 0; index < size ; ++index){
                //if element isn't removed
                if(removed.get(index) == false){ 
                    Location loc = quakeData.get(index).getLocation();
                    //if this is first non-removed element or it has min distance
                    if(minQuake == null || loc.distanceTo(current) < minLocation.distanceTo(current)){
                        minQuake = quakeData.get(index);
                        minLocation = loc;
                        minIndex = index;
                    }
                }
            }
            
            answer.add(minQuake);
            removed.set(minIndex,true);
            
        }
        return answer;
    }

    public void findClosestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        // String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size());

        Location jakarta  = new Location(-6.211,106.845);

        ArrayList<QuakeEntry> close = getClosest(list,jakarta,3);
        for(int k=0; k < close.size(); k++){
            QuakeEntry entry = close.get(k);
            double distanceInMeters = jakarta.distanceTo(entry.getLocation());
            System.out.printf("%4.2f\t %s\n", distanceInMeters/1000,entry);
        }
        System.out.println("number found: "+close.size());
    }
    
}
