package raisetech.StudentManagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.StudentManagement.controller.StudentController;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class ValidationTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  private StudentService studentService;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  //Controllerのバリデーションテスト
  /*
   *getStudentDetailByIdメソッドのバリエーションテスト
   */
  @Test
  public void 単一の受講生詳細情報の検索_受講生IDの入力値上限オーバーかつスペースが含まれている()
      throws Exception {
    //実行時に桁数オーバーの場合の例外がServletExceptionであることを検証し取得
    Exception exception = assertThrows(ServletException.class, () -> {
      mockMvc.perform(MockMvcRequestBuilders.get("/students/01234 56789"))
          .andReturn();
    });
    //ServletExceptionにラップされた入力チェック時の例外がConstraintViolationExceptionであることを確認し取得
    ConstraintViolationException constraintViolationException = assertInstanceOf(
        ConstraintViolationException.class, exception.getCause());
    // violations からメッセージ部分だけを抜き出す
    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
    List<String> messages = new ArrayList<>();
    for (ConstraintViolation<?> violation : violations) {
      messages.add(violation.getMessage());
    }
    //検証
    assertThat(messages).isEqualTo(
        List.of("入力できる値は10文字までです", "半角スペースと全角スペースは使用できません"));
  }

  /*
   *getFilteredStudentDetailListメソッドのバリエーションテスト
   */
  @Test
  public void 受講生詳細情報の絞り込み検索_年齢の最小値が0以下() throws Exception {
    //実行時に不正な値の入力だった場合の例外を検証し取得
    Exception exception = assertThrows(ServletException.class, () -> {
      mockMvc.perform(MockMvcRequestBuilders.get("/students")
          .param("minAge", String.valueOf(-99))
          .param("maxAge", String.valueOf(120))
          .param("isDeleted", String.valueOf(false))
          .param("courseId", "00001")
      ).andReturn();
    });
    //ServletExceptionにラップされた入力チェック時の例外がConstraintViolationExceptionであることを確認し取得
    ConstraintViolationException constraintViolationException = assertInstanceOf(
        ConstraintViolationException.class, exception.getCause());
    // violation からメッセージ部分だけを抜き出す
    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
    String message = violations.iterator().next().getMessage();
    //検証
    assertThat(message).contains("年齢の最小値は0です");
  }

  @Test
  public void 受講生詳細情報の絞り込み検索_年齢の最大値が120以上() throws Exception {
    //実行時に不正な値の入力だった場合の例外を検証し取得
    Exception exception = assertThrows(ServletException.class, () -> {
      mockMvc.perform(MockMvcRequestBuilders.get("/students")
          .param("minAge", String.valueOf(0))
          .param("maxAge", String.valueOf(999))
          .param("isDeleted", String.valueOf(false))
          .param("courseId", "00001")
      ).andReturn();
    });
    //ServletExceptionにラップされた入力チェック時の例外がConstraintViolationExceptionであることを確認し取得
    ConstraintViolationException constraintViolationException = assertInstanceOf(
        ConstraintViolationException.class, exception.getCause());
    // violation からメッセージ部分だけを抜き出す
    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
    String message = violations.iterator().next().getMessage();
    //検証
    assertThat(message).contains("年齢の最大値は120です");
  }

  @Test
  public void 受講生詳細情報の絞り込み検索_コースIDが5文字以上() throws Exception {
    //実行時に不正な値の入力だった場合の例外を検証し取得
    Exception exception = assertThrows(ServletException.class, () -> {
      mockMvc.perform(MockMvcRequestBuilders.get("/students")
          .param("minAge", String.valueOf(0))
          .param("maxAge", String.valueOf(120))
          .param("isDeleted", String.valueOf(false))
          .param("courseId", "123456")
      ).andReturn();
    });
    //ServletExceptionにラップされた入力チェック時の例外がConstraintViolationExceptionであることを確認し取得
    ConstraintViolationException constraintViolationException = assertInstanceOf(
        ConstraintViolationException.class, exception.getCause());
    // violation からメッセージ部分だけを抜き出す
    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
    String message = violations.iterator().next().getMessage();
    //検証
    assertThat(message).contains("コースIDは5文字で入力してください");
  }

  @Test
  public void 受講生詳細情報の絞り込み検索_コースIDが5文字以下() throws Exception {
    //実行時に不正な値の入力だった場合の例外を検証し取得
    Exception exception = assertThrows(ServletException.class, () -> {
      mockMvc.perform(MockMvcRequestBuilders.get("/students")
          .param("minAge", String.valueOf(0))
          .param("maxAge", String.valueOf(120))
          .param("isDeleted", String.valueOf(false))
          .param("courseId", "1")
      ).andReturn();
    });
    //ServletExceptionにラップされた入力チェック時の例外がConstraintViolationExceptionであることを確認し取得
    ConstraintViolationException constraintViolationException = assertInstanceOf(
        ConstraintViolationException.class, exception.getCause());
    // violation からメッセージ部分だけを抜き出す
    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
    String message = violations.iterator().next().getMessage();
    //検証
    assertThat(message).contains("コースIDは5文字で入力してください");
  }

  /*
   *deleteStudentメソッドのバリデーションテスト
   */
  @Test
  public void 受講生一覧の削除処理_受講生IDの入力値上限オーバー() throws Exception {
    //実行時に不正な値の入力だった場合の例外を検証し取得
    Exception exception = assertThrows(ServletException.class, () -> {
      mockMvc.perform(MockMvcRequestBuilders.post("/deleteStudent")
          .contentType(MediaType.APPLICATION_JSON)
          .content("12345678910")
      ).andReturn();
    });
    //ServletExceptionにラップされた入力チェック時の例外がConstraintViolationExceptionであることを確認し取得
    ConstraintViolationException constraintViolationException = assertInstanceOf(
        ConstraintViolationException.class, exception.getCause());
    // violation からメッセージ部分だけを抜き出す
    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
    String message = violations.iterator().next().getMessage();
    //検証
    assertThat(message).contains("入力できる受講生IDは10文字までです");
  }

  @Test
  public void 受講生一覧の削除処理_受講生IDの入力値にスペースが含まれる() throws Exception {
    //実行時に不正な値の入力だった場合の例外を検証し取得
    Exception exception = assertThrows(ServletException.class, () -> {
      mockMvc.perform(MockMvcRequestBuilders.post("/deleteStudent")
          .contentType(MediaType.APPLICATION_JSON)
          .content("12 45")
      ).andReturn();
    });
    //ServletExceptionにラップされた入力チェック時の例外がConstraintViolationExceptionであることを確認し取得
    ConstraintViolationException constraintViolationException = assertInstanceOf(
        ConstraintViolationException.class, exception.getCause());
    // violation からメッセージ部分だけを抜き出す
    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
    String message = violations.iterator().next().getMessage();
    //検証
    assertThat(message).contains("半角スペースと全角スペースは使用できません");
  }

  //DTOクラスのバリデーションテスト

  /*
   *Studentクラスのバリデーションテスト
   */
  @Test
  public void Studnet_受講生IDが10文字以上() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setStudentId("12345678910");

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("受講生IDは10桁までです");
  }

  @Test
  public void Student_受講生ID入力されていない() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setStudentId("");

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("受講生IDが入力されていません");
  }

  @Test
  public void Studnet_受講生IDにスペースが含まれている() {
    //DTOにデータを格納
    //受講生情報
    Student student = getStudent();
    student.setStudentId("12 45");

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("半角スペースと全角スペースは使用できません");
  }

  @Test
  public void Student_名前が60文字以上() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setName("あいうえお".repeat(13));

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("名前は60文字までです");
  }

  @Test
  public void Studnet_名前が未入力() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setName("");

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("名前が入力されていません");
  }

  @Test
  public void Student_フリガナが60文字以上() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setFurigana("アイウエオ".repeat(13));

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("フリガナは60文字までです");
  }

  @Test
  public void Student_フリガナが未入力() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setFurigana("");

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("フリガナが入力されていません");
  }

  @Test
  public void Student_ニックネームが60文字以上() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setNickname("アイウエオ".repeat(13));

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("ニックネームは60文字までです");
  }

  @Test
  public void Student_ニックネームが未入力() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setNickname("");

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("ニックネームが入力されていません");
  }

  @Test
  public void Student_無効な形式のメールアドレス() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setEmail("無効なメールアドレス");

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("無効な形式のメールアドレスです");
  }

  @Test
  public void Student_メールアドレスが未入力() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setEmail(null);

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("メールアドレスが入力されていません");
  }

  @Test
  public void Student_住所が161文字以上() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setAddress("0123456789".repeat(17));

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("住所は161文字までです");
  }

  @Test
  public void Student_年齢の最小値が0以下() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setAge(-1);

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("最少年齢は0才からです");
  }

  @Test
  public void Student_年齢の最大値が120以上() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setAge(121);

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("最大年齢は120才までです");
  }

  @Test
  public void Student_性別が20文字以上() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setGender("あいうえお".repeat(5));

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("20文字までです");
  }

  @Test
  public void Student_備考が255文字以上() {
    //DTOにデータを格納
    Student student = getStudent();
    student.setRemark("あいうえお".repeat(52));

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("最大文字数255文字まで");
  }

  /*
   *StudentsCoursesクラスのバリデーションテスト
   */
  @Test
  public void StudentsCourses_受講生IDが10桁以上() {
    //DTOにデータを格納
    StudentsCourses studentsCourses = getSampleStudentsCourse();
    studentsCourses.setStudentId("12345".repeat(3));

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<StudentsCourses>> violations = validator.validate(studentsCourses);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("受講生IDは10桁までです");
  }

  @Test
  public void StudentCourses_受講生IDが未入力() {
    //DTOにデータを格納
    StudentsCourses studentsCourses = getSampleStudentsCourse();
    studentsCourses.setStudentId("");

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<StudentsCourses>> violations = validator.validate(studentsCourses);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("受講生IDが入力されていません");
  }

  @Test
  public void StudentCourses_受講生IDにスペースが含まれている() {
    //DTOにデータを格納
    StudentsCourses studentsCourses = getSampleStudentsCourse();
    studentsCourses.setStudentId("12 34");

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<StudentsCourses>> violations = validator.validate(studentsCourses);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("半角スペースと全角スペースは使用できません");
  }

  @Test
  public void StudentCourses_コースIDが未入力の場合() {
    //DTOにデータを格納
    StudentsCourses studentsCourses = getSampleStudentsCourse();
    studentsCourses.setCourseId("");

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<StudentsCourses>> violations = validator.validate(studentsCourses);

    //検証
    assertThat(violations.size()).isEqualTo(2);
    assertThat(violations).extracting("message")
        .containsOnly("コースIDは5文字で入力してください", "コースIDが入力されていません");
  }

  @Test
  public void StudentsCourses_コースIDにスペースが含まれている() {
    //DTOにデータを格納
    StudentsCourses studentsCourses = getSampleStudentsCourse();
    studentsCourses.setCourseId("12 34");

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<StudentsCourses>> violations = validator.validate(studentsCourses);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("半角スペースと全角スペースは使用できません");
  }

  @Test
  public void StudentsCourses_開始日付がnull() {
    //DTOにデータを格納
    StudentsCourses studentsCourses = getSampleStudentsCourse();
    studentsCourses.setStartDate(null);

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<StudentsCourses>> violations = validator.validate(studentsCourses);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("開始日付が未入力です");
  }

  @Test
  public void StudentsCourses_修了日付がnull() {
    //DTOにデータを格納
    StudentsCourses studentsCourses = getSampleStudentsCourse();
    studentsCourses.setExpectedCompletionDate(null);

    //バリエーションチェックの結果を取得
    Set<ConstraintViolation<StudentsCourses>> violations = validator.validate(studentsCourses);

    //検証
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("修了日付が未入力です");
  }

  /*
   * テスト用データ
   * */
  /*受講生情報*/
  private static Student getStudent() {
    //受講生情報
    Student student = new Student(
        "1",
        "山本太郎",
        "ヤマモトタロウ",
        "タロ",
        "taro@exampl.com",
        "東京",
        25,
        "男性",
        null,
        false
    );

    return student;
  }

  /*受講情報*/
  private static StudentsCourses getSampleStudentsCourse() {
    StudentsCourses studentsCourses = StudentsCourses.builder()
        .id(1)
        .studentId("1")
        .courseId("00001")
        .startDate(Date.valueOf("2023-09-01"))
        .expectedCompletionDate(Date.valueOf("2024-09-01"))
        .build();

    return studentsCourses;
  }
}
