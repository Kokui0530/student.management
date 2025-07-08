package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  //studentsのリスト表示
  @Select("SELECT * FROM students")
  List<Student> search();

  //students単一表示
  @Select("SELECT * FROM students WHERE id= #{id}")
  Student searchStudent(String id);

  //students_coursesのリスト表示
  @Select("SELECT * FROM students_courses")
  List<Student> searchStudentsCoursesList();

  //students_coursesの単一表示
  @Select("SELECT * FROM students_courses WHERE students_id= #{studentId}")
  List<StudentsCourses> searchStudentsCourses(String studentId);

  //退会した受講生以外を表示
  @Select("SELECT * FROM students WHERE isDeleted = FALSE")
  List<Student> nowSearch();


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

  //受講生登録情報の更新
  @Update(
      "UPDATE students SET name=#{name} , furigana=#{furigana} , nickname=#{nickname} , mail=#{mail} , region=#{region},"
          + "age=#{age},gender=#{gender},remark=#{remark},isdeleted=#{isDeleted} WHERE id=#{id}")
  void updateStudent(Student student);

  //コース情報の更新
  @Update(
      "UPDATE students_Courses SET courses_Name = #{coursesName} WHERE id = #{id}")
  void updateStudentCourses(StudentsCourses studentsCourses);

  //退会　論理削除
  @Delete(
      "DELETE FROM students WHERE id = #{id}")
  void deleteStudent(Student student);

  @Delete(
      "DELETE FROM students_courses WHERE id = #{id}")
  void deleteStudentCourses(StudentsCourses studentsCourses);
}
