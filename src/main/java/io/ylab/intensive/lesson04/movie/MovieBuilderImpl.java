package io.ylab.intensive.lesson04.movie;

public class MovieBuilderImpl implements MovieBuilder {
    private Integer year;
    private Integer length;
    private String title;
    private String subject;
    private String actors;
    private String actress;
    private String director;
    private Integer popularity;
    private Boolean awards;

    public MovieBuilderImpl() {
        super();
    }

    @Override
    public MovieBuilder withYear(Integer year) {
        this.year = year;
        return this;
    }

    @Override
    public MovieBuilder withLength(Integer length) {
        this.length = length;
        return this;
    }

    @Override
    public MovieBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public MovieBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    @Override
    public MovieBuilder withActors(String actors) {
        this.actors = actors;
        return this;
    }

    @Override
    public MovieBuilder withActress(String actress) {
        this.actress = actress;
        return this;
    }

    @Override
    public MovieBuilder withDirector(String director) {
        this.director = director;
        return this;
    }

    @Override
    public MovieBuilder withPopularity(Integer popularity) {
        this.popularity = popularity;
        return this;
    }

    @Override
    public MovieBuilder withAwards(Boolean awards) {
        this.awards = awards;
        return this;
    }

    @Override
    public Movie build() {
        Movie movie = new Movie();
        movie.setYear(this.year);
        movie.setLength(this.length);
        movie.setTitle(this.title);
        movie.setSubject(this.subject);
        movie.setActors(this.actors);
        movie.setActress(this.actress);
        movie.setDirector(this.director);
        movie.setPopularity(this.popularity);
        movie.setAwards(this.awards);
        return movie;
    }
}
