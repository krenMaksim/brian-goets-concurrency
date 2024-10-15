package brian.goets.chapter4.listing4_2;

import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

@ThreadSafe
class PersonSet {

  private final Set<Person> mySet = new HashSet<>();

  public synchronized void addPerson(Person p) {
    mySet.add(p);
  }

  public synchronized boolean containsPerson(Person p) {
    return mySet.contains(p);
  }

  static class Person {
  }
}