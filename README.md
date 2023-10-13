# 프로그래밍 언어
- 배열이 올 수 있는 위치
  선언문, 예) int a[10];
  배정문, 예) a[1] = 10;
  식, 예) x = a[1]+1;

1) Parser.java 에서, decl(), assignment(), factor() 함수를 수정
2) AST.java 에는 배열을 포함하도록 제공되고 있음
3) Sint.java 에서, State allocate (Decl ds, State state), Value V(Expr e, State state), State Eval(Assignment a, State state) 함수를 수정


int a[10];
a[1] = 10;
a[10];

int a[10];
a[1] = 10;
x = a[1] +1;
fun int a(int x;){print(x);}

fun int a(int x;){return x;}

>> int x = 5;

>> print(x);
5

>> x = a(31);
>> print(x);
>> 31
