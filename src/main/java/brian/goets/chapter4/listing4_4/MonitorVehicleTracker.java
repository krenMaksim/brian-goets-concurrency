package brian.goets.chapter4.listing4_4;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.function.Function;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

@ThreadSafe
class MonitorVehicleTracker {

  private final Map<String, MutablePoint> locations;

  public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
    this.locations = deepCopy(locations);
  }

  public synchronized Map<String, MutablePoint> getLocations() {
    return deepCopy(locations);
  }

  public synchronized MutablePoint getLocation(String id) {
    MutablePoint loc = locations.get(id);
    return loc == null ? null : new MutablePoint(loc);
  }

  public synchronized void setLocation(String id, int x, int y) {
    MutablePoint loc = locations.get(id);
    if (loc == null) {
      throw new IllegalArgumentException("No such ID: " + id);
    }
    loc.setX(x);
    loc.setY(y);
  }

  private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> source) {
    Function<String, MutablePoint> newMutablePointCopy = id -> {
      MutablePoint point = source.get(id);
      return new MutablePoint(point);
    };
    return source.keySet()
        .stream()
        .collect(toUnmodifiableMap(identity(), newMutablePointCopy));
  }
}