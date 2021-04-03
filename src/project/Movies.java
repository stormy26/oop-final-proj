package project;

public class Movies {
    private String id;
    private String movieTitle;
    private String movieDirector;
    private String movieActor;
    private int movieYear;
    private String movieGenre;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return movieTitle;
    }
    public void setTitle(String title) {
        this.movieTitle = title;
    }
    public String getMovieDirector() {
        return movieDirector;
    }
    public void setMovieDirector(String director) {
        this.movieDirector = director;
    }
    public String getMovieActor() {
        return movieActor;
    }
    public void setMovieActor(String actor) {
        this.movieActor = actor;
    }
    public int getMovieYear() {
        return movieYear;
    }
    public void setMovieYear(int year) {
        this.movieYear = year;
    }
    public String getMovieGenre() {
        return movieGenre;
    }
    public void setMovieGenre(String genre) {
        this.movieGenre = genre;
    }
}
