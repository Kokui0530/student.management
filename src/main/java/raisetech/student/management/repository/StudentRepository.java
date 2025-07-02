package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;


@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

}
