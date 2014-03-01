#include <jni.h>
#include <stdio.h>
#include  "cn_com_comit_jni_HelloJni.h"

JNIEXPORT void JNICALL Java_cn_com_comit_jni_HelloJni_displayHelloJni
  (JNIEnv *, jobject)
{
printf("Hello Jni .....\n");
return ;
}
