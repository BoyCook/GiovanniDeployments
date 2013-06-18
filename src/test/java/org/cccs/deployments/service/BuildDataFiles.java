package org.cccs.deployments.service;

import org.cccs.deployments.provider.FishEyeIndexProvider;
import org.cccs.deployments.provider.SubversionProvider;
import org.tmatesoft.svn.core.SVNException;

/**
 * User: boycook
 * Date: Aug 25, 2010
 * Time: 5:54:28 PM
 */
public class BuildDataFiles {

    //For testing purposes only
    public static void main(String[] args) throws SVNException {
        DataService dataService = new DataService();
        dataService.setIndexProvider(new FishEyeIndexProvider());
        dataService.setSvn(new SubversionProvider("https://svn", "", ""));

        dataService.loadIndexes();
        dataService.loadProjectData();

//        ValidatorService validatorService;        
//        validatorService.validate();
    }
}
