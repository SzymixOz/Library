package pl.edu.agh.repository;

import org.springframework.stereotype.Repository;
import pl.edu.agh.model.Member;

@Repository
public interface MemberRepository extends UserRepository<Member> {
}
