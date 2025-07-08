package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;


@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  //studentの後に登録するカラム名を入れないと、登録されてるカラム数とVALUESの数が一致していないとエラーになる
  //（テーブル名だけだと全件分という意味になる）
  @Insert(
      "INSERT INTO students(name , furigana , nickname , mail , region , age,gender,remark,isdeleted) "
          + "VALUES(#{name}, #{furigana}, #{nickname}, #{mail}, #{region}, #{age}, #{gender}, #{remark},false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  //自動採番がIDだよって指定

  void registerStudent(Student student);

  @Insert(
      "INSERT INTO students_courses(students_Id ,courses_Name , start , end)" //SQLの登録と同じ名前にしないとNG
          + "VALUES(#{studentsId} , #{coursesName} , #{start} , #{end})")  //ここで使う名前を記載してOK
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentsCourses(StudentsCourses studentsCourses);

}
