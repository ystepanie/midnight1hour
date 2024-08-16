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

  ![image](https://github.com/user-attachments/assets/981bb130-a410-494c-94d7-93fb4f7bd33d)
  ![image](https://github.com/user-attachments/assets/c49e303d-71f3-408b-b14e-499b1903331a)
  ![image](https://github.com/user-attachments/assets/9c8fcb03-5d86-4f99-93cc-6715e031815f)
  ![image](https://github.com/user-attachments/assets/d502cb9b-a0b6-4ebd-bd4b-aa8ffea0af8b)
  
  ![image](https://github.com/user-attachments/assets/6b3d0510-7e5f-4aed-a343-9af11bd29234)
  ![image](https://github.com/user-attachments/assets/11c56909-fece-4290-99e3-3944322b0cb1)
  ![image](https://github.com/user-attachments/assets/642de78e-420a-4d33-948e-485e21f216c4)
  ![image](https://github.com/user-attachments/assets/a524d9ae-7c9a-4d6c-b4dd-34ef1f93ce86)

</details>

### Application
직접적인 비즈니스 로직이 들어가는 부분입니다. 어플리케이션 내에 port 패키지가 존재하며 이는 어댑터 계층에서 가공된 데이터가 적절한 추상화를 통해 비즈니스 로직에서 사용될 수 있게 하는 역할입니다. 핵심 로직에 대한 철저한 분리를 위해 꼭 필요한 패키지라고 할 수 있습니다. port의 in패키지에는 api와 직접적으로 통신하는 controller와 연관이 있습니다. out 패키지의 LoginUserPort는 db와 연관되어 있습니다. service 패키지는 비즈니스 로직입니다. 이 예제에서는 로그인에 필요한 로직들을 처리합니다. service 계층은 domain 계층의 model을 적절히 사용합니다. 이는 계층 간 결합도를 낮춤으로써 관심사의 분리를 이끕니다.

<details>
  <summary>관련 소스 보기</summary>

  ![image](https://github.com/user-attachments/assets/df71b790-f965-4f73-9a49-ad5bc942dedf)
  ![image](https://github.com/user-attachments/assets/d27ae12f-58a4-4970-9fa1-f9bf424d8861)
  ![image](https://github.com/user-attachments/assets/d6489950-322c-4b8c-9665-4549049da520)
  ![image](https://github.com/user-attachments/assets/b73f1caf-4164-466b-9633-a0996c924733)

</details>

### Domain
이는 헥사고날 아키텍처에서 가장 코어한 부분을 담당합니다. 클린 아키텍처에서는 이를 가장 고수준의 정책이며, 가장 코어한 부분에 위치하여야 한다고 말합니다. 가장 변경이 적고 가장 안정된 상태를 유지하여야 합니다. 안정된 상태라 함은 다른 외부 라이브러리나 객체에 의존적이지 않아야 하며 반대로 저수준의 정책이 이 부분을 의지하여야 합니다. 도메인의 모델 패키지는 어플리케이션 계층과 협력하여 비즈니스 로직을 처리하는데 사용됩니다.

### Util
유틸 클래스에서는 모든 계층에서 사용이 가능하며 정적 타입(static)의 클래스를 제공합니다. 이는 최초 프로젝트 빌드 시 생성되어 재사용이 가능한 객체임을 알 수 있습니다. calendarUtil, StringUtil등의 클래스가 포함될 수 있습니다.

GKE(Google Kubernetes Engine)를 사용하여 클라우드 배포 환경을 구축해보았습니다.

<br>

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
