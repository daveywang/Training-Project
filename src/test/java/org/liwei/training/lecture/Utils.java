/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved. 
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.lecture;

import javassist.*;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class Utils {
    /* print all fields info */
    public static void printFields(final CtClass ctClass) throws Exception {
        for (CtField field: ctClass.getDeclaredFields()) {
            System.out.println(">>>>>>>>>> Field: " + Modifier.toString(field.getModifiers()) + " " + field.getName() + " - " + field.getType().getName());
        }
    }

    /* print all constructors info */
    public static void printConstructors(final CtClass ctClass) throws Exception {
        for (CtConstructor constructor: ctClass.getConstructors()) {
            System.out.println(">>>>>>>>>> Constructor: " + Modifier.toString(constructor.getModifiers()) + " " + constructor.getLongName());
            MethodInfo methodInfo = constructor.getMethodInfo();

            LocalVariableAttribute localVaribaleTable = (LocalVariableAttribute)methodInfo.getCodeAttribute().getAttribute(javassist.bytecode.LocalVariableAttribute.tag);
            CtClass[] parameterTypes = constructor.getParameterTypes();

            if(localVaribaleTable != null && parameterTypes != null) {
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i >= localVaribaleTable.tableLength() - 1) break;

                    String variableName = methodInfo.getConstPool().getUtf8Info(localVaribaleTable.nameIndex(i+1));
                    System.out.println(">>>>>>>>>> **Parameter: " + variableName + " - " + parameterTypes[i].getName());
                }

                for (int i = parameterTypes.length + 1; i < localVaribaleTable.tableLength(); i++ ) {
                    String variableName = methodInfo.getConstPool().getUtf8Info(localVaribaleTable.nameIndex(i));
                    String variableType = localVaribaleTable.descriptor(i).replaceAll("/", ".");

                    System.out.println(">>>>>>>>>> **Local variable: " + variableName + " - " + variableType.substring(1));
                }
            }
        }
    }

    /* print all methods info */
    public static void printMethods(final CtClass ctClass) throws Exception {
        for(CtMethod method: ctClass.getDeclaredMethods()) {
            MethodInfo methodInfo = method.getMethodInfo();
            if (methodInfo.getCodeAttribute() == null) continue;

            String methodName = method.getLongName() + " - " + method.getReturnType().getName();
            System.out.println(">>>>>>>>>> Method: " + Modifier.toString(method.getModifiers()) + " " + methodName);
            System.out.println(">>>>>>>>>> Annotations: " + Arrays.deepToString(method.getAnnotations()));

            //ProcessMessage.collectMessage(Thread.currentThread().getId(), methodName, "", 0);

            LocalVariableAttribute localVaribaleTable = (LocalVariableAttribute)methodInfo.getCodeAttribute().getAttribute(javassist.bytecode.LocalVariableAttribute.tag);
            CtClass[] parameterTypes = method.getParameterTypes();

            if(localVaribaleTable != null && parameterTypes != null) {
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i >= localVaribaleTable.tableLength() - 1) break;

                    String variableName = methodInfo.getConstPool().getUtf8Info(localVaribaleTable.nameIndex(i+1));
                    String parameter = "**Parameter: " + variableName + " - " + parameterTypes[i].getName();
                    System.out.println(">>>>>>>>>> " + parameter);
                    //ProcessMessage.collectMessage(Thread.currentThread().getId(), parameter, "", 0);
                }

                for (int i = parameterTypes.length + 1; i < localVaribaleTable.tableLength(); i++ ) {
                    String variableName = methodInfo.getConstPool().getUtf8Info(localVaribaleTable.nameIndex(i));
                    String variableType = localVaribaleTable.descriptor(i).replaceAll("/", ".");

                    String localVariable = "**Local variable: " + variableName + " - " + variableType.substring(1);
                    System.out.println(">>>>>>>>>> " + localVariable);
                    //ProcessMessage.collectMessage(Thread.currentThread().getId(), localVariable, "", 0);
                }
            }
        }
    }

    /* Using Java Reflection to show all information of an object*/
    public static void printObjectInfo(Object object) throws Exception {
        System.out.println(">>>>>>>>>> class name: " + object.getClass().getSuperclass().getName());

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (fieldValue == null) fieldValue = "null";
            System.out.println(">>>>>>>>>> filed: " + field.getName() + ", value: " + fieldValue.toString() + ", Type: " + field.getType().getName());
        }

        for (Constructor<?> constructor : object.getClass().getConstructors()) {
            constructor.setAccessible(true);
            System.out.println(">>>>>>>>>> constructor name: " + constructor.toGenericString());
        }

        for (Method method : object.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            System.out.println(">>>>>>>>>> method name: " + method.getName());
        }
    }

    public static boolean isNumeric(String strNumber) {
        try {
            Double.valueOf(strNumber);
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    public static double toDouble(String strNumber) {
        try {
            return Double.valueOf(strNumber);
        }
        catch (Exception e) {
            return 0.00;
        }
    }

    public static int toInt(String strNumber) {
        try {
            return Integer.valueOf(strNumber);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static String booleanToYN(boolean b) {
        if (b) return "Y";
        else   return "N";
    }

    public static boolean trueOrFalse(String booleanFlag) {
        boolean flag = false;

        try {
            if (booleanFlag.trim().equalsIgnoreCase("TRUE") || booleanFlag.trim().equalsIgnoreCase("Y")) flag = true;
        }
        catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    public static String getJarPath(String className) {
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            className = className.replace('.', '/') + ".class";
            URL url = classLoader.getResource(className);
            JarURLConnection connection = (JarURLConnection)url.openConnection();
            JarFile file = connection.getJarFile();

            return file.getName();
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String getCurrentDir() {
        return System.getProperty("user.dir");
    }

    public static String md5(String password) {
        String md5Password = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] b = md.digest();
            String stmp = "";
            for (int n = 0; n < b.length; n++) {
                stmp = java.lang.Integer.toHexString(b[n] & 0XFF);

                if (stmp.length() == 1) md5Password = md5Password + "0" + stmp;
                else					md5Password += stmp;
            }
        }
        catch (NoSuchAlgorithmException e) {
            md5Password = null;
        }

        return md5Password;
    }

    public static String serialize(Object obj) throws Exception {
        String serializedMessage = null;

        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        ) {
            /* serialized the message object */
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            byte[] bytesEncoded = Base64.getEncoder().encode(byteArrayOutputStream.toByteArray());
            serializedMessage = new String(bytesEncoded);
        }
        catch (Exception e) {
            throw e;
        }

        return serializedMessage;
    }

    public static Object deSerialize(String serializedMessage) throws Exception {
        Object obj = null;
        byte[] bytes = serializedMessage.getBytes();
        byte[] bytesDecoded = Base64.getDecoder().decode(bytes);

        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytesDecoded);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        ) {
            obj = objectInputStream.readObject();
        }
        catch (Exception e) {
            throw e;
        }

        return obj;
    }

    public static String getMethodSignature(String methodFullName, int lineNumber) throws Exception {
        String className = methodFullName.substring(0, methodFullName.lastIndexOf("."));
        String methodName = methodFullName.substring(methodFullName.lastIndexOf(".") + 1, methodFullName.length());
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get(className);
        CtMethod ctMethod = Stream.of(ctClass.getDeclaredMethods())
                .filter(method -> method.getName().equals(methodName))
                .filter(method -> method.getMethodInfo().getLineNumber(0) <= lineNumber && lineNumber <= method.getMethodInfo().getLineNumber(Integer.MAX_VALUE))
                .findFirst().orElse(null);

		/* for testing
		System.out.println("*******************************************************************************");
		System.out.printf("########## className: %s, methodName: %s, lineNumber: %d\n", className, methodName, lineNumber);
		if (ctMethod != null) {
			System.out.println("########## start line number: " + ctMethod.getMethodInfo().getLineNumber(0));
			System.out.println("########## end line number: " + ctMethod.getMethodInfo().getLineNumber(Integer.MAX_VALUE));
			System.out.println("########## method signature: " + ctMethod.getLongName());
		}
		System.out.println("*******************************************************************************");
		*/

        if (ctMethod != null) return ctMethod.getLongName();
        else 				  return null;
    }

    public static String clobToString(Clob clob) throws Exception {
        if (clob == null) return  null;

        StringBuffer stringBuffer = new StringBuffer();
        String str;
        BufferedReader bufferRead = new BufferedReader(clob.getCharacterStream());

        while ((str = bufferRead .readLine()) != null) {
            stringBuffer.append(str);
        }

        return stringBuffer.toString();
    }

    public static boolean isNotNullOrEmpty(String str) {
        if (str == null || str.trim().isEmpty()) return false;
        else									 return true;
    }

    public static Class<?> loadClassFromJar(String className, String jarPath, ClassLoader classLoader) throws Exception {
        Class<?> clazz = null;
        URL[] urls = { new URL("jar:file:" + jarPath + "!/") };

        if (classLoader == null) classLoader = Thread.currentThread().getContextClassLoader();

        ClassLoader cl = URLClassLoader.newInstance(urls, classLoader);
        clazz = cl.loadClass(className);

        return clazz;
    }


    public static Map<String, Class<?>> loadAllClassesFromJar(String className, String jarPath) throws Exception {
        Map<String, Class<?>> clazz = new HashMap<String, Class<?>>();
        URL[] urls = { new URL("jar:file:" + jarPath + "!/") };
        JarFile jarFile = new JarFile(jarPath);
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        ClassLoader cl = URLClassLoader.newInstance(urls, Thread.currentThread().getContextClassLoader());

        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            if(jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) continue;

            String clazzName = jarEntry.getName().substring(0, jarEntry.getName().length()-6);
            clazzName = clazzName.replace('/', '.');
            clazz.put(className, cl.loadClass(className));
        }

        jarFile.close();

        return clazz;
    }
}
