# Student Management System

プログラミングスクール（RaiseTech）の課題成果物として開発した、受講生情報およびコース申し込み状況を管理・操作するための RESTful API Web アプリケーションです。

## 主な機能

- **受講生情報の検索・一覧取得**
  - 受講生詳細情報の一覧取得（全件検索）
  - 受講生IDを指定した単一検索
  - 名前・フリガナ・年齢範囲・論理削除フラグ・コースIDによる複合条件絞り込み検索
- **受講生情報の登録・更新・論理削除**
  - 新規受講生および関連コース情報の同時登録
  - 受講生情報の更新
  - 受講生ID指定による論理削除処理
- **コース情報およびステータスの管理**
  - 全コース情報の一覧取得
  - 受講状態ステータス定義の一覧取得
- **入力チェック・例外ハンドリング**
  - Jakarta Validation によるリクエストパラメータおよび DTO のバリデーション
  - API 仕様可視化のための OpenAPI (Swagger UI) の導入

## 技術スタック & バージョン

- **Java**: 21
- **Framework**: Spring Boot 3.3.2
- **Build Tool**: Gradle (Dependency Management 1.1.6)
- **Database / ORM**: MySQL (`mysql-connector-j`) / MyBatis 3.0.3
- **Template Engine**: Thymeleaf
- **API Documentation**: OpenAPI 3 (`springdoc-openapi` 2.5.0)
- **Testing**: JUnit 5 (JUnit Platform) / MyBatis Test 3.0.3 / H2 Database 2.2.224
- **Utilities**: Lombok, Apache Commons Lang 3.14.0
- **Frontend**: HTML / CSS / JavaScript (Vanilla JS)

## セットアップ & 実行方法

### 動作要件
- Java 21 以上
- Gradle

### 起動手順

1. **リポジトリのクローン**
   ```bash
   git clone [https://github.com/keita-yamao/StudentManagement.git](https://github.com/keita-yamao/StudentManagement.git)
   cd StudentManagement
