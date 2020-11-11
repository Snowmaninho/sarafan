package letscode.sarafan.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "usr")
@lombok.Data // автоматическая генерация геттеров, сеттеров, equals, hashcode и др.
public class User {
    @Id // автогенерации id здесь не будет, т.к. id будет приходить с Google'а
    private String id;
    private String name;
    private String userpic;
    private String email;
    private String gender;
    private String locale;
    private LocalDateTime lastVisit;

}
