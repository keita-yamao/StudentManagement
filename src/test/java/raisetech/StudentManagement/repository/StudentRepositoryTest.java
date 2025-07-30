package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

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
    List<Student> actual = sut.searchFilterStudent(20, 29, false);
    assertThat(actual.size()).isEqualTo(3);
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
    assertThat(actual.get(6).getStudentId()).isEqualTo("7");
    assertThat(actual.get(6).getName()).isEqualTo("東京太郎");
    assertThat(actual.get(6).getFurigana()).isEqualTo("トウキョウタロウ");
    assertThat(actual.get(6).getNickname()).isEqualTo("キョウタロウ");
    assertThat(actual.get(6).getEmail()).isEqualTo("kyotaro@exampl.com");
    assertThat(actual.get(6).getAddress()).isEqualTo("東京");
    assertThat(actual.get(6).getAge()).isEqualTo(20);
    assertThat(actual.get(6).getGender()).isEqualTo("男性");
    assertThat(actual.get(6).isDeleted()).isEqualTo(false);
  }

  @Test
  @DisplayName("受講情報の追加ができること")
  void insertStudentCourseTest01() {
    //テスト用データのセット
    StudentsCourses studentsCourses = createStudentsCourses();
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
    assertThat(actual.getAddress()).isEqualTo("大阪");
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

  /*
   * テスト用データ
   * */
  /*新規受講生情報*/
  private static Student createSampleStudent() {
    //受講生情報
    Student student = new Student();
    student.setStudentId("7");
    student.setName("東京太郎");
    student.setFurigana("トウキョウタロウ");
    student.setNickname("キョウタロウ");
    student.setEmail("kyotaro@exampl.com");
    student.setAddress("東京");
    student.setAge(20);
    student.setGender("男性");
    student.undelete();//削除フラグfalse

    return student;
  }

  /*更新用受講生情報*/
  private static Student createSampleUpdateStudent() {
    //受講生情報
    Student student = new Student();
    student.setStudentId("1");
    student.setName("山本太郎");
    student.setFurigana("ヤマモトタロウ");
    student.setNickname("タロ");
    student.setEmail("taro@exampl.com");
    student.setAddress("大阪");
    student.setAge(25);
    student.setGender("男性");
    student.undelete();//削除フラグfalse

    return student;
  }

  /*新規受講情報*/
  private static StudentsCourses createStudentsCourses() {
    //受講情報
    StudentsCourses studentsCourses = new StudentsCourses();
    studentsCourses.setStudentId("7");
    studentsCourses.setCourseId("00001");
    studentsCourses.setStartDate(Date.valueOf("2023-09-01"));
    studentsCourses.setExpectedCompletionDate(Date.valueOf("2024-09-01"));

    return studentsCourses;
  }

  /*更新用受講情報*/
  private static StudentsCourses createUpdateStudentsCourses() {
    //受講情報
    StudentsCourses studentsCourses = new StudentsCourses();
    studentsCourses.setId(1);
    studentsCourses.setStudentId("1");
    studentsCourses.setCourseId("00002");
    studentsCourses.setStartDate(Date.valueOf("2023-09-01"));
    studentsCourses.setExpectedCompletionDate(Date.valueOf("2024-09-01"));

    return studentsCourses;
  }
}
