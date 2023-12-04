package pl.edu.agh.repository.users;

import org.springframework.stereotype.Repository;
import pl.edu.agh.model.users.Member;

@Repository
public interface MemberRepository extends UserRepository<Member> {
}
