package raisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import factory.TestDataFactory;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domein.CourseDetail;
import raisetech.StudentManagement.domein.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  /*
   searchStudentDetailListメソッドのテスト
   1.受講生詳細情報一覧_全件_リポジトリとコンバーター処理が想定回数呼び出されているか
   2.受講生詳細情報一覧_全件_削除フラグの立った生徒情報を省く処理が想定通り機能しているか
   */
  @Test
  void 受講生詳細情報一覧_全件_リポジトリとコンバーター処理が想定回数呼び出されているか() {
    //オブジェクトの準備
    List<Student> students = new ArrayList<>();
    List<Course> courses = new ArrayList<>();
    List<CourseDetail> courseDetails = new ArrayList<>();
    List<StudentsCourses> studentsCourses = new ArrayList<>();

    //リポジトリのスタブ化
    Mockito.when(repository.searchStudent()).thenReturn(students);
    Mockito.when(repository.searchCourses()).thenReturn(courses);
    Mockito.when(repository.searchStudentsCourses()).thenReturn(studentsCourses);

    //実行
    sut.searchStudentDetailList();

    //検証
    Mockito.verify(repository, Mockito.times(1)).searchStudent();
    Mockito.verify(repository, Mockito.times(1)).searchCourses();
    Mockito.verify(repository, Mockito.times(1)).searchStudentsCourses();
    Mockito.verify(converter, Mockito.times(1)).createCourseDetails(studentsCourses, courses);
    Mockito.verify(converter, Mockito.times(1)).createStudentDetails(students, courseDetails);

  }

  @Test
  void 受講生詳細情報一覧_全件_削除フラグの立った生徒情報を省く処理が想定通り機能しているか() {
    //テストデータを格納
    List<Student> students = TestDataFactory.createSampleStudents();

    //引数検証のためキャプチャーオブジェクトの作成
    ArgumentCaptor<List<Student>> studentsCaptor = ArgumentCaptor.forClass(List.class);

    //リポジトリとコンバーターのスタブ化
    List<Course> courses = new ArrayList<>();
    List<StudentsCourses> studentsCourses = new ArrayList<>();
    List<CourseDetail> courseDetails = new ArrayList<>();
    List<StudentDetail> studentDetails = new ArrayList<>();
    Mockito.when(repository.searchStudent()).thenReturn(students);
    Mockito.when(repository.searchCourses()).thenReturn(courses);
    Mockito.when(repository.searchStudentsCourses()).thenReturn(studentsCourses);
    Mockito.when(converter.createCourseDetails(studentsCourses, courses)).thenReturn(courseDetails);
    Mockito.when(converter.createStudentDetails(studentsCaptor.capture(), eq(courseDetails)))
        .thenReturn(studentDetails);

    //実行
    sut.searchStudentDetailList();

    //検証
    List<Student> filtered = studentsCaptor.getValue();
    assertEquals(2, filtered.size());
  }

  /*
   searchStudentDetailByIdメソッドのテスト
   1.受講生詳細情報を受講生IDで検索_リポジトリとコンバーターが想定回数呼び出されいているか
   */
  @Test
  void 受講生詳細情報を受講生IDで検索_リポジトリとコンバーターが想定回数呼び出されいているか() {
    //オブジェクトの準備
    Student student = new Student();
    List<Course> courses = new ArrayList<>();
    List<StudentsCourses> studentsCourses = new ArrayList<>();
    List<CourseDetail> courseDetails = new ArrayList<>();

    //リポジトリのスタブ化
    Mockito.when(repository.searchStudentById(anyString())).thenReturn(student);
    Mockito.when(repository.searchCourses()).thenReturn(courses);
    Mockito.when(repository.searchStudentsCourses()).thenReturn(studentsCourses);

    //実行
    sut.searchStudentDetailById(anyString());

    //検証
    Mockito.verify(repository, Mockito.times(1)).searchStudentById(anyString());
    Mockito.verify(repository, Mockito.times(1)).searchCourses();
    Mockito.verify(repository, Mockito.times(1)).searchStudentsCourses();
    Mockito.verify(converter, Mockito.times(1)).createCourseDetails(studentsCourses, courses);
    Mockito.verify(converter, Mockito.times(1)).createStudentDetail(student, courseDetails);

  }

  /*
   searchFilterStudentDetailListメソッドのテスト
   1.受講生詳細情報一覧をフィルタリングして表示する_リポジトリとコンバーターが想定回数呼び出されているか
   */
  @Test
  void 受講生詳細情報一覧をフィルタリングして表示する_リポジトリとコンバーターが想定回数呼び出されているか() {
    //オブジェクトの準備
    List<Student> students = new ArrayList<>();
    List<Course> courses = new ArrayList<>();
    List<StudentsCourses> studentsCourses = new ArrayList<>();
    List<CourseDetail> courseDetails = new ArrayList<>();

    //リポジトリのスタブ化
    Mockito.when(repository.searchFilterStudent(anyInt(), anyInt(), anyBoolean()))
        .thenReturn(students);
    Mockito.when(repository.searchCourses()).thenReturn(courses);
    Mockito.when(repository.searchFilterStudentsCourses(anyString())).thenReturn(studentsCourses);

    //実行
    sut.searchFilterStudentDetailList(0, 30, false, "00001");

    //検証
    Mockito.verify(repository, Mockito.times(1))
        .searchFilterStudent(anyInt(), anyInt(), anyBoolean());
    Mockito.verify(repository, Mockito.times(1)).searchCourses();
    Mockito.verify(repository, Mockito.times(1)).searchFilterStudentsCourses(anyString());
    Mockito.verify(converter, Mockito.times(1)).createCourseDetails(studentsCourses, courses);
    Mockito.verify(converter, Mockito.times(1)).createStudentDetails(students, courseDetails);

  }

  /*
    searchStudentByIdメソッドのテスト
    1.受講生IDが一致する受講生情報を検索する_リポジトリが想定回数呼び出されているか
    2.受講生IDが一致する受講生情報を検索する_受講生IDの検索処理が機能しているか
   */
  @Test
  void 受講生IDが一致する受講生情報を検索する_リポジトリが想定回数呼び出されているか() {
    //オブジェクトの準備
    List<Student> studentList = new ArrayList<>();

    //リポジトリのスタブ化
    Mockito.when(repository.searchStudent()).thenReturn(studentList);

    //実行
    sut.searchStudentById("1");

    //検証
    Mockito.verify(repository, Mockito.times(1)).searchStudent();

  }

  @Test
  void 受講生IDが一致する受講生情報を検索する_受講生IDの検索処理が機能しているか() {
    //テストデータを格納
    List<Student> students = TestDataFactory.createSampleStudents();

    //リポジトリのスタブ化
    Mockito.when(repository.searchStudent()).thenReturn(students);

    //実行
    Student actual = sut.searchStudentById("1");

    //検証
    Assertions.assertEquals("1", actual.getStudentId());
  }

  /*
    searchCourseListメソッドのテスト
    1.コース情報一覧を全件検索_リポジトリが想定回数呼び出されているか
   */
  @Test
  void コース情報一覧を全件検索_リポジトリが想定回数呼び出されているか() {
    //実行
    sut.searchCourseList();

    //検証
    Mockito.verify(repository, Mockito.times(1)).searchCourses();
  }

  /*
   *addStudentメソッドのテスト
   *1.リポジトリが想定回数呼び出されているか
   *2.受講生ID,削除フラグ,開始日・終了日の各処理が想定通りの処理になっているか
   */
  @Test
  void 新規受講生の登録_リポジトリが想定回数呼び出されているか() {
    //オブジェクトの準備
    List<Student> studentList = new ArrayList<>();

    //テストデータのセット
    StudentDetail studentDetail = TestDataFactory.createSampleNewStudentDetail();

    //リポジトリのスタブ化
    Mockito.when(repository.searchStudent()).thenReturn(studentList);

    //実行
    sut.addStudent(studentDetail);

    //検証
    Mockito.verify(repository, Mockito.times(1)).searchStudent();
    Mockito.verify(repository, Mockito.times(1)).insertStudent(any(Student.class));
    Mockito.verify(repository, Mockito.times(1)).insertStudentCourse(any(StudentsCourses.class));

  }

  @Test
  void 新規受講生の登録_受講生IDと論理削除フラグのsetが想定通りの処理になっているか() {

    //テストデータのセット
    List<Student> studentList = TestDataFactory.createSampleStudents();
    StudentDetail studentDetail = TestDataFactory.createSampleNewStudentDetail();

    //引数検証のためキャプチャーオブジェクトの作成
    ArgumentCaptor<Student> studentsCaptor = ArgumentCaptor.forClass(Student.class);
    ArgumentCaptor<StudentsCourses> studentCoursesCaptor = ArgumentCaptor.forClass(
        StudentsCourses.class);

    //リポジトリのスタブ化
    Mockito.when(repository.searchStudent()).thenReturn(studentList);

    //実行
    sut.addStudent(studentDetail);

    //検証
    Mockito.verify(repository).insertStudent(studentsCaptor.capture());
    Mockito.verify(repository)
        .insertStudentCourse(studentCoursesCaptor.capture());
    assertEquals("4", studentsCaptor.getValue().getStudentId());
    assertEquals(false, studentsCaptor.getValue().isDeleted());
    assertEquals("4", studentCoursesCaptor.getValue().getStudentId());
  }

  /*
   *updateStudentメソッドのテスト
   * 1.リポジトリの呼び出しが想定回数行われているか
   * 2.リポジトリに渡すstudentとstudentCoursesの値が想定通りか
   * ※日付チェックは今回は省く
   */
  @Test
  void 受講生詳細情報の更新_リポジトリが想定回数行われているか() {
    //テストデータのセット
    StudentDetail studentDetail = TestDataFactory.ccreateSampleStudentDetail();

    //実行
    sut.updateStudent(studentDetail);

    //検証
    Mockito.verify(repository, Mockito.times(1)).updateStudent(any(Student.class));
    Mockito.verify(repository, Mockito.times(1)).updateStudentCourses(any(StudentsCourses.class));
  }

  @Test
  void 受講生詳細情報の更新_リポジトリのメソッド引数の値が想定通りか() {
    //テストデータのセット
    StudentDetail studentDetail = TestDataFactory.ccreateSampleStudentDetail();

    //引数検証のためのキャプチャーオブジェクトの作成
    ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
    ArgumentCaptor<StudentsCourses> studentsCoursesCaptor = ArgumentCaptor.forClass(
        StudentsCourses.class);

    //実行
    sut.updateStudent(studentDetail);

    //検証
    Mockito.verify(repository).updateStudent(studentCaptor.capture());
    assertEquals("1", studentCaptor.getValue().getStudentId());
    assertEquals("山本太郎", studentCaptor.getValue().getName());
    assertEquals("ヤマモトタロウ", studentCaptor.getValue().getFurigana());
    assertEquals("タロ", studentCaptor.getValue().getNickname());
    assertEquals("taro@exampl.com", studentCaptor.getValue().getEmail());
    assertEquals("東京", studentCaptor.getValue().getAddress());
    assertEquals(25, studentCaptor.getValue().getAge());
    assertEquals("男性", studentCaptor.getValue().getGender());
    assertEquals(false, studentCaptor.getValue().isDeleted());

    Mockito.verify(repository).updateStudentCourses(studentsCoursesCaptor.capture());
    assertEquals(1, studentsCoursesCaptor.getValue().getId());
    assertEquals("1", studentsCoursesCaptor.getValue().getStudentId());
    assertEquals("00001", studentsCoursesCaptor.getValue().getCourseId());

  }

  /*
   *deleteStudentメソッドのテスト
   * 1.リポジトリが想定回数呼び出されているか
   * 2.削除フラグがtrueになっているか
   */
  @Test
  void 受講生情報の論理削除処理_リポジトリが想定回数呼び出されているか() {
    //オブジェクトの作成
    Student student = new Student();

    //実行
    sut.deleteStudent(student);

    //検証
    Mockito.verify(repository, Mockito.times(1)).updateStudent(student);

  }

  @Test
  void 受講生情報の論理削除処理_削除フラグがtrueになっているか() {
    //オブジェクトの作成
    Student student = new Student();
    student.undelete();//削除フラグfalse

    //引数検証のためのキャプチャーオブジェクトの作成
    ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);

    //実行
    sut.deleteStudent(student);

    //検証
    Mockito.verify(repository).updateStudent(studentCaptor.capture());
    assertEquals(true, studentCaptor.getValue().isDeleted());
  }
}

