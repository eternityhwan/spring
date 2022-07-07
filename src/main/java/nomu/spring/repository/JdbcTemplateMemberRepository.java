package nomu.spring.repository;

import nomu.spring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate; // 스프링 프레임워크의 JdbcTemplate 클래스를 객체로 만들어 필드선언한다

    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) { // DataSource 클래스에서 dataSource 객체를 만들어
        jdbcTemplate = new JdbcTemplate(dataSource); // jdbcTemplate 객체가 JdbcTemplate를 쓰는거지(데이터 소스를 바탕으로)
    }
    // 생성자가 하나면 @Autowired 생략 가능해

    @Override
    public Member save(Member member) {
        // SimpleJdbcInsert
        // 테이블이름과, usingGenerateKeyColumns 메서드를 사용하면 바로 생성된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }
    // 아래에 로우매퍼로 선언했잖아, sql 쿼리를 코드에서 사용할 수 있는거야.
    // 코드를 최소화했다.

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }   // stream은 저장요소를 람다식으로 처리할 수 있게 해주는 반복식

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    @Override
    public void clearStore() {

    }

    private RowMapper<Member> memberRowMapper() {
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {

                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName((rs.getString("name")));
                return member;
            }
        };
    }
}
