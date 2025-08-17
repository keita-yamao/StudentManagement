package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.CourseStatus;
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
   * 受講生をstudent_idで検索(論理削除済みを除く)
   *
   * @param studentId 受講生ID
   * @return 受講生情報(1件)
   */
  Student searchStudentById(String studentId);

  /**
   * 受講生を年齢・論理削除の真偽値で絞り込み検索
   *
   * @param minAge    最少年齢
   * @param maxAge    最大年齢
   * @param isDeleted 論理削除の真偽値
   * @return 受講生情報一覧(条件に一致するもの)
   */
  List<Student> searchFilterStudent(String name, String furigana, Integer minAge, Integer maxAge,
      Boolean isDeleted);

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
   * 受講コース情報をコースIDで絞り込み検索
   *
   * @param courseId コースID
   * @return 受講コース情報
   */
  List<StudentsCourses> searchFilterStudentsCourses(String courseId);

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

  /**
   * 受講状態の全件検索
   *
   * @return 受講状態
   */
  List<CourseStatus> searchCourseStatuses();

  /**
   * 受講状態の登録
   *
   * @param courseStatus 　受講状態
   */
  void insertCourseStatus(CourseStatus courseStatus);

  /**
   * 受講状態の更新
   *
   * @param courseStatus 受講状態
   */
  void updateCourseStatuses(CourseStatus courseStatus);

}
