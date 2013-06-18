package org.cccs.deployments.validation;

import org.cccs.deployments.cache.ProjectCache;
import org.cccs.deployments.domain.Project;
import org.cccs.deployments.service.ValidatorService;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.cccs.deployments.utils.IOUtils.readFile;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: Aug 8, 2010
 * Time: 6:26:20 PM
 */
public class TestValidatorService {

    @SuppressWarnings({"unchecked"})
    @Test
    public void validatorServiceShouldWork() throws ClassNotFoundException, IOException, InterruptedException {
        ProjectCache.setProjects((Map<String, Project>) readFile("src/test/resources/projects.data"));
        ValidatorService validator = new ValidatorService();
        validator.validate();
//        Thread.sleep(1000);
//        listThreads();
    }

//    @Test
    public void listThreads() throws InterruptedException {

        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup parent;
        while ((parent = rootGroup.getParent()) != null) {
            rootGroup = parent;
        }

        List<Thread> threads = new ArrayList<Thread>();
        findThreads(rootGroup, "HttpValidator", threads);

        Thread.sleep(30000);

        List<Thread> dead = new ArrayList<Thread>();
        findThreads(rootGroup, "HttpValidator", dead);

        System.out.println(format("Were [%d] Now [%s]", threads.size(), dead.size()));
        assertThat(dead.size(), is(equalTo(0)));
    }

    public static void findThreads(ThreadGroup group, String match, List<Thread> found) {
        System.out.println("Group[" + group.getName() + ":" + group.getClass() + "]");
        int nt = group.activeCount();
        Thread[] threads = new Thread[nt * 2 + 10]; //nt is not accurate
        nt = group.enumerate(threads, false);

        // List every thread in the group
        for (int i = 0; i < nt; i++) {
            Thread t = threads[i];
            
            if (t.getClass().getName().indexOf(match) > -1) {
                System.out.println("Matched thread: " + t.getName() + " - " + t.getClass().getName());
                found.add(t);
            } else {
                System.out.println(t.getName() + " - " + t.getClass().getName());
            }
        }

        // Recursively list all subgroups
        int ng = group.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[ng * 2 + 10];
        ng = group.enumerate(groups, false);

        for (int i = 0; i < ng; i++) {
            findThreads(groups[i], match, found);
        }
    }
}
