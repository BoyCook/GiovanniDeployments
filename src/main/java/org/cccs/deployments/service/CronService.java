package org.cccs.deployments.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * User: boycook
 * Date: Aug 3, 2010
 * Time: 6:56:20 PM
 */
public class CronService extends QuartzJobBean {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private DataService dataService;
    private ValidatorService validatorService;

    public CronService() {
        validatorService = new ValidatorService();
    }

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }    

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("Started CronService");
        dataService.loadFromDisk();
        dataService.loadIndexes();
        dataService.loadProjectData();
        validatorService.validate();
        log.info("Finished CronService");
    }
}
