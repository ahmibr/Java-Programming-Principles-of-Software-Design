import java.util.*;
import edu.duke.*;

/**
 * @author Ahmed Ibrahim
 */

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        
        for(QuakeEntry qe : quakeData){
            if(qe.getMagnitude() > magMin){
                answer.add(qe);
            }
        }

        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData){
            Location loc = qe.getLocation();
            if(loc.distanceTo(from) < distMax){
                answer.add(qe);   
            }
        }
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData,
    double minDepth,
    double maxDepth){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        
        for(QuakeEntry qe : quakeData){
            double depth = qe.getDepth();
            if(depth > minDepth && depth < maxDepth){
                answer.add(qe);
            }
        }
        
        return answer;
    }
    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        
        ArrayList<QuakeEntry> bigQuakesList = filterByMagnitude(list, 5);
        for(QuakeEntry qe : bigQuakesList){
            System.out.println(qe);
        }
        System.out.printf("Found %d quakes that match that criteria\n",bigQuakesList.size());
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        // Location city = new Location(35.988, -78.907);

        // This location is Bridgeport, CA
        Location city =  new Location(38.17, -118.82);
        //Note that Location calculates distance with meters
        ArrayList<QuakeEntry> closeQuakes = filterByDistanceFrom(list,1000000,city);
        
        for(QuakeEntry qe : closeQuakes){
            Location loc = qe.getLocation();
            //Convert distance to KM
            System.out.printf("%f %s\n",loc.distanceTo(city)/1000 , qe.getInfo());
        }
        System.out.printf("Found %d quakes that match that criteria\n",closeQuakes.size());
    }

    public void quakeOfDepth(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        
        ArrayList<QuakeEntry> deepQuakes = filterByDepth(list, -10000 , -5000);
        System.out.println("Find quakes with depth between -10000.0 and -5000.0");
        for(QuakeEntry qe : deepQuakes){
            System.out.println(qe);
        }
        System.out.printf("Found %d quakes that match that criteria\n",deepQuakes.size());
    }
    
    
    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
}
