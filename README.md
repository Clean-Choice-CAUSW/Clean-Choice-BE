# Clean Choice
### 소개
Clean Choice는 중앙대학교 소프트웨어학부의 캡스톤 디자인 프로젝트입니다. Clean Choice는 캡스톤 디자인 프로젝트이기 때문에 기부금을 받지 않습니다.

# 실행 방법
1. jdk 17 설치
2. Redis 설치
3. PostgreSQL 설치
4. PostgreSQL에 Pg_vector extension 설치
5. `src\main\resources`에 gitignore file - `application.yml` 만들기. yml파일 내에 private 변수(eg. `spring.datasource.datasource.url`)에 적절한 값 넣어주기.
6. 외부 flask 서버, redis 등에 올바른 주소와 포트 넣어주기
```
spring:
  application:
    name: clean-choice

  servlet:
    multipart:
      max-file-size: 1000MB
      max-request-size: 1000MB
      enabled: true

  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher

  datasource:
    #driver-class-name: com.mysql.cj.jdbc.Driver
    driver-class-name: org.postgresql.Driver
    url: {DB URL}
    username: {DB USERNAME}
    password: {DB PW}

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        jdbc:
          batch_size: 10000
        order_inserts: true
        order_updates: true

  jwt:
    secret: {JWT SECRET}
    expirationMs: {EX_MS
    refreshExpirationMs: {REFRESH_EX_MS}

  data:
    redis:
      host: 127.0.0.1 # 로컬에서 테스트 할 때는 localhost로 사용
      port: 6379

  password:
    prefix: "clean_choice"


logging:
  level:
    org.hibernate.SQL: DEBUG
    com:
      amazonaws:
        util:
          EC2MetadataUtils: ERROR


springdoc:
  api-docs:
    enabled: true
  swagger-ui:
    groups-order: DESC
    doc-expansion: none
    operationsSorter: method
    display-request-duration: true

flask:
  url: http://3.37.64.46:15000
  #url: http://localhost:15000
  completion-url: http://localhost:15001
```
