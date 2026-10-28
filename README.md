# 实验二：学生成绩管理系统

当前使用 Java 21、Spring Boot 3.2.12、Maven，数据保存在内存中。课程文档要求 JDK 17，当前按开发者决定保留 21。

## 启动与接口文档

在 IDEA 中重新加载 Maven 项目，使用 JDK 21 启动 `Demo2Application`。也可使用 Maven 3.8+ 执行 `mvn spring-boot:run`。

- Knife4j：http://localhost:8080/doc.html
- OpenAPI JSON：http://localhost:8080/v3/api-docs
- 测试：`mvn test`

Knife4j 使用官方 Spring Boot 3 Jakarta starter 4.5.0，不需要再添加 Springfox。

## 接口清单

| 方法 | 路径 | 请求体 | 成功 | 主要失败 |
| --- | --- | --- | --- | --- |
| GET | /api/students | 无 | 200，学生列表 | 500 |
| GET | /api/students/{studentNo}/scores | 无 | 200，包含课程名称和学分的成绩列表 | 500 |
| GET | /api/courses | 无 | 200，课程列表 | 500 |
| POST | /api/scores | studentNo、courseId、score | 201，完整记录及新 ID | 400、404、409、500 |
| PUT | /api/scores/{id} | score | 200，完整记录 | 400、404、500 |
| DELETE | /api/scores/{id} | 无 | 204，响应体为空 | 400、404、500 |

学号不存在时，成绩查询返回 HTTP 200 和空列表。

## 统一响应与错误码

成功：`{"code":0,"message":"ok","data":...}`。

失败：`{"code":40001,"message":"请求参数不合法","data":null}`。

删除成功返回 204，不包含以上响应体。

| 业务码 | HTTP | 条件与提示 | 出现位置 |
| --- | --- | --- | --- |
| 40001 | 400 | 学号不能为空、课程编号不能为空、分数不能为空、分数必须在 0 到 100 之间；无法解析请求或参数类型错误时提示请求参数不合法 | POST、PUT；参数类型错误也可出现在 DELETE |
| 40401 | 404 | 学生不存在、课程不存在 | POST |
| 40401 | 404 | 成绩不存在 | PUT、DELETE |
| 40901 | 409 | 该学生这门课程已录入成绩 | POST |
| 50000 | 500 | 服务器内部错误 | 全部接口 |

## 在文档页面验证写接口

初始数据已经包含 5 名学生和 3 门课程的全部组合，直接录入会返回 409。

在同一次启动期间依次执行：

1. PUT `/api/scores/1`，请求 `{"score":80}`，确认 200。
2. DELETE `/api/scores/1`，不带请求体，确认 204 和空响应。
3. 再次 DELETE 同一 ID，确认 404。
4. POST `/api/scores`，请求 `{"studentNo":"2023010001","courseId":"C001","score":90}`，确认 201，首次新 ID 为 16。

重启应用会恢复初始数据。文档中的在线调试会修改当前应用的内存数据。

## Git 记录说明

仓库在完成核心接口后才初始化，首个提交记录核心实现；后续记录文档等真实变更。没有补造此前开发阶段的提交历史。
