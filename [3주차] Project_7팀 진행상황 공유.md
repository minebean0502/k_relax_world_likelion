## 팀 구성원, 개인 별 역할

---

프로젝트 팀 구성원을 기재해 주시고, 그 주의 팀원이 어떤 역할을 맡아서 개발을 진행했는지 구체적으로 작성해 주세요. 🙂

- 노채운 (팀장)  
- 이윤배 
- 장수빈  
- 탁민렬  

## 팀 내부 회의 진행 회차 및 일자

---

예) 1회차(2024.03.18) 구글 밋 진행, (OOO님 불참)

- 일주일 간 진행한 내부 회의 횟수와 일자, 진행 방법, 불참 인원을 위와 같이 작성해 주세요.
- 1회차(2024.03.25) 디스코드 회의 진행
- 2회차(2024.03.26) 디스코드 회의 진행
- 3회차(2024.03.27) 디스코드 회의 진행
- 4회차(2024.03.28) 디스코드 회의 진행
- 5회차(2024.03.29) 디스코드 회의 진행

## 현재까지 개발 과정 요약 (최소 500자 이상)

---

현재까지 진행하고 있는 개발 현황을 기능별 목표, 목표달성률, 성과자체평가(상세히) 작성해주세요.

- 성과자체평가는 ‘기술적으로 새로 알게된 점, 어려웠던 점, 아쉬운 점, 시도해볼 점' 등을 작성해 주시면 됩니다 🙂
- 팀원 각자 현재 구현하고 있는 것을 적어주세요. :)
- ex) 기능별 목표: 암기장 삭제하기 기능 추가 / 목표달성률: 50% - 마켓에서 등록해제는 가능, 내 암기장 삭제는 아직 미구현 / 지속적으로 수정사항이 발생하여 완성도를 올리는 중, 현재 관련 문서 참고 중
- 노채운

  기능별 목표: 로그인 - 서비스 프론트 페이지 담당

  목표 달성률 : 40% - ajax를 사용하여 인증 관련 부분에서 문제가 생겨 페이지 제작보다 문제 원인 파악, 해결에 많은 시간이 투자된 상태

  성과자체평가: 프론트↔ 백엔드간의 대략적인 개념도 잡혔고, 인증 관련 부분도 어떻게 하면 되는지, 어떤 방법으로 진행하면 되는지 알았기에, 이후에는 html 구조 간단히 잡고, css랑 js 사용해서 동적 페이지를 만들려고 했으나 계속해서 .authticated() 이후 jwt 토큰 송신 관련해서 문제 발생해서 진도를 나가지 못했음

- 이윤배

  기능 별 목표 : 프로필 사진 이미지 AWS S3 이미지 업로드 구현, 프로필 수정시 S3 용량이 낭비되지 않게 기존 이미지 S3내에서 삭제 작업 진행

  목표달성률 : 80% - 에러 해결 진행중

  성과 자체평가: aws는 알면 알수록 신기한 것 같다.

- 장수빈

  기능별 목표: 로그인 관련 페이지 프론트 개발, oauth2 카카오 로그인 기능 구현

  목표 달성률: 60% - 로그인 관련 페이지에서 jwt를 가진 사용자가 인증된 페이지로 넘어갈 수 없는 오류 해결 중, 카카오 로그인은 기초 세팅만 해놓은 상태

  성과자체평가: 생각보다 프론트 관련해서 모르는 것이 너무 많아서 찾아봐야 할 것들이나 어떻게 연결해야 할지 jwt 관련해서 어려움이 너무 많았다. jwt를 가져와서 출력하는 것은 되지만 jwt를 가지고 있는 사용자가 서비스 화면(jwt 인증된 사람들만 접근 가능한 화면)에 접근할 수 없는 에러가 생겨서 해결하려고 하고 있다.  restcontroller, controller의 차이를 좀 더 알게 되었고 프론트에 대해 좀 더 알고 있었다면 해볼 수 있었던 것들이 더욱 많았을 것 같아서 조금 아쉽다.

- 탁민렬

  기능별 목표: 아임포트 api를 이용해서 이니시스 통합결제 구현,환불 구현

  목표 달성률:80% -일단 기능은 다 구현했으나 설계대로 구현되지 않아 병합하기 위해서는 코드 리펙토링이 필요

  성과자체평가 : api문서에따른 엔드 포인트와 필요 요청을 백엔드에서 구현하는방법에 익숙치 않아서 오랜 시간이 걸렸고 자바스크립트를 써야 결제와 환불이 가능했기 떄문에 자바스크립트를 공부하는데도 오래 걸린거 같다.


## 개발 과정에서 나왔던 질문 (최소 200자 이상)

---

개발을 진행하며 나왔던 질문 중 핵심적인 것을 요약하여 작성해 주세요 🙂

- 질의응답 과정 중 해결되지 않은 질문을 정리하여도 좋습니다.
- 동기 통신과 비동기 통신간의 차이는 크게 무엇이 있나
- 사용자 인증이 끝났을 때, 인증을 확인하는 방식에는 어떤것이 있으며 어떤 것을 쓸 것인가?
  - 그리고 저장 방식에는 어떤것을 쓸 것인가? (로컬, 쿠키, 세션)
- 비동기 통신에서 AJAX를 사용한다면, 그 사용법은?
- 프론트에서 jwt를 가져오기 위해 ajax를 사용해야 하는가?
- RestController 와 Controller를 같이 사용해도 되는가?
- 환불 test중 postman으로는 실행되는데 local로 실행하면 id 부분이 null로 표시된다.

## 개발 결과물 공유

---

Github Repository URL: https://github.com/minebean0502/k_relax_world_likelion

- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂
- ![Untitled](https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fc69962b0-3951-485b-b10a-5bb29576bba8%2Febbe996a-3947-47ac-847e-ec000d3bc5a3%2FUntitled.png?table=block&id=989686a0-76a6-4cb9-b3df-04497410ec3e&spaceId=c69962b0-3951-485b-b10a-5bb29576bba8&width=450&userId=7af9430c-f86d-419b-89f5-85767bf0a982&cache=v2)
- ![Untitled](https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fc69962b0-3951-485b-b10a-5bb29576bba8%2Fbd1e71ae-8b5c-4488-877e-ee6a5297ed17%2FUntitled.png?table=block&id=b64dd77c-12b4-480b-9585-542f538c7be1&spaceId=c69962b0-3951-485b-b10a-5bb29576bba8&width=470&userId=7af9430c-f86d-419b-89f5-85767bf0a982&cache=v2)
- ![Untitled](https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fc69962b0-3951-485b-b10a-5bb29576bba8%2Ff4fb46fe-31ce-4592-8205-0265d4bb1ce7%2FUntitled.png?table=block&id=5882c325-417f-4bb4-9e6c-324ddc22173f&spaceId=c69962b0-3951-485b-b10a-5bb29576bba8&width=2000&userId=7af9430c-f86d-419b-89f5-85767bf0a982&cache=v2)
- ![Untitled](https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fc69962b0-3951-485b-b10a-5bb29576bba8%2Fb89b9bf8-7539-4bc7-9cec-259ea3446e58%2FUntitled.png?table=block&id=c5a33c8a-d713-413b-899e-11a8057b4988&spaceId=c69962b0-3951-485b-b10a-5bb29576bba8&width=2000&userId=7af9430c-f86d-419b-89f5-85767bf0a982&cache=v2)
- ![check](https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fc69962b0-3951-485b-b10a-5bb29576bba8%2Fb131ab19-0413-448a-b851-e146eb5e76b2%2FUntitled.png?table=block&id=a9e3f687-bff9-4b5b-8bc5-721e601fbb1d&spaceId=c69962b0-3951-485b-b10a-5bb29576bba8&width=2000&userId=7af9430c-f86d-419b-89f5-85767bf0a982&cache=v2)