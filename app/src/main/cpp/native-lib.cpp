#include <jni.h>
#include <string>
#include "bspatch.c"
#include "bsdiff.c"

//extern "C" JNIEXPORT jstring JNICALL
//Java_com_body_bsdiffapplication_MainActivity_stringFromJNI(
//        JNIEnv* env,
//        jobject /* this */) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}

//extern "C" JNIEXPORT jstring JNICALL
//Java_com_body_bsdiffapplication_BsPatchUtils_stringFromJNI(
//        JNIEnv* env,
//        jobject /* this */) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_body_bsdiffapplication_BsPatchUtils_stringFromJNI(JNIEnv *env, jclass clazz) {
    std::string hello = "BsPatchUtils_stringFromJNI Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_body_bsdiffapplication_BsPatchUtils_patch(JNIEnv *env, jclass clazz, jstring old_apk,
                                                   jstring new_apk, jstring patch_file) {
    // TODO: implement patch()
    int args = 4;
    char *argv[args];
    argv[0] = "DiffUtils";

    argv[1] = (char *) (env->GetStringUTFChars(old_apk, 0));
    argv[2] = (char *) (env->GetStringUTFChars(new_apk, 0));
    argv[3] = (char *) (env->GetStringUTFChars(patch_file, 0));

    //此处executePathch()就是上面我们修改出的
    int result = executePatch(args, argv);

    env->ReleaseStringUTFChars(old_apk, argv[1]);
    env->ReleaseStringUTFChars(new_apk, argv[2]);
    env->ReleaseStringUTFChars(patch_file, argv[3]);
    return result;

}
extern "C"
JNIEXPORT jint JNICALL
Java_com_body_bsdiffapplication_BsPatchUtils_diff(JNIEnv *env, jclass clazz, jstring old_apk,
                                                  jstring new_apk, jstring diss_file) {

}
