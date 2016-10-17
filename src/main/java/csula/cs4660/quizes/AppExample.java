package csula.cs4660.quizes;

import com.google.common.collect.Lists;
import csula.cs4660.quizes.models.State;

import java.util.*;

/**
 * Here is your quiz entry point and your app
 */
public class AppExample {
    public static void main(String[] args) {
        // to get a state, you can simply call `Client.getState with the id`
        State initialState = Client.getState("10a5461773e8fd60940a56d2e9ef7bf4").get();
        //System.out.println(initialState);
        // to get an edge between state to its neighbor, you can call stateTransition
        //System.out.println(Client.stateTransition(initialState.getId(), initialState.getNeighbors()[0].getId()));

        Queue<State> frontier = new LinkedList<>();
        Set<State> exploredSet = new HashSet<>();
        Map<State, State> parents = new HashMap<>();
        frontier.add(initialState);

        while (!frontier.isEmpty()) {
            State current = frontier.poll();
            exploredSet.add(current);

            // for every possible action
            for (State neighbor: Client.getState(current.getId()).get().getNeighbors()) {
                // state transition
                if (neighbor.getId().equals("e577aa79473673f6158cc73e0e5dc122")) {
                    // construct actions from endTile

                    for(String s: findDepth(parents, neighbor)) {
                        System.out.println(s);
                    }
                    System.out.println();
                }
                if (!exploredSet.contains(neighbor)) {
                    parents.put(neighbor, current);
                    frontier.add(neighbor);
                }
            }
        }

        System.out.println("End");
    }

    public static List<String> findDepth(Map<State, State> parents, State current) {

        State child = current;
        State parent = parents.get(child);

        int depth = 0;
        int cost = 0;

        List<String> strings = new ArrayList<>();

        strings.add("depth: " + depth);

        while (parent != null) {
            depth ++;

            child = Client.getState(child.getId()).get();
            parent = Client.getState(parent.getId()).get();

            cost = Client.stateTransition(parent.getId(), child.getId()).get().getEvent().getEffect();

            strings.add(parent.getLocation().getName() + " : " + child.getLocation().getName() +
                    " : " + cost);

            child = parent;
            parent = parents.get(child);
        }

        strings.set(0, "depth: " + depth);

        return Lists.reverse(strings);
    }
}