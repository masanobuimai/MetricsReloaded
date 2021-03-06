/*
 * Copyright 2005, Sixth and Red River Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.sixrr.metrics.utils;

import com.intellij.psi.*;

public class MethodUtils {
    private MethodUtils() {
        super();
    }

    public static boolean isAbstract(PsiMethod method) {
        if (method.hasModifierProperty(PsiModifier.ABSTRACT)) {
            return true;
        }
        final PsiClass containingClass = method.getContainingClass();
        if (containingClass == null) {
            return false;
        }
        return containingClass.isInterface();
    }

    public static String calculateSignature(PsiMethod method) {

        final PsiClass containingClass = method.getContainingClass();

        final PsiParameterList parameterList = method.getParameterList();
        final PsiParameter[] parameters = parameterList.getParameters();
        final String className;
        if (containingClass != null) {
            className = containingClass.getQualifiedName();
        } else {
            className = "";
        }
        final String methodName = method.getName();
        final StringBuffer out = new StringBuffer(256);
        out.append(className);
        out.append('.');
        out.append(methodName);
        out.append('(');
        for (int i = 0; i < parameters.length; i++) {
            if (i != 0) {
                out.append(',');
            }
            final PsiType parameterType = parameters[i].getType();
            final String parameterTypeText = parameterType.getPresentableText();
            out.append(parameterTypeText);
        }
        out.append(')');
        return out.toString();
    }
}
