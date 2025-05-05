package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

/**
 * 受講生テーブル・受講コース情報テーブル・コース情報テーブル と紐づくリポジトリ。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索。
   *
   * @return 受講生一覧(全件)
   */
  @Select("SELECT * FROM students")
  List<Student> searchStudent();

  /**
   * コース情報の全件検索
   *
   * @return コース情報一覧(全件)
   */
  @Select("SELECT * FROM courses")
  List<Course> searchCourses();

  /**
   * 受講コース情報の全件検索
   *
   * @return 受講コース情報一覧(全件)
   */
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  /**
   * 受講生情報の追加
   *
   * @param student 受講生情報
   */
  @Insert(
      "INSERT INTO students (student_id,name,furigana,nickname,email,address,age,gender,is_deleted) "
          + "VALUES (#{studentId},#{name},#{furigana},#{nickname},#{email},#{address},#{age},#{gender},#{isDeleted})")
  void insertStudent(Student student);

  /**
   * 受講情報の追加
   *
   * @param studentsCourses 受講情報
   */
  @Insert(
      "INSERT INTO students_courses (student_id,course_id,start_date,expected_completion_date) "
          + "VALUES (#{studentId},#{courseId},#{startDate},#{expectedCompletionDate})")
  void insertStudentCourse(StudentsCourses studentsCourses);

  /**
   * 受講生情報の更新
   *
   * @param student 受講生情報
   */
  @Update(
      "UPDATE students SET name=#{name},furigana=#{furigana},nickname=#{nickname},email=#{email}"
          + ",address=#{address},age=#{age},gender=#{gender},is_deleted =#{isDeleted} WHERE student_id=#{studentId}")
  void updateStudent(Student student);

  /**
   * 受講情報の更新
   *
   * @param studentsCourses 受講情報
   */
  @Update("UPDATE students_courses SET course_id=#{courseId} WHERE id=#{id}")
  void updateStudentCourses(StudentsCourses studentsCourses);

}
