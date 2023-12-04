package pl.edu.agh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.model.Title;

public interface TitleRepository extends JpaRepository<Title, Integer> {
}
