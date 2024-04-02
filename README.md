# 기초 캡스톤 서버 파트 Convention 정리

## Git Issue Label
- bug : 버그를 발견하고 상황을 이슈로 등록. 디버깅 목적의 코드 수정
- docs : 문서 작성
- setting : 환경세팅. gradle, yml 등
- refactor : 코드의 동작에는 변화 없이 유지보수 하기 좋은 코드로 수정
- deploy : 배포
- fix : 개발 도중 수정할 경우
- feat : 새로운 기능 추가

## Git Pull requests
pr 올릴 때 스웨거로 테스트하신 사진과 설명 첨부

## Branch name
깃이슈 라벨/간단한 작업설명-이슈번호 <br>
ex ) feat/comment-12

## Commit
깃이슈 라벨: 작업 설명 #깃이슈번호 <br>
ex ) git commit -m "feat: 댓글 api 작성 #12"

## Code
1. 클래스 스코프의 첫 줄, 마지막 줄 개행추가
2. 클래스명은 대문자로 시작
3. boolean 변수는 is로 시작
4. dto 이름은 ~Dto로 작성
5. import문에 와일드 카드 쓰지 않기
6. 조건문에서 소괄호는 양쪽 띄어쓰기 <br>
```
if (catIsCute) {
  System.out.println("meow")
}

while (catIsCute) {
  System.out.println("meow")
}
```
7. 의존성 주입, entity에서 필드 선언할 때 사이사이에 개행하기
