package brian.goets.chapter4.listing4_12;

import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
class PublishingVehicleTracker {

  private final Map<String, SafePoint> locations;
  private final Map<String, SafePoint> unmodifiableLocations;

  public PublishingVehicleTracker(Map<String, SafePoint> locations) {
    this.locations = new ConcurrentHashMap<>(locations);
    this.unmodifiableLocations = Collections.unmodifiableMap(this.locations);
  }

  public Map<String, SafePoint> getLocations() {
    return unmodifiableLocations;
  }

  public SafePoint getLocation(String id) {
    return locations.get(id);
  }

  public void setLocation(String id, int x, int y) {
    if (!locations.containsKey(id)) {
      throw new IllegalArgumentException("No such ID: " + id);
    }
    locations.get(id).set(x, y);
  }
}
