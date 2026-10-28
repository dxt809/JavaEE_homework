# 实验一：学生成绩查询系统

## 环境与运行

- JDK 21
- Maven 3.8 或更高版本
- Spring Boot 3.2.12

在本目录执行 `mvn test` 验证功能，再执行 `mvn spring-boot:run` 启动服务。IntelliJ IDEA 打开本目录的 `pom.xml` 后，应把 Project SDK、Maven Importer JDK 和 Run Configuration JDK 均设为 JDK 21。若 Maven Wrapper 下载或导入失败，可在 IDEA 的 Maven 设置中选择已安装的 Maven 3.8+。

## 查询接口

| 请求 | 用途 |
| --- | --- |
| `GET /api/students` | 查询全部学生，按学号升序 |
| `GET /api/students/{studentNo}/scores` | 查询指定学号的成绩、课程名与学分；学号不存在时返回 `[]` |
| `GET /api/courses` | 查询全部课程 |

数据保存在内存中，重启后恢复预置数据：5 名学生、3 门课程、15 条成绩。
