비밀번호와 아이디의 유효성검사는 Entity에서 해야할까? 아니면 Service에서 해야할까?

- Entity에서 validation(@Pattern 등)을 통해서 유효성 검사를 한다면, 속성으로 가지는 message는 어떻게 반환할 수 있을까?
- Service에서 유효성을 검사한다면, 상황에 맞는 에러를 잡아줄 수 있는 if문을 여러개 적어줘야할까? (비밀번호 값을 입력 안했을 때, 유효성 검증을 통과 못했을 때 등)

ResponseDto 를 반환 받아 핸들링하는 과정이 너무 지저분하다.</br>
깔끔하게 정리를 할 수 있는 방법을 찾아보자.

(22.12.11)