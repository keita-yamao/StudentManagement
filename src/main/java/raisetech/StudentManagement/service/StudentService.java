package raisetech.StudentManagement.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domein.CourseDetail;
import raisetech.StudentManagement.domein.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービス 受講生の検索・登録・削除を行う
 */
@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentConverter converter;

  /**
   * コンストラクタ
   *
   * @param repository 受講生情報を管理するリポジトリ
   */
  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細情報一覧を全件検索。
   *
   * @return 受講生詳細情報一覧(全件)
   */
  public List<StudentDetail> searchStudentDetailList() {
    //テーブルデータを各オブジェクトに格納
    List<Student> students = repository.searchStudent();
    List<Course> courses = repository.searchCourses();
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourses();
    //削除フラグの立った生徒情報を省く
    students = students.stream()
        .filter(student -> !student.isDeleted())
        .collect(Collectors.toList());
    //受講情報一覧とコース情報一覧のコンバート処理
    List<CourseDetail> courseDetails = converter.courseDetails(studentsCourses, courses);
    //受講生情報一覧と受講コース情報一覧をコンバート処理してリターン
    return converter.studentDetails(students, courseDetails);
  }

  /**
   * 受講生詳細情報を受講生IDで検索
   *
   * @param studentId 受講生ID
   * @return 受講生詳細情報(1件)
   */
  public StudentDetail searchStudentDetailById(String studentId) {
    //テーブルデータを各オブジェクトに格納
    Student student = repository.searchStudentById(studentId);
    List<Course> courses = repository.searchCourses();
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourses();
    //受講情報一覧とコース情報一覧のコンバート処理
    List<CourseDetail> courseDetails = converter.courseDetails(studentsCourses, courses);
    //受講生情報一覧と受講コース情報一覧をコンバート処理してリターン
    return converter.studentDetail(student, courseDetails);
  }

  /**
   * 受講生詳細情報一覧をフィルタリング(削除済み・年齢・受講コース)して表示する
   *
   * @param minAge    検索年齢の最小値
   * @param maxAge    検索年齢の最大値
   * @param isDeleted 論理削除の真偽値
   * @param courseId  コースID
   * @return 受講生詳細情報一覧(条件に一致するもの)
   */
  public List<StudentDetail> searchFilterStudentDetailList(Integer minAge, Integer maxAge,
      Boolean isDeleted, String courseId) {
    //テーブルデータを各オブジェクトに格納
    List<Student> students = repository.searchFilterStudent(minAge, maxAge, isDeleted);
    List<Course> courses = repository.searchCourses();
    List<StudentsCourses> studentsCourses = repository.searchFilterStudentsCourses(courseId);
    //受講情報一覧とコース情報一覧のコンバート処理
    List<CourseDetail> courseDetails = converter.courseDetails(studentsCourses, courses);
    //受講生情報一覧と受講コース情報一覧をコンバート処理してリターン
    return converter.studentDetails(students, courseDetails);
  }

  /**
   * 受講生IDが一致する受講生情報を検索する
   *
   * @param studentId 　受講生ID
   * @return 受講生情報
   */
  public Student searchStudentById(String studentId) {
    List<Student> studentList = repository.searchStudent();
    return studentList.stream()
        .filter(student -> student.getStudentId().equals(studentId))
        .findFirst().orElseGet(Student::new);
  }

  /**
   * コース情報一覧を全件検索
   *
   * @return コース情報一覧(全件)
   */
  public List<Course> searchCourseList() {
    return repository.searchCourses();
  }

  /**
   * 新規受講生の受講生詳細情報を受け取り 受講生情報と受講情報を登録する
   *
   * @param studentDetail 受講生詳細情報
   * @return 受講生詳細情報
   */
  @Transactional
  public StudentDetail addStudent(StudentDetail studentDetail) {
    //受講生情報と受講情報の両方で使用するもの
    //受講生IDの決定
    List<Student> studentList = repository.searchStudent();
    String studentId = Integer.toString(studentList.size() + 1);

    //受講生情報追加に関する処理
    //引数のオブジェクトからデータをset
    Student student = studentDetail.getStudent();
    //受講生IDのset
    student.setStudentId(studentId);
    //論理削除フラグをset
    student.undelete();
    //studentsテーブルに追加
    repository.insertStudent(student);

    //受講情報の追加に関する処理
    List<CourseDetail> courseDetails = studentDetail.getCourseDetail();
    //日付の取得
    LocalDate startDate = LocalDate.now();
    //終了日の計算
    LocalDate expectedCompletionDate = startDate.plusYears(1);
    for (CourseDetail courseDetail : courseDetails) {
      //引数のオブジェクトからデータをset
      StudentsCourses studentsCourses = courseDetail.getStudentsCourses();
      //受講生IDをset
      studentsCourses.setStudentId(studentId);
      //開始日をset
      studentsCourses.setStartDate(Date.valueOf(startDate));
      //終了日をset
      studentsCourses.setExpectedCompletionDate(Date.valueOf(expectedCompletionDate));
      //students_coursesテーブルに追加
      repository.insertStudentCourse(studentsCourses);
    }
    return studentDetail;
  }

  /**
   * 受講生詳細情報を受け取り、受講生情報と受講情報の更新処理を行う。
   *
   * @param studentDetail 受講生詳細情報
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    //引数から受講生情報をオブジェクトにセット
    Student student = studentDetail.getStudent();
    //受講生情報の更新処理
    repository.updateStudent(student);
    //引数から受講コース情報をオブジェクトにセット
    List<CourseDetail> courseDetails = studentDetail.getCourseDetail();
    //受講コース情報を元に受講情報を更新する処理
    for (CourseDetail courseDetail : courseDetails) {
      StudentsCourses studentsCourses = courseDetail.getStudentsCourses();
      repository.updateStudentCourses(studentsCourses);
    }
  }

  /**
   * 受講生情報の論理削除を行う
   *
   * @param student 受講生情報
   */
  @Transactional
  public void deleteStudent(Student student) {
    //削除フラグを立てる
    student.delete();
    //受講生情報の削除処理
    repository.updateStudent(student);
  }
}
