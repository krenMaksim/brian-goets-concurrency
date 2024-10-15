package brian.goets.chapter4.listing4_7;

import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
class DelegatingVehicleTracker {

  private final Map<String, Point> locations;
  private final Map<String, Point> unmodifiableLocations;

  public DelegatingVehicleTracker(Map<String, Point> locations) {
    // Take notice at this "trick"
    this.locations = new ConcurrentHashMap<>(locations);
    this.unmodifiableLocations = Collections.unmodifiableMap(this.locations);
  }

  public Map<String, Point> getLocations() {
    // "live" locations are returned
    return unmodifiableLocations;
  }

  public Map<String, Point> getLocationsStaticCopy() {
    return Map.copyOf(locations);
  }

  public Point getLocation(String id) {
    return locations.get(id);
  }

  public void setLocation(String id, int x, int y) {
    if (locations.replace(id, new Point(x, y)) == null) {
      throw new IllegalArgumentException("No such ID: " + id);
    }
  }
}