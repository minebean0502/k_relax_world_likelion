## 팀 구성원, 개인 별 역할

---

프로젝트 팀 구성원을 기재해 주시고, 그 주의 팀원이 어떤 역할을 맡아서 개발을 진행했는지 구체적으로 작성해 주세요. 🙂

- 노채운 (팀장) - 스티커 발급 서비스 구현
- 이윤배 - 아마존S3 이미지 업로드,  mysql설치 연습
- 장수빈 - 로그인, 회원가입
- 탁민렬 -  결제 서비스 구현

## 팀 내부 회의 진행 회차 및 일자

---

예) 1회차(2024.03.18) 구글 밋 진행, (OOO님 불참)

- 일주일 간 진행한 내부 회의 횟수와 일자, 진행 방법, 불참 인원을 위와 같이 작성해 주세요.
- 1회차(2024.03.18) 디스코드 회의 진행
- 2회차(2024.03.19) 디스코드 회의 진행
- 3회차(2024.03.20) 디스코드 회의 진행
- 4회차(2024.03.21) 디스코드 회의 진행
- 5회차(2024.03.22) 디스코드 회의 진행

## 현재까지 개발 과정 요약 (최소 500자 이상)

---

현재까지 진행하고 있는 개발 현황을 기능별 목표, 목표달성률, 성과자체평가(상세히) 작성해주세요.

- 성과자체평가는 ‘기술적으로 새로 알게된 점, 어려웠던 점, 아쉬운 점, 시도해볼 점' 등을 작성해 주시면 됩니다 🙂
- 팀원 각자 현재 구현하고 있는 것을 적어주세요. :)
- ex) 기능별 목표: 암기장 삭제하기 기능 추가 / 목표달성률: 50% - 마켓에서 등록해제는 가능, 내 암기장 삭제는 아직 미구현 / 지속적으로 수정사항이 발생하여 완성도를 올리는 중, 현재 관련 문서 참고 중
- 노채운

  기능별 목표 : 폐기물 스티커 발급 (임시 폼 생성, 장바구니에 담기, 최총 폼 확인) (백엔드)

  목표 달성률 : 80% - Security 권한 문제 때문에(403 Foribidden) 직접 확인할 수 없었으나, Jwt, Security를 빼고 로컬 환경에서 진행했을 때에는 문제 없었습니다.

  @RequestParam과 @RequestBody의 사용, 그리고 Postman의 사용법에 대해 팀원들의 도움으로 확립할 수 있게 되었고, 임시 폼 생성-최종 확인 간에, 사용자 취소의 경우에 대해 어떻게 진행할지 고민중에 있습니다 (@transactional 사용 관련)

  추가적으로 기능 제작에 있어서, Facade를 써본적이 없는데, 이미 인증된 사용자가 사용한다고 할 시, extractUserId 등 기능을 추가해서 써보는 것을 한번 해보려고 합니다.

  또한 AuthFacade 관련해서, 인증시 사용자가 보낸 bearer토큰 만으로 사용자가 누구인지 알 수 있는 방법을 써보려고 합니다

- 이윤배

  아마존S3 이미지 업로드,  mysql설치 연습

  목표 달성률 : 80%

  성과 자체 평가 : 이미지 업로드 테스트 진행중

- 장수빈

  기능별 목표: 로그인, 회원가입, 아이디/비밀번호 찾기, 비밀번호 수정 기능 구현 (백엔드)

  목표 달성률: 80% - 각 기능별 작동은 하지만 프론트엔드 부분은 아직 미구현

  성과자체평가: 평소 아무 생각없이 썼던 RequestBody 부분을 한 메서드에 2개 이상 쓸 수 없다는 것을 알게 되었고 그로 인해 entity를 새로 만들거나 다른 방법을 찾아야 했다. 백엔드 부분을 번저 개발하다보니 프론트로 return해 주어야 할 부분에서 어떻게 처리해야할지 많이 헷갈렸고 다음 부터는 백엔드를 다 개발 하고 프론트를 나중에 작업하는 것보다는 부분부분 진행사항을 봐서 같이 작업해도 괜찮을 것 같다고 생각했다.

- 탁민렬

  기능별 목표 : 결제 관련 API 조사,결제 기능, 환불 기능(백엔드)

  목표 달성률 : 50%- 결제 기능은 구현했지만 환불은 아직 미구현

  성과자체평가: 수업때 배우지 않은 api를 스스로 조사해 적용 하는게 생각보다 어려웠고 백엔드에서 데이터들을 처리하는 과정 또한 복잡해서 이해가 잘 되질 않았다.


## 개발 과정에서 나왔던 질문 (최소 200자 이상)

---

개발을 진행하며 나왔던 질문 중 핵심적인 것을 요약하여 작성해 주세요 🙂

- 질의응답 과정 중 해결되지 않은 질문을 정리하여도 좋습니다.
- Git flow에 대해 대략적으로 감은 잡았는데, branch 를 생성 및 merge에 대해 많은 오류가 생기고 있습니다(?) (포인터가 어딜 가리키는지, git flow를 잘 하고 있는지 등)
- 403에러가 발생하는데 어디서 오류가 나는지 모르겠습니다
  - 메서드 실행 과정에 로그를 볼 수 있도록 해서 어디부터 실행이 안되는지, 어디서 오류가 나는지 확인해 보는 과정으로 오류 해결
- dto 구조 질문
  1. dto를 필요한 만큼 RequestBody에 넣는다 (ex : 3개면 RequestBody 3개)
     -> Servicie에서 한번에 처리한다
     -> 단 반환할때는 3개의 dto들을 다 반환한다
  2. entity에 저장시키기 위해, 전체의 정보를 담을 큰 dto 1개를 만든다
    1. Service3개를 나눠서 1개의 Controller 내에서 순차적으로 처리한다
    2. 그리고 반환값을 큰 dto로 한다 (or)
    3. -> Controller를 void로 하고 Service에 따로 dto 반환하는 처리를 시킨다.
- 결제 환불 진행

## 개발 결과물 공유

---

Github Repository URL: https://github.com/minebean0502/k_relax_world_likelion

- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂
- ![Untitled](https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fc69962b0-3951-485b-b10a-5bb29576bba8%2Febbe996a-3947-47ac-847e-ec000d3bc5a3%2FUntitled.png?table=block&id=989686a0-76a6-4cb9-b3df-04497410ec3e&spaceId=c69962b0-3951-485b-b10a-5bb29576bba8&width=450&userId=7af9430c-f86d-419b-89f5-85767bf0a982&cache=v2)
- ![Untitled](https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fc69962b0-3951-485b-b10a-5bb29576bba8%2Fbd1e71ae-8b5c-4488-877e-ee6a5297ed17%2FUntitled.png?table=block&id=b64dd77c-12b4-480b-9585-542f538c7be1&spaceId=c69962b0-3951-485b-b10a-5bb29576bba8&width=470&userId=7af9430c-f86d-419b-89f5-85767bf0a982&cache=v2)
- ![Untitled](https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fc69962b0-3951-485b-b10a-5bb29576bba8%2Ff4fb46fe-31ce-4592-8205-0265d4bb1ce7%2FUntitled.png?table=block&id=5882c325-417f-4bb4-9e6c-324ddc22173f&spaceId=c69962b0-3951-485b-b10a-5bb29576bba8&width=2000&userId=7af9430c-f86d-419b-89f5-85767bf0a982&cache=v2)
- ![Untitled](https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fc69962b0-3951-485b-b10a-5bb29576bba8%2Fb89b9bf8-7539-4bc7-9cec-259ea3446e58%2FUntitled.png?table=block&id=c5a33c8a-d713-413b-899e-11a8057b4988&spaceId=c69962b0-3951-485b-b10a-5bb29576bba8&width=2000&userId=7af9430c-f86d-419b-89f5-85767bf0a982&cache=v2)
- ![check](https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fc69962b0-3951-485b-b10a-5bb29576bba8%2Fb131ab19-0413-448a-b851-e146eb5e76b2%2FUntitled.png?table=block&id=a9e3f687-bff9-4b5b-8bc5-721e601fbb1d&spaceId=c69962b0-3951-485b-b10a-5bb29576bba8&width=2000&userId=7af9430c-f86d-419b-89f5-85767bf0a982&cache=v2)