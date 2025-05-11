package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
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
  List<Student> searchStudent();

  /**
   * コース情報の全件検索
   *
   * @return コース情報一覧(全件)
   */
  List<Course> searchCourses();

  /**
   * 受講コース情報の全件検索
   *
   * @return 受講コース情報一覧(全件)
   */
  List<StudentsCourses> searchStudentsCourses();

  /**
   * 受講生情報の追加
   *
   * @param student 受講生情報
   */
  void insertStudent(Student student);

  /**
   * 受講情報の追加
   *
   * @param studentsCourses 受講情報
   */
  void insertStudentCourse(StudentsCourses studentsCourses);

  /**
   * 受講生情報の更新
   *
   * @param student 受講生情報
   */
  void updateStudent(Student student);

  /**
   * 受講情報の更新
   *
   * @param studentsCourses 受講情報
   */
  void updateStudentCourses(StudentsCourses studentsCourses);

}
