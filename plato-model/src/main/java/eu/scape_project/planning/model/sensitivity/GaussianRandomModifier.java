/*******************************************************************************
 * Copyright 2006 - 2012 Vienna University of Technology,  
 * Department of Software Technology and Interactive Systems, IFS
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This work originates from the Planets project, co-funded by the European Union under the Sixth Framework Programme.
 ******************************************************************************/
package eu.scape_project.planning.model.sensitivity;

import org.apache.commons.math.random.JDKRandomGenerator;
import org.apache.commons.math.random.RandomGenerator;

import eu.scape_project.planning.model.tree.TreeNode;

public class GaussianRandomModifier implements IWeightModifier {
    
    private RandomGenerator generator = new JDKRandomGenerator();
    
    private int counter = 0;
    
    public boolean performModification(TreeNode node) {
        int nodeCount = node.getChildren().size();
        for(TreeNode child : node.getChildren()) {
            double oldWeight = child.getWeight();
            double newWeight = oldWeight +  generator.nextGaussian()/(8*nodeCount);
            child.setWeight(newWeight);
        }
        counter++;
        if(counter >= 50) {
            counter = 0;
            return false;
        } else {
            return true;
        }
    }

}
