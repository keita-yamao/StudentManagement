package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.CourseStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.enums.CourseStatusType;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  @DisplayName("受講生の全件検索が行えること")
  void searchStudentTest01() {
    List<Student> actual = sut.searchStudent();
    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  @DisplayName("受講生IDで受講生情報を検索できること")
  void searchStudentByIdTest01() {
    Student actual = sut.searchStudentById("1");
    assertThat(actual.getStudentId()).isEqualTo("1");
  }

  @Test
  @DisplayName("受講生を年齢・論理削除の真偽値で絞り込み検索できること")
  void searchFilterStudentTest01() {
    List<Student> actual = sut.searchFilterStudent(null, null, 20, 29, false);
    assertThat(actual.size()).isEqualTo(3);
  }

  @Test
  @DisplayName("受講生を名前で絞り込み検索できること")
  void searchFilterStudentTest02() {
    List<Student> actual = sut.searchFilterStudent("佐", null, null, null, null);
    assertThat(actual.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("受講生をフリガナで絞り込み検索できること")
  void searchFilterStudentTest03() {
    List<Student> actual = sut.searchFilterStudent(null, "サトウ", null, null, null);
    assertThat(actual.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("指定がなかった場合に全件検索できること")
  void searchFilterStudentTest04() {
    List<Student> actual = sut.searchFilterStudent(null, null, null, null, null);
    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  @DisplayName("コースの全件検索ができること")
  void searchCoursesTest01() {
    List<Course> actual = sut.searchCourses();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  @DisplayName("受講コース情報の全件検索ができること")
  void searchStudentsCoursesTest01() {
    List<StudentsCourses> actual = sut.searchStudentsCourses();
    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  @DisplayName("受講コース情報をコースIDで絞り込み検索できること")
  void searchFilterStudentsCoursesTest01() {
    List<StudentsCourses> actual = sut.searchFilterStudentsCourses("00001");
    assertThat(actual.size()).isEqualTo(3);
  }

  @Test
  @DisplayName("受講生情報の追加ができること")
  void insertStudentTest() {
    //テスト用データのセット
    Student student = createSampleStudent();
    //実行
    sut.insertStudent(student);
    //検証
    List<Student> actual = sut.searchStudent();
    assertThat(actual.size()).isEqualTo(7);
    assertThat(actual.getLast().equals(student)).isTrue();
  }

  @Test
  @DisplayName("受講情報の追加ができること")
  void insertStudentCourseTest01() {
    //テスト用データのセット
    StudentsCourses studentsCourses = createNewStudentsCourses();
    //実行
    sut.insertStudentCourse(studentsCourses);
    //検証
    List<StudentsCourses> actual = sut.searchStudentsCourses();
    assertThat(actual.size()).isEqualTo(7);
  }

  @Test
  @DisplayName("受講生情報の更新ができること")
  void updateStudentTest01() {
    //テスト用データのセット
    Student student = createSampleUpdateStudent();
    //実行
    sut.updateStudent(student);
    //検証
    Student actual = sut.searchStudentById("1");
    assertThat(actual.equals(student)).isTrue();
  }

  @Test
  @DisplayName("受講情報の更新ができること")
  public void updateStudentCoursesTest01() {
    //テスト用データのセット
    StudentsCourses studentsCourses = createUpdateStudentsCourses();
    //実行
    sut.updateStudentCourses(studentsCourses);
    //検証
    List<StudentsCourses> actual = sut.searchStudentsCourses();
    assertThat(actual.get(0).getCourseId()).isEqualTo("00002");
  }

  @Test
  @DisplayName("受講状態の全件検索ができること")
  void searchCourseStatusTest01() {
    List<CourseStatus> actual = sut.searchCourseStatuses();
    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  @DisplayName("受講状態が登録できること")
  void insertCourseStatusTest01() {
    //テスト用データのセット
    CourseStatus courseStatus = createNewCourseStatuses();
    //実行
    sut.insertCourseStatus(courseStatus);
    //検証
    List<CourseStatus> actual = sut.searchCourseStatuses();
    assertThat(actual.size()).isEqualTo(7);
  }

  @Test
  @DisplayName("受講状態が更新できること")
  void updateCourseStatusesTest01() {
    //テスト用データのセット
    CourseStatus courseStatus = new CourseStatus(3, CourseStatusType.ENROLLED.getLabel());
    //実行
    sut.updateCourseStatuses(courseStatus);
    //検証
    List<CourseStatus> actual = sut.searchCourseStatuses();
    assertThat(actual.get(2).getStatus()).isEqualTo(CourseStatusType.ENROLLED.getLabel());
  }

  /*
   * テスト用データ
   * */
  /*新規受講生情報*/
  private static Student createSampleStudent() {
    //受講生情報
    Student student = new Student(
        "7",
        "東京太郎",
        "トウキョウタロウ",
        "キョウタロウ",
        "kyotaro@exampl.com",
        "東京",
        20,
        "男性",
        null,
        false
    );

    return student;
  }

  /*更新用受講生情報*/
  private static Student createSampleUpdateStudent() {
    //受講生情報
    Student student = new Student(
        "1",
        "山本太郎",
        "ヤマモトタロウ",
        "タロ",
        "taro@exampl.com",
        "大阪",
        25,
        "男性",
        null,
        false
    );

    return student;
  }

  /*新規受講情報*/
  private static StudentsCourses createNewStudentsCourses() {
    //受講情報
    StudentsCourses studentsCourses = StudentsCourses.builder()
        .studentId("7")
        .courseId("00001")
        .startDate(Date.valueOf("2023-09-01"))
        .expectedCompletionDate(Date.valueOf("2024-09-01"))
        .build();

    return studentsCourses;
  }

  /*更新用受講情報*/
  private static StudentsCourses createUpdateStudentsCourses() {

    //受講情報
    StudentsCourses studentsCourses = StudentsCourses.builder()
        .id(1)
        .studentId("1")
        .courseId("00002")
        .startDate(Date.valueOf("2023-09-01"))
        .expectedCompletionDate(Date.valueOf("2024-09-01"))
        .build();

    return studentsCourses;
  }

  /*新規登録時の受講状況*/
  private static CourseStatus createNewCourseStatuses() {
    return new CourseStatus(1, "仮申込");
  }
}
