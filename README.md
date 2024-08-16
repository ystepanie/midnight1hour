# midnight1hour
헥사고날 아키텍처 구조를 사용하여 로그인 api 실습


## 🖥️ 프로젝트 소개

### 구조
<img src="https://github.com/user-attachments/assets/87703424-d976-4814-bbf9-50f7d74892c8" width="200" height="200">
<br>
<img src="https://github.com/user-attachments/assets/8ceb0836-96cc-4c15-b12f-dbe5bb823d10" width="400" height="200">

헥사고날 아키텍처의 구조를 참고하여 각 계층별로 패키지를 만들어 클래스를 작성했습니다.

### Adapter
각 핵심 로직과 외부를 연결하는 계층입니다. DB에서 데이터를 가져오는(out) 부분과 api의 직접적인 통신을 담당하는(in) Controller 부분이 해당합니다. 어떤 db를 사용할건지, api 통신을 담당하는 애플리케이션이 무엇인지에 대해서는 핵심 로직을 설계하는데 전혀 영향이 없어야 하고 그것을 위해 필요한 계층입니다. 
<details>
  <summary>관련 소스 보기</summary>
  <img src="https://github.com/user-attachments/assets/981bb130-a410-494c-94d7-93fb4f7bd33d" width="200" height="200"><br>
  
  ```java
  @Getter
  @Setter
  @ToString
  public class LoginDto {
         @NotBlank(message = MessageUtil.BLANK_ID)
         @Size(max = 15, min = 4, message = MessageUtil.INVALID_LENGTH_ID)
         private String userId;
  
         @NotBlank(message = MessageUtil.BLANK_PASSWORD)
         @Size(max = 20, min = 8, message = MessageUtil.INVALID_LENGTH_PASSWORD)
         @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
                 , message = MessageUtil.INVALID_PASSWORD)
         private String userPw;
  }
  ```

  ```java
  // 컨트롤러 Response 객체
  public class Response {
      int status;
      String message;
      Object data;
  
      public Response() {}
  
      public Response(int status, String message, Object data) {
          this.status = status;
          this.message = message;
          this.data = data;
      }
  
      public Response(int status, String message) {
          this.status = status;
          this.message = message;
      }
  }
  ```

  ```java
  @RestController
  @RequestMapping(value = "/v1/api")
  @RequiredArgsConstructor
  public class LoginController {
      private final LoginUseCase loginUserCase;
  
      final Logger log = LogManager.getLogger(getClass());
  
      @PostMapping("/login")
      public Response postLogin(@Valid @RequestBody LoginDto loginDto) throws Exception {
          return loginUserCase.login(loginDto);
      }
  }
  ```
  <img src="https://github.com/user-attachments/assets/6b3d0510-7e5f-4aed-a343-9af11bd29234" width="200" height="200"><br>

  ```java
@Entity
@Table(name="T_USER")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(name = "user_id", length = 15, nullable = false)
    private String userId;

    @Column(name = "user_pw", length = 20, nullable = false)
    private String userPw;

    @Column(name = "phone_number", length = 15, nullable = false)
    private String phoneNumber;

}
  ```

  ```java
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserId(String userId);
}
  ```

  ```java
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoginUserPort {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public User findByUserId(String userId) {
        UserEntity userEntity = jpaUserRepository.findByUserId(userId);
        User user = new User();
        if(userEntity == null) return null;

        user = User.builder()
                .userSeq(userEntity.getUserSeq())
                .userId(userEntity.getUserId())
                .userPw(userEntity.getUserPw())
                .phoneNumber(userEntity.getPhoneNumber())
                .build();

        return user;
    }
}
  ```

</details>

### Application
직접적인 비즈니스 로직이 들어가는 부분입니다. 어플리케이션 내에 port 패키지가 존재하며 이는 어댑터 계층에서 가공된 데이터가 적절한 추상화를 통해 비즈니스 로직에서 사용될 수 있게 하는 역할입니다. 핵심 로직에 대한 철저한 분리를 위해 꼭 필요한 패키지라고 할 수 있습니다. port의 in패키지에는 api와 직접적으로 통신하는 controller와 연관이 있습니다. out 패키지의 LoginUserPort는 db와 연관되어 있습니다. service 패키지는 비즈니스 로직입니다. 이 예제에서는 로그인에 필요한 로직들을 처리합니다. service 계층은 domain 계층의 model을 적절히 사용합니다. 이는 계층 간 결합도를 낮춤으로써 관심사의 분리를 이끕니다.

<details>
  <summary>관련 소스 보기</summary>
  <img src="https://github.com/user-attachments/assets/df71b790-f965-4f73-9a49-ad5bc942dedf" width="200" height="200"><br>  

  ```java
  public interface LoginUseCase {
    public Response login(LoginDto loginDto) throws Exception;
}
  ```

  ```java
public interface LoginUserPort {
    public User findByUserId(String userId);
}
  ```

  ```java
@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {
    private final LoginUserPort loginUserPort;

    @Override
    public Response login(LoginDto loginDto) throws Exception {
        // 유저 정보 찾기
        User user = findByUserId(loginDto);
        if(user == null) {
            return new Response(200, MessageUtil.USER_NOT_EXIST);
        }

        // 비밀번호 검증
        if(InvalidatePassword(user, loginDto.getUserPw())) {
            return new Response(200, MessageUtil.DIFF_PASSWORD);
        }

        return new Response(100, MessageUtil.LOGIN_SUCCESS, user);
    }

    public User findByUserId(LoginDto loginDto) throws Exception {
        String userId = loginDto.getUserId();
        return loginUserPort.findByUserId(userId);
    }

    public boolean InvalidatePassword(User user, String userPw) throws Exception {
        String userInfoPw = user.getUserPw();
        return !userInfoPw.equals(userPw);
    }
  ```

</details>

### Domain
이는 헥사고날 아키텍처에서 가장 코어한 부분을 담당합니다. 클린 아키텍처에서는 이를 가장 고수준의 정책이며, 가장 코어한 부분에 위치하여야 한다고 말합니다. 가장 변경이 적고 가장 안정된 상태를 유지하여야 합니다. 안정된 상태라 함은 다른 외부 라이브러리나 객체에 의존적이지 않아야 하며 반대로 저수준의 정책이 이 부분을 의지하여야 합니다. 도메인의 모델 패키지는 어플리케이션 계층과 협력하여 비즈니스 로직을 처리하는데 사용됩니다.

<details>
  <summary>관련 소스 보기</summary>
  <img src="https://github.com/user-attachments/assets/4ee69d54-4433-45c3-a3d9-f0c6e594dc4c" width="200" height="200"><br>

  ```java
@Getter
@NoArgsConstructor
public class User {
    private Long userSeq;
    private String userId;
    private String userPw;
    private String phoneNumber;

    @Builder
    public User(Long userSeq, String userId, String userPw, String phoneNumber) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.userPw = userPw;
        this.phoneNumber = phoneNumber;
    }
}
  ```
</details>

### Util
유틸 클래스에서는 모든 계층에서 사용이 가능하며 정적 타입(static)의 클래스를 제공합니다. 이는 최초 프로젝트 빌드 시 생성되어 재사용이 가능한 객체임을 알 수 있습니다. calendarUtil, StringUtil등의 클래스가 포함될 수 있습니다.

<br>

### GKE(Google Kubernetes Engine)
구글 쿠버네티스 엔진을 사용하여 클라우드 서버 기반 환경을 구축했습니다.
<details>
  <summary>관련 소스 보기</summary>

  ```linux
FROM --platform=linux/amd64 openjdk:17-slim
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

```yaml
//deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: [project-name]
spec:
  replicas: 2 //프로젝트 장애 시 대응할 백업서버 개수
  selector:
    matchLabels:
      app: [project-name]
  template:
    metadata:
      labels:
        app: [project-name]
    spec:
      containers:
      - name: [project-name]
        image: gcr.io/[PROJECT_ID]/[project-name]
        ports:
        - containerPort: 8080
```

```yaml
//service.yaml
apiVersion: v1
kind: Service
metadata:
  name: [project-name]-service
spec:
  type: LoadBalancer
  selector:
    app: [project-name]
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8080
```

</details>
<details>
  <summary>결과 화면</summary>

  ![image](https://github.com/user-attachments/assets/97e94bcb-9492-4204-afda-e0dd32f2f483)
</details>

## 🕰️ 개발 기간
* 24.06.02일 - 24.07.01일


### ⚙️ 개발 환경
- `Java 21`
- **IDE** : InteliJ
- **Framework** : Springboot(3.x)
- **Database** : Maria DB
- **Database Tool** : Sequel Ace
- **ORM** : JPA
- **Build** : Gradle Groovy
- **Skills** : lombok, Docker, Kubernetes, GCP

## 📌 프로젝트를 만들며 고민한 점들
#### 헥사고날 아키텍처의 정리 - <a href="https://jincchan.tistory.com/53" >블로그 이동</a>
- 헥사고날 아키텍처 패키지 설명
- 각 계층별 역할과 소스에 대한 상세 설명
#### 클라우드 배포환경 구축 - <a href="https://jincchan.tistory.com/52" >블로그 이동</a>
- Docker를 사용한 이미지 생성
- GKE 환경에서 배포환경 구축
