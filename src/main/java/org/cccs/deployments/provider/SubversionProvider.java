package org.cccs.deployments.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: Aug 2, 2010
 * Time: 5:40:57 PM
 */
public class SubversionProvider {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SVNRepository repository;

    public SubversionProvider(final String url, final String uName, final String pWord) throws SVNException {
        if (url.startsWith("http")) {
            DAVRepositoryFactory.setup();
        } else {
            throw new RuntimeException("Invalid repository path");
        }

        repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(url));

        if (uName != null && !uName.equals("")) {
            log.debug("Creating AuthManager with: " + uName);
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(uName, pWord);
            repository.setAuthenticationManager(authManager);
        }
    }

    public void find(String path, String match, SVNNodeKind type, Set<String> matched, boolean working) throws SVNException {
        for (Object o : repository.getDir(path, -1, null, (Collection) null)) {
            SVNDirEntry item = (SVNDirEntry) o;

            String q = path.equals("") ? item.getName() : path + "/" + item.getName();

            if (item.getKind() == type) {
                if (q.contains(match)) {
                    String url = q.substring(1);
                    log.debug("Matched: " + url);
                    matched.add(url);
                }
            }

            if (item.getKind() == SVNNodeKind.DIR) {
                if (working) {
                    if (!item.getRelativePath().contains("tags") && !item.getRelativePath().contains("branches")) {
                        find(q, match, type, matched, working);
                    } else {
                        log.debug(format("Ignoring [%s] as it's a tag/branch", item.getRelativePath()));
                    }
                } else {
                    find(q, match, type, matched, working);
                }
            }
        }
    }

    public long getRevision(String path) {
        try {
            SVNDirEntry entry = repository.getDir(path, -1, false, null);
            return entry.getRevision();
        } catch (SVNException e) {
            log.error(format("Sorry unable to to get revision for [%s]", path));
            e.printStackTrace();
            return -1;
        }
    }

    public boolean checkPath(String path) throws SVNException {
        SVNNodeKind nodeKind = repository.checkPath(path, -1);
        return nodeKind != SVNNodeKind.NONE;
    }

    @Deprecated
    public void printFile(String path) throws SVNException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        SVNProperties fileProperties = new SVNProperties();
        repository.getFile(path, -1, fileProperties, baos);

        String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
        boolean isTextType = SVNProperty.isTextMimeType(mimeType);

        if (isTextType) {
            log.debug("File contents:");
            try {
                baos.writeTo(System.out);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            log.debug("Not a text file.");
        }
    }

    public ByteArrayOutputStream getFileAsStream(String path) throws SVNException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        repository.getFile(path, -1, new SVNProperties(), baos);
        return baos;
    }
}
