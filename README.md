## 깃 Flow 관련 docs
[Gitflow에 대하여](https://velog.io/@couchcoding/%EC%B6%A9%EB%8F%8C-%EC%97%86%EB%8A%94-Git%EC%9D%84-%EC%9C%84%ED%95%B4-Git-Flow%EC%97%90-%EB%8C%80%ED%95%B4-%EC%95%8C%EC%95%84%EB%B3%B4%EC%9E%90)   
[커밋 메세지는?](https://puleugo.tistory.com/165)   
[커밋 메세지의 실제 예시](https://github.com/gyoogle/tech-interview-for-developer/blob/master/ETC/Git%20Commit%20Message%20Convention.md)


## 룰
1. master, main에 절대 Push하지 않는다.
2. 각자 맞는 개발 부분에 맞춰에 push한다.

---
## git flow // branch 예시

- master/main : 제품 출시 브랜치 (최종 결과물)
- develop : 출시를 위해 개발하는 브랜치
  - feature 기능 구현이 완료되고, 해당 feature의 팀원들 간의 코드 리뷰(찬성)가 끝난다면, develop에 merge 됩니다.
- feat/{기능명}: 새로운 기능 개발하는 브랜치
- refactor/{기능명}: 개발된 기능을 리팩터링하는 브랜치
- hotfix: 출시 버전에서 발생한 버그를 수정하는 브랜치

### GitFlow의 브랜치 생성은
master/main -> develop -> feature 순으로 생성되며, 코드 통합은 역으로   
feature -> develop -> release -> master/main 순으로 진행이 됩니다.


예시
- main
- dev/feat/login : 로그인 기능
- dev/feat/register : 회원가입 기능
- dev/feat/modify-id : 아이디 바꾸기
- dev/feat/modify-password : 비밀번호 바꾸기
- dev/feat/find : 아이디/비밀번호 찾기

- dev/refactor/pay : 결제 리팩토링 할거에요!

---
## commit message rule
3가지 영역(제목, 본문, 꼬릿말)로 나눠 제작한다
```
type : {제목}

Body : 

foo(꼬릿말/who) : {작성자}
```
메시지 type은 아래와 같이 분류된다. 아래와 같이 소문자로 작성한다.

- feat : 새로운 기능 추가
- fix : 버그 수정
- docs : 문서 내용 변경
- style : 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우 등
- refactor : 코드 리팩토링
- test : 테스트 코드 작성
- chore : 빌드 수정, 패키지 매니저 설정, 운영 코드 변경이 없는 경우 등
- Rename : 파일명(or 폴더명) 을 수정한 경우
- Remove : 코드(파일)의 삭제가 있을 때.

### 제목 
과거형을 사용하지 않고, 명령조로 시작한다.
간결하고 요점만 서술 한다


### Body (본문)
   Body (본문)은 최대한 상세히 적고, 무엇을 왜 진행했는 지 설명해야 한다. 만약 한 줄이 72자가 넘어가면 다음 문단으로 나눠 작성하도록 한다.
   
### foo (꼬릿말)
일반적으로 issue tracker ID를 작성하나, 우리팀은 누가 commit 한 내용인지 알기 위해 작성자 명을 적는다.

--------------------------------------

ImageUpload readme

aws S3 내 사용자 생성 및 S3 스토리지 생성은 너무길어질까봐 생략합니다. 구글 보면 아주 잘돼있고 간단하더라구용.

application.properties 에서

    #AWS S3 imageUpload
    #access key
    cloud.aws.credentials.accessKey= ${accessKey} // aws IAM 사용자 accessKEY 입력

    #secret key
    cloud.aws.credentials.secretKey= ${secretKey} // aws IAM 사용자 sccessKEY 입력

    #bucketName
    cloud.aws.s3.bucketName=${bucketName}

    #server-region
    cloud.aws.region.static=ap-northeast-2

    #cloud formation
    cloud.aws.stack.auto=false

S3Config Class 생성

    @Configuration
    public class S3Config {
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
