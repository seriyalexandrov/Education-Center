package com.alexandrov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

        jdbcTemplate.execute("DROP TABLE persons IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE persons(id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        List<Object[]> persons = new ArrayList<>();
        persons.add(new Object[]{"Josh", "Bloch"});
        persons.add(new Object[]{"Antonio", "Goncalves"});
        persons.add(new Object[]{"Brian", "Goetz"});
        persons.forEach(name -> System.out.println(String.format("Insert: %s %s", name[0], name[1])));

        jdbcTemplate.batchUpdate(
                "INSERT INTO persons(first_name, last_name) VALUES (?,?)",
                persons);

        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM persons WHERE first_name = ?",
                new Object[] { "Josh" },
                new RowMapper<Person>() {
                    @Override
                    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Person(
                                rs.getLong("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"));
                    }
                }
        ).forEach(person -> System.out.println(person.toString()));

        jdbcTemplate.query(
                "SELECT * FROM persons",
                new Object[]{},
                new ResultSetExtractor<List<Person>>() {
                    @Override
                    public List<Person> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                        ArrayList<Person> persons = new ArrayList<>();

                        while (resultSet.next()) {
                            persons.add(new Person(
                                    resultSet.getLong("id"),
                                    resultSet.getString("first_name"),
                                    resultSet.getString("last_name")
                            ));
                        }
                        return persons;
                    }
                }
        ).forEach(person -> System.out.println(person.toString()));
    }

    private class Person {

	    private long id;
	    private String first_name;
	    private String last_name;

        public Person(long id, String first_name, String last_name) {
            this.id = id;
            this.first_name = first_name;
            this.last_name = last_name;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Person{");
            sb.append("id=").append(id);
            sb.append(", first_name='").append(first_name).append('\'');
            sb.append(", last_name='").append(last_name).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
