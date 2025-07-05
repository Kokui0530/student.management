package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;


@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  //not nullの名前、フリガナ、mailだけ登録
  @Insert("INSERT INTO students(name,furigana,mail) ,VALUES(#{name},#{furigana},#{mail})")
  void insert(Student student);
}
