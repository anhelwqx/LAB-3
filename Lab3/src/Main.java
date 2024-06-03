import java.util.*;
import java.util.stream.Collectors;

class Movie {
    private String title;
    private List<Actor> actors;

    public Movie(String title, List<Actor> actors) {
        this.title = title;
        this.actors = actors;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public String getTitle() {
        return title;
    }
}

class Actor {
    private String name;
    private List<Movie> movies;

    public Actor(String name, List<Movie> movies) {
        this.name = name;
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public String getName() {
        return name;
    }
}

class CinemaDatabase {
    private List<Movie> movies;
    private List<Actor> actors;

    public CinemaDatabase(List<Movie> movies, List<Actor> actors) {
        this.movies = movies;
        this.actors = actors;
    }

    public boolean hasActorNeverPlayed() {
        return actors.stream().anyMatch(actor -> actor.getMovies().isEmpty());
    }

    public List<String> findActorsWithCoActor(String actorName) {
        return actors.stream()
                .filter(actor -> !actor.getName().equals(actorName)) // Виключаємо актора зі списку результатів
                .filter(actor -> actor.getMovies()
                        .stream()
                        .anyMatch(movie -> movie.getActors()
                                .stream()
                                .anyMatch(a -> a.getName().equals(actorName))))
                .map(Actor::getName)
                .collect(Collectors.toList());
    }

    public Movie findMovieWithMostActors() {
        return movies.stream()
                .max(Comparator.comparingInt(movie -> movie.getActors().size()))
                .orElse(null);
    }
}

public class Main {
    public static void main(String[] args) {
        // Creating actors
        Actor actor1 = new Actor("Actor 1", new ArrayList<>());
        Actor actor2 = new Actor("Actor 2", new ArrayList<>());
        Actor actor3 = new Actor("Actor 3", new ArrayList<>());
        Actor actor4 = new Actor("Actor 4", new ArrayList<>());

        // Creating movies
        Movie movie1 = new Movie("Movie 1", Arrays.asList(actor1, actor2, actor4));
        Movie movie2 = new Movie("Movie 2", Arrays.asList(actor2, actor3));
        Movie movie3 = new Movie("Movie 3", Arrays.asList(actor1, actor3));

        // Adding movies to actors
        actor1.getMovies().addAll(Arrays.asList(movie1, movie3));
        actor2.getMovies().addAll(Arrays.asList(movie1, movie2));
        actor3.getMovies().addAll(Arrays.asList(movie2, movie3));
        actor4.getMovies().addAll(Arrays.asList(movie1));

        // Creating cinema database
        CinemaDatabase cinemaDatabase = new CinemaDatabase(Arrays.asList(movie1, movie2, movie3),
                Arrays.asList(actor1, actor2, actor3, actor4));

        // Task 1: Check if there is an actor who never played
        System.out.println("Actor who never played: " + cinemaDatabase.hasActorNeverPlayed());

        // Task 2: Find actors who acted with a given actor
        System.out.println("Actors who acted with Actor 1: " + cinemaDatabase.findActorsWithCoActor("Actor 1"));

        // Task 3: Find movie with the most actors
        System.out.println("Movie with the most actors: " + cinemaDatabase.findMovieWithMostActors().getTitle());
    }
}
