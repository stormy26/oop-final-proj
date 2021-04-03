package project;

public class Movies {
    private int id;
    private String movieTitle;
    private String movieDirector;
    private String movieActor;
    private String movieYear;
    private String movieGenre;

    public int getId() {
        return id;
    }
    public void setId(int id) {
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
    public String getMovieYear() {
        return movieYear;
    }
    public void setMovieYear(String year) {
        this.movieYear = year;
    }
    public String getMovieGenre() {
        return movieGenre;
    }
    public void setMovieGenre(String genre) {
        this.movieGenre = genre;
    }
}
