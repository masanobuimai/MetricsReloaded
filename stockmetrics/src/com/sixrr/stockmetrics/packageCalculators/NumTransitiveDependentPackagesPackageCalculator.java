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

package com.sixrr.stockmetrics.packageCalculators;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiPackage;
import com.sixrr.stockmetrics.dependency.DependentsMap;
import com.sixrr.metrics.utils.ClassUtils;

import java.util.HashSet;
import java.util.Set;

public class NumTransitiveDependentPackagesPackageCalculator extends PackageCalculator {
    private final Set<PsiPackage> packages = new HashSet<PsiPackage>();

    public void endMetricsRun() {
        for (final PsiPackage aPackage : packages) {
            final DependentsMap dependencyMap = getDependentsMap();
            final Set<PsiPackage> dependentPackages = dependencyMap.calculateTransitivePackageDependents(aPackage);
            final int numDependencies = dependentPackages.size();
            postMetric(aPackage, (double) numDependencies);
        }
    }

    protected PsiElementVisitor createVisitor() {
        return new Visitor();
    }

    private class Visitor extends JavaRecursiveElementVisitor {
        public void visitClass(PsiClass aClass) {
            super.visitClass(aClass);
            if (!ClassUtils.isAnonymous(aClass)) {
                final PsiPackage usedPackage = ClassUtils.findPackage(aClass);
                packages.add(usedPackage);
            }
        }
    }
}
