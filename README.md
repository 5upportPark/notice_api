# [과제전형] 공지사항 API
### 박지원

# 실행 방법
작성 환경: Windows11, SpringBoot, JDK17, MySQL, Docker

※ MySQL 서버에 스키마명이 'workd'인 스키마 필요
1. cmd 창에서 프로젝트 루트 경로로 이동
2. ./gradlew clean build 명령어 실행
3. docker run -i -t -p 8080:8080 --name backend_pjw 명령어 실행
4. localhost:8080 으로 접근
