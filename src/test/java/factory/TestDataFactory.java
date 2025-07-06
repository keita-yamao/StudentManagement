package factory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domein.CourseDetail;
import raisetech.StudentManagement.domein.StudentDetail;

public class TestDataFactory {

  //受講生詳細情報
  public static StudentDetail ccreateSampleStudentDetail() {
    //受講生情報
    Student student = new Student();
    student.setStudentId("1");
    student.setName("山本太郎");
    student.setFurigana("ヤマモトタロウ");
    student.setNickname("タロ");
    student.setEmail("taro@exampl.com");
    student.setAddress("東京");
    student.setAge(25);
    student.setGender("男性");
    student.undelete();//削除フラグfalse

    //コース情報
    Course course = new Course();
    course.setCourseId("00001");
    course.setCourse("Javaコース");

    //受講情報
    StudentsCourses studentsCourses = new StudentsCourses();
    studentsCourses.setId(1);
    studentsCourses.setStudentId("1");
    studentsCourses.setCourseId("00001");
    studentsCourses.setStartDate(Date.valueOf("2023-09-01"));
    studentsCourses.setExpectedCompletionDate(Date.valueOf("2024-09-01"));

    //リターンオブジェクトの作成
    CourseDetail courseDetail = new CourseDetail();
    courseDetail.setCourse(course);
    courseDetail.setStudentsCourses(studentsCourses);
    List<CourseDetail> courseDetails = new ArrayList<>();
    courseDetails.add(courseDetail);
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setCourseDetail(courseDetails);

    return studentDetail;
  }

  //新規受講生詳細情報
  public static StudentDetail createSampleNewStudentDetail() {
    /*
    受講生情報
    *※受講生IDと削除フラグは登録処理時に付与される想定
    */
    Student student = new Student();
    student.setName("佐藤花子");
    student.setFurigana("サトウハナコ");
    student.setNickname("ハナ");
    student.setEmail("hana.sato@example.com");
    student.setAddress("東京");
    student.setAge(22);
    student.setGender("女性");

    /*
     *受講コース情報リスト
     */
    //コース情報
    Course course = new Course();
    course.setCourseId("00001");
    course.setCourse("Javaコース");
    //受講情報 ※受講情報のidは自動採番。受講生ID、開始日終了日は登録処理時に付与される想定
    StudentsCourses studentsCourses = new StudentsCourses();
    studentsCourses.setCourseId("00001");
    //受講コース情報
    CourseDetail courseDetail = new CourseDetail();
    courseDetail.setCourse(course);
    courseDetail.setStudentsCourses(studentsCourses);
    //リストに格納
    List<CourseDetail> courseDetails = new ArrayList<>();
    courseDetails.add(courseDetail);

    //受講生詳細情報にセットしてリターン
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setCourseDetail(courseDetails);
    return studentDetail;
  }

  /*受講生情報一覧
   * 削除フラグあり１件、なし２件の計3件のデータを作成
   * */
  public static List<Student> createSampleStudents() {
    //受講生1
    Student student1 = new Student();
    student1.setStudentId("1");
    student1.setName("山本太郎");
    student1.setFurigana("ヤマモトタロウ");
    student1.setNickname("タロ");
    student1.setEmail("taro@exampl.com");
    student1.setAddress("東京");
    student1.setAge(25);
    student1.setGender("男性");
    student1.delete();//削除フラグtrue
    //受講生2
    Student student2 = new Student();
    student2.setStudentId("2");
    student2.setName("鈴木一郎");
    student2.setFurigana("スズキイチロウ");
    student2.setNickname("イチ");
    student2.setEmail("ichiro@exampl.com");
    student2.setAddress("大阪");
    student2.setAge(30);
    student2.setGender("男性");
    student2.undelete();//削除フラグfalse
    //受講生3
    Student student3 = new Student();
    student3.setStudentId("3");
    student3.setName("田中花子");
    student3.setFurigana("タナカハナコ");
    student3.setNickname("ハナ");
    student3.setEmail("hana@exampl.com");
    student3.setAddress("北海道");
    student3.setAge(22);
    student3.setGender("女性");
    student3.undelete();//削除フラグfalse

    //リストに格納
    List<Student> students = new ArrayList<>();
    students.add(student1);
    students.add(student2);
    students.add(student3);

    return students;
  }

}
