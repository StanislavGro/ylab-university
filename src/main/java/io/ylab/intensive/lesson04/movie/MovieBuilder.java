package io.ylab.intensive.lesson04.movie;

public interface MovieBuilder {
    MovieBuilder withYear(Integer year);

    MovieBuilder withLength(Integer length);

    MovieBuilder withTitle(String title);

    MovieBuilder withSubject(String subject);

    MovieBuilder withActors(String actors);

    MovieBuilder withActress(String actress);

    MovieBuilder withDirector(String director);

    MovieBuilder withPopularity(Integer popularity);

    MovieBuilder withAwards(Boolean awards);

    Movie build();
}
